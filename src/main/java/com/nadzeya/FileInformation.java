package com.nadzeya;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.*;

public class FileInformation {
    ArgumentsAnalyser argumentsAnalyser;
     List<Statistic> statistic=new ArrayList<>();
     Map<String, Statistic> fileStatisticMap = new HashMap<>();
    Map<String, String> fileNamesMap = new HashMap<>();
    {
        fileNamesMap.put("int", "integers.txt");
        fileNamesMap.put("floats", "floats.txt");
        fileNamesMap.put("string", "strings.txt");
    }

    String path;
    List<String>dataFiles;
    public FileInformation(ArgumentsAnalyser argumentsAnalyser) {
        this.argumentsAnalyser = argumentsAnalyser;
        createStatisticType();
        if(!argumentsAnalyser.getNamePrefix().isEmpty()){
            createFileNames();
        }
        this.dataFiles=argumentsAnalyser.getFiles();
        this.path= argumentsAnalyser.getPath();
    }
    private void createStatisticType(){

        if(argumentsAnalyser.getStatisticType().equals("-s")){
            for(Statistic s: statistic){
                s=new ShortStatistic();
            }
        }
        else{
            for(Statistic s: statistic){
                s=new FullStatistic();
            }
        }
    }
    private void createFileNames(){
        String prefix=argumentsAnalyser.getNamePrefix();
        fileNamesMap.replaceAll((k, v) -> prefix + fileNamesMap.get(k));
    }
    public void analyseFiles() throws IOException {

        for (String file:dataFiles){
            fileStatisticMap.put(file, analyseFile(file));
        }
    }

    private Statistic analyseFile(String file) throws IOException {
        try(BufferedReader fileReader = new BufferedReader(new FileReader(file))){
            String str = fileReader.readLine();
            String type = checkType(str);
            switch (type){
                case "int":
                    addToResultFile(str, fileNamesMap.get(type));








                    break;
                case "float":
                    break;
                case "string":
                    break;
                default:
                    System.out.println("Извините, произошла непредвиденная ошибка. Строка " + str);
            }
        }
        catch (IOException e){
            System.out.println("К сожалению, файл " + file + " не найден.");
        }

        return null;
    }
    private String checkType(String s){

    }
    private void addToResultFile(String s, String resultFile){

    }
    //считывать инфу оттуда
    //создать файл если его нет
}
