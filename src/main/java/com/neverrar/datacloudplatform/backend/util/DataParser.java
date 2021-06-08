package com.neverrar.datacloudplatform.backend.util;

import com.alibaba.fastjson.JSON;
import com.csvreader.CsvReader;
import com.neverrar.datacloudplatform.backend.model.*;
import com.neverrar.datacloudplatform.backend.repository.*;
import com.neverrar.datacloudplatform.backend.service.OSSService;
import com.neverrar.datacloudplatform.backend.view.AllInteractionBehaviourDataByTest;
import com.neverrar.datacloudplatform.backend.view.AllMainDataByTest;
import com.neverrar.datacloudplatform.backend.view.InteractionBehaviourDataInformation;
import com.neverrar.datacloudplatform.backend.view.MainDataInformation;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import net.lingala.zip4j.core.ZipFile;

public class DataParser {

   private final HashMap<String,Tester> testerHashMap=new HashMap<>();

   private final HashMap<String,Task> taskHashMap=new HashMap<>();

   private final HashMap<String,Test> testHashMap=new HashMap<>();

   private final Project project=new Project();

   private User owner;

   private String path;

   private File zFile;

   private File dFile;

   private File dataFolder;

   private File testInfoFile;

   private File screenCaptureFolder;

   private ProjectRepository projectRepository;

   private TesterRepository testerRepository;

   private TaskRepository taskRepository;

   private TestRepository testRepository;

   private MainDataRepository mainDataRepository;

   private InteractionBehaviourDataRepository interactionBehaviourDataRepository;

   private MarkDataRepository markDataRepository;

   private LogEventDataRepository logEventDataRepository;

   private VideoRepository videoRepository;

   private OSSService ossService;


   public  void parseZip(MultipartFile dataFile){
       try {
           zFile = new File(path+"zip/" +dataFile.getName()+UUID.randomUUID() + ".zip");
           zFile.mkdirs();
           dataFile.transferTo(zFile);
           ZipFile zipFile = new ZipFile(zFile);
           zipFile.setFileNameCharset("GBK");
           dFile=new File(path+"data/" +dataFile.getName()+UUID.randomUUID());
           dFile.mkdirs();
           zipFile.extractAll(dFile.getPath());
           parseProject(dFile);
           project.setName(dataFile.getOriginalFilename());
           parseTotalFile(dFile);
       }
       catch (IOException | ZipException e){
            e.printStackTrace();
       }
   }

   public void parseTotalFile(File file){
       File[] dataList = file.listFiles();
       if(dataList==null) return;
       for(File dataFile: dataList){
           switch (dataFile.getName()) {
               case "DataFolder":
                   dataFolder=dataFile;
                   break;
               case "ScreenCaptureFolder":
                   screenCaptureFolder=dataFile;
                   break;
               case "Task.csv":
                   testInfoFile=dataFile;
                   break;
               default:
                   break;
           }
       }
       parseTester(dataFolder);
       updateTestInfo(testInfoFile);
   }

   /*
         任务开始时间,任务结束时间,任务名称,任务具体次数,实验用户名,是否完成,出错次数
    */
   public void updateTestInfo(File file){
       try {
           CsvReader csvReader = new CsvReader(file.getAbsolutePath(), ',', Charset.forName("GBK"));
           csvReader.readHeaders();
           while(csvReader.readRecord()){
               String taskName=csvReader.get(2);
               String testerName = csvReader.get(4);
               String testName= csvReader.get(3);
               Test test=testHashMap.get(testerName+"/"+taskName+"/"+testName);
               if(test == null) {
                   System.out.println(testerName+"/"+taskName+"/"+testName);
                   continue;
               }
               //todo 等待task.csv格式修改
               test.setStartTime(dateValue(csvReader.get(0),"202021/MM/dd HH:mm:ss"));
               test.setEndTime(dateValue(csvReader.get(1),"21/MM/dd HH:mm:ss"));
               test.setSuccessful(csvReader.get(5));
               test.setErrorCount(doubleValue(csvReader.get(6)));
           }
           csvReader.close();
       } catch (Exception e){
           e.printStackTrace();
       }
   }

   public String getPath(){
       return path;
   }

   public void setPath(String path){
       this.path=path;
   }

   public void upload(){
        uploadProject();
        uploadTask();
        uploadTester();
        uploadTest();
        uploadVideo();
   }

   private void uploadProject(){
       projectRepository.save(project);
   }

   private void uploadTester(){
       testerRepository.saveAll(testerHashMap.values());
   }

   private void uploadTask(){
       taskRepository.saveAll(taskHashMap.values());
   }

   private void uploadTest(){
       if(dataFolder==null)return;
       File[] testerList = dataFolder.listFiles();
       if(testerList==null) return;
       for(File testerFile : testerList){
           Tester tester=testerHashMap.get(testerFile.getName());
           File[] taskList = testerFile.listFiles();
           if(taskList==null) continue;
           for(File taskFile : taskList){
               Task task=taskHashMap.get(taskFile.getName());
               File[] testList = taskFile.listFiles();
               if(testList==null) continue;
               for(File testFile : testList){
                   Test test=testHashMap.get(tester.getName()+"/"+task.getName()+"/"+testFile.getName());
                   testRepository.save(test);
                   uploadTestData(test,testFile);
               }
           }
       }
   }

   private void uploadVideo(){
       if(screenCaptureFolder==null) return;
       File[] testerList = screenCaptureFolder.listFiles();
       if(testerList==null) return;
       for(File testerFile : testerList){
           Tester tester=testerHashMap.get(testerFile.getName());
           File[] taskList = testerFile.listFiles();
           if(taskList==null) continue;
           for(File taskFile : taskList){
               Task task=taskHashMap.get(taskFile.getName());
               File[] testList = taskFile.listFiles();
               if(testList==null) continue;
               for(File testFile : testList){
                   Test test=testHashMap.get(tester.getName()+"/"+task.getName()+"/"+testFile.getName());
                   File[] videoList = testFile.listFiles();
                   if(videoList==null) continue;
                   for(File videoFile : videoList){
                        String url=ossService.uploadFile(videoFile);
                        Video video=new Video();
                        video.setTest(test);
                        video.setName(videoFile.getName());
                        video.setUrl(url);
                        videoRepository.save(video);
                   }
               }
           }
       }
   }

   private void uploadTestData(Test test,File file){
       File[] dataList = file.listFiles();
       if(dataList==null) {
           return;
       }
       for(File dataFile: dataList){
           switch (dataFile.getName()) {
               case "main.csv":
                   uploadMainData(test,dataFile);
                   break;
               case "LogEvent.csv":
                   uploadLogEventData(test,dataFile);
                   break;
               case "InteractionBehaviour.csv":
                   uploadInteractionBehaviourData(test,dataFile);
                   break;
               case "Mark.csv":
                   uploadMarkData(test,dataFile);
                   break;
               default:
                   break;
           }
       }
   }

    private void uploadMarkData(Test test,File file){
        try {
            CsvReader csvReader = new CsvReader(file.getAbsolutePath(), ',', Charset.forName("GBK"));
            csvReader.readHeaders();
            List<MarkData> list=new LinkedList<>();
            while(csvReader.readRecord()){
                MarkData data=new MarkData();
                data.setTest(test);
                data.setColor(csvReader.get("Color"));
                data.setName(csvReader.get("Name"));
                data.setSystemDate(dateValue(csvReader.get("SystemDate"),"yyyy-MM-dd"));
                data.setSystemTime(dateValue(csvReader.get("SystemTime"),"HH:mm:ss.SSS"));
                data.setMarkInfo(csvReader.get("MarkInfo"));
                data.setLength(doubleValue(csvReader.get("Length")));
                data.setTest(test);
                list.add(data);
            }
            markDataRepository.saveAll(list);
            csvReader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void uploadInteractionBehaviourData(Test test,File file){
        try {
            CsvReader csvReader = new CsvReader(file.getAbsolutePath(), ',', Charset.forName("GBK"));
            csvReader.readHeaders();
            List<InteractionBehaviourData> list=new LinkedList<>();
            while(csvReader.readRecord()){
                InteractionBehaviourData data=new InteractionBehaviourData();
                data.setTest(test);
                data.setType(csvReader.get("Type"));
                data.setLocation(csvReader.get("Location"));
                data.setElement(csvReader.get("Element"));
                data.setStartTime(dateValue(csvReader.get("StartTime"),"yyyy-MM-dd HH:mm:ss.SSS"));
                data.setEndTime(dateValue(csvReader.get("EndTime"),"yyyy-MM-dd HH:mm:ss.SSS"));
                data.setStartStatus(csvReader.get("StartStatus"));
                data.setEndStatus(csvReader.get("EndStatus"));
                data.setDistanceStartingTime(doubleValue(csvReader.get("DistanceStartingTime")));
                data.setTest(test);
                list.add(data);
            }
            interactionBehaviourDataRepository.saveAll(list);
            csvReader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Double doubleValue(String s) throws NumberFormatException {
       if(s.isEmpty()) return 0.0;
       return Double.valueOf(s);
    }
    /*
    * "yyyy-MM-dd"
    * "HH:mm:ss.SSS"
    * "yyyy-MM-dd HH:mm:ss.SSS"
    */

    private static Date dateValue(String s,String pattern){
       try{
           DateFormat dateFormat= new SimpleDateFormat(pattern);
           return dateFormat.parse(s);
       } catch (Exception e){
           e.printStackTrace();
           return null;
       }
    }

    private void uploadLogEventData(Test test,File file){
        try {
            CsvReader csvReader = new CsvReader(file.getAbsolutePath(), ',', Charset.forName("GBK"));
            csvReader.readHeaders();
            List<LogEventData> list=new LinkedList<>();
            while(csvReader.readRecord()){
                LogEventData data=new LogEventData();
                data.setTest(test);
                data.setType(csvReader.get("Type"));
                data.setFrom(csvReader.get("From"));
                data.setTo(csvReader.get("To"));
                data.setDataTime(dateValue(csvReader.get("Time"),"yyyy-MM-dd HH:mm:ss.SSS"));
                data.setDuration(doubleValue(csvReader.get("Duration")));
                data.setDistanceStartingTime(doubleValue(csvReader.get("DistanceStartingTime")));
                data.setTest(test);
                list.add(data);
            }
            logEventDataRepository.saveAll(list);
            csvReader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

   private void uploadMainData(Test test,File file){
       try {
           CsvReader csvReader = new CsvReader(file.getAbsolutePath(), ',', Charset.forName("GBK"));
           csvReader.readHeaders();
           List<MainData> list=new LinkedList<>();
           while(csvReader.readRecord()){
               MainData data=new MainData();
               data.setTest(test);
               data.setDate(dateValue(csvReader.get("Date"),"yyyy-MM-dd"));
               data.setTime(dateValue(csvReader.get("Time"),"HH:mm:ss.SSS"));
               data.setSpeed(doubleValue(csvReader.get("Speed")));
               data.setAccelerate(doubleValue(csvReader.get("Accelerate")));
               data.setTurnAround(doubleValue(csvReader.get("TurnAround")));
               data.setLeftLineDistance(doubleValue(csvReader.get("LeftLineDistance")));
               data.setRightLineDistance(doubleValue(csvReader.get("RightLineDistance")));
               data.setDistanceStartingTime(doubleValue(csvReader.get("DistanceStartingTime")));
               list.add(data);
           }
           mainDataRepository.saveAll(list);
           csvReader.close();
       } catch (Exception e){
           e.printStackTrace();
       }
   }

   public void clear(){
       deleteDir(zFile);
       deleteDir(dFile);
   }

   private boolean deleteDir(File dir) {
       if (dir.isDirectory()) {
           String[] children = dir.list();
           if(children==null)  return dir.delete();
           for (String child : children) {
               boolean success = deleteDir(new File(dir, child));
               if (!success) {
                   return false;
               }
           }
       }
       return dir.delete();
   }

   private void parseProject(File file){
       project.setName(file.getName());
       project.setCreateTime(new Date());
       project.setOwner(owner);
   }

   private void parseTester(File file){
       File[] tempList = file.listFiles();
       if(tempList==null) return;
       for(File tFile : tempList){
           String name=tFile.getName();
           Tester tester=new Tester();
           tester.setName(name);
           tester.setProject(project);
           tester.setOwner(owner);
           testerHashMap.put(name,tester);
           parseTask(tFile,tester);
       }
   }

   private void parseTask(File file,Tester tester){
       File[] tempList = file.listFiles();
       if(tempList==null) return;
       for(File tFile : tempList){
           String name=tFile.getName();
           Task task = taskHashMap.get(name);
           if(task == null) {
               task = new Task();
               task.setName(name);
               task.setProject(project);
               task.setOwner(owner);
               taskHashMap.put(name, task);
           }
           parseTest(tFile,tester,task);
       }
   }

   private void parseTest(File file,Tester tester,Task task){
       File[] tempList = file.listFiles();
       if(tempList==null) return;
       for(File tFile : tempList){
           String name=tFile.getName();
           Test test=new Test();
           test.setOwner(owner);
           test.setTester(tester);
           test.setTask(task);
           test.setName(name);
           testHashMap.put(tester.getName()+"/"+task.getName()+"/"+test.getName(),test);
       }
   }
   public void display(){
       for(Map.Entry<String,Tester> entry: testerHashMap.entrySet()){
           System.out.println(entry.getKey());
       }
       for(Map.Entry<String,Task> entry: taskHashMap.entrySet()){
           System.out.println(entry.getKey());
       }
   }

    public void setTesterRepository(TesterRepository testerRepository) {
        this.testerRepository = testerRepository;
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TesterRepository getTesterRepository() {
        return testerRepository;
    }

    public TestRepository getTestRepository() {
        return testRepository;
    }

    public void setTestRepository(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Project getProject(){
       return project;
    }

    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public MainDataRepository getMainDataRepository() {
        return mainDataRepository;
    }

    public void setMainDataRepository(MainDataRepository mainDataRepository) {
        this.mainDataRepository = mainDataRepository;
    }

    public void setInteractionBehaviourDataRepository(InteractionBehaviourDataRepository interactionBehaviourDataRepository) {
        this.interactionBehaviourDataRepository = interactionBehaviourDataRepository;
    }

    public void setVideoRepository(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public void setOssService(OSSService ossService) {
        this.ossService = ossService;
    }

    public MarkDataRepository getMarkDataRepository() {
        return markDataRepository;
    }

    public void setMarkDataRepository(MarkDataRepository markDataRepository) {
        this.markDataRepository = markDataRepository;
    }

    public LogEventDataRepository getLogEventDataRepository() {
        return logEventDataRepository;
    }

    public void setLogEventDataRepository(LogEventDataRepository logEventDataRepository) {
        this.logEventDataRepository = logEventDataRepository;
    }
}
