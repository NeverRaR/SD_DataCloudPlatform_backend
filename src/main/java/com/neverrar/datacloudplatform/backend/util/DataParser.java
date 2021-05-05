package com.neverrar.datacloudplatform.backend.util;

import com.alibaba.fastjson.JSON;
import com.csvreader.CsvReader;
import com.neverrar.datacloudplatform.backend.model.*;
import com.neverrar.datacloudplatform.backend.repository.*;
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
import java.text.SimpleDateFormat;
import java.util.*;

import net.lingala.zip4j.core.ZipFile;

public class DataParser {

   private final HashMap<String,Tester> testerHashMap=new HashMap<>();

   private final HashMap<String,Task> taskHashMap=new HashMap<>();

   private final Project project=new Project();

   private User owner;

   private String path;

   private File zFile;

   private File dFile;

   private File dataFolder;

   private ProjectRepository projectRepository;

   private TesterRepository testerRepository;

   private TaskRepository taskRepository;

   private TestRepository testRepository;

   private MainDataRepository mainDataRepository;

   private InteractionBehaviourDataRepository interactionBehaviourDataRepository;

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
                   parseTester(dataFile);
                   dataFolder=dataFile;
                   break;
               default:
                   break;
           }
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
       File[] testerList = dataFolder.listFiles();
       if(testerList==null) return;
       for(File testerFile : testerList){
           Tester tester=testerHashMap.get(testerFile.getName());
           File[] taskList = testerFile.listFiles();
           for(File taskFile : taskList){
               Task task=taskHashMap.get(taskFile.getName());
               File[] testList = taskFile.listFiles();
               for(File testFile : testList){
                   Test test=new Test();
                   test.setOwner(owner);
                   test.setTester(tester);
                   test.setTask(task);
                   test.setTestTime(new Date());
                   testRepository.save(test);
                   uploadTestData(test,testFile);
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

    }

    private void uploadInteractionBehaviourData(Test test,File file){
        FileWriter fWriter = null;
        try {
            CsvReader csvReader = new CsvReader(file.getAbsolutePath(), ',', Charset.forName("GBK"));
            csvReader.readHeaders();
            AllInteractionBehaviourDataByTest allInteractionBehaviourDataByTest=new AllInteractionBehaviourDataByTest();
            allInteractionBehaviourDataByTest.setTestId(test.getId());
            List<InteractionBehaviourDataInformation> list=new LinkedList<>();
            InteractionBehaviourData interactionBehaviourData=new InteractionBehaviourData();
            interactionBehaviourData.setTest(test);
            while(csvReader.readRecord()){
                InteractionBehaviourDataInformation info=new InteractionBehaviourDataInformation();
                list.add(info);
                info.setType(csvReader.get("Type"));
                info.setLocation(csvReader.get("Location"));
                info.setElement(csvReader.get("Element"));
                info.setStartTime(csvReader.get("StartTime"));
                info.setEndTime(csvReader.get("EndTime"));
                info.setStartStatus(csvReader.get("StartStatus"));
                info.setEndStatus(csvReader.get("EndStatus"));

                info.setDistanceStartingTime(doubleValue(csvReader.get("DistanceStartingTime")));
            }
            allInteractionBehaviourDataByTest.setList(list);
            File jsonFile=new File(file.getAbsolutePath()+".json");
            fWriter = new FileWriter(jsonFile);
            fWriter.write(JSON.toJSONString(allInteractionBehaviourDataByTest));
            interactionBehaviourData.setPath(jsonFile.getAbsolutePath());
            interactionBehaviourDataRepository.save(interactionBehaviourData);
            csvReader.close();
            deleteDir(file);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(fWriter!= null) {
                    fWriter.flush();
                    fWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static Double doubleValue(String s) throws NumberFormatException {
       if(s.isEmpty()) return 0.0;
       return Double.valueOf(s);
    }

    private void uploadLogEventData(Test test,File file){

    }

   private void uploadMainData(Test test,File file){
       FileWriter fWriter = null;
       try {
           CsvReader csvReader = new CsvReader(file.getAbsolutePath(), ',', Charset.forName("GBK"));
           csvReader.readHeaders();
           AllMainDataByTest allMainDataByTest=new AllMainDataByTest();
           allMainDataByTest.setTestId(test.getId());
           List<MainDataInformation> mainList=new LinkedList<>();
           MainData mainData=new MainData();
           mainData.setTest(test);
           while(csvReader.readRecord()){
               MainDataInformation mainDataInfo=new MainDataInformation();
               mainList.add(mainDataInfo);
               mainDataInfo.setDate(csvReader.get("Date"));
               mainDataInfo.setTime(csvReader.get("Time"));
               mainDataInfo.setSpeed(doubleValue(csvReader.get("Speed")));
               mainDataInfo.setAccelerate(doubleValue(csvReader.get("Accelerate")));
               mainDataInfo.setTurnAround(doubleValue(csvReader.get("TurnAround")));
               mainDataInfo.setLeftLineDistance(doubleValue(csvReader.get("LeftLineDistance")));
               mainDataInfo.setRightLineDistance(doubleValue(csvReader.get("RightLineDistance")));
               mainDataInfo.setDistanceStartingTime(doubleValue(csvReader.get("DistanceStartingTime")));
           }
           allMainDataByTest.setList(mainList);
           File jsonFile=new File(file.getAbsolutePath()+".json");
           fWriter = new FileWriter(jsonFile);
           fWriter.write(JSON.toJSONString(allMainDataByTest));
           mainData.setPath(jsonFile.getAbsolutePath());
           mainDataRepository.save(mainData);
           csvReader.close();
           deleteDir(file);
       } catch (Exception e){
           e.printStackTrace();
       } finally {
           try {
               if(fWriter!= null) {
                   fWriter.flush();
                   fWriter.close();
               }
           } catch (IOException ex) {
               ex.printStackTrace();
           }
       }
   }

   public void clear(){
       deleteDir(zFile);
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
           parseTask(tFile);
       }
   }

   private void parseTask(File file){
       File[] tempList = file.listFiles();
       if(tempList==null) return;
       for(File tFile : tempList){
           String name=tFile.getName();
           if(taskHashMap.containsKey(name)) continue;
           Task task=new Task();
           task.setName(name);
           task.setProject(project);
           task.setOwner(owner);
           taskHashMap.put(name,task);
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
}
