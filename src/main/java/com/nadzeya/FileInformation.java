package com.nadzeya;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileInformation {
    ArgumentsAnalyser argumentsAnalyser;
    String statisticType;
    Map<String, Statistic> typeStatisticMap = new HashMap<>();
    Map<String, String> typeFileNameMap = new HashMap<>();
    boolean append;

    {
        typeFileNameMap.put("int", "integers.txt");
        typeFileNameMap.put("floats", "floats.txt");
        typeFileNameMap.put("string", "strings.txt");
    }

    String path;
    List<String> dataFiles;

    public FileInformation(ArgumentsAnalyser argumentsAnalyser) {
        this.argumentsAnalyser = argumentsAnalyser;
        this.dataFiles = argumentsAnalyser.getFiles();
        this.path = argumentsAnalyser.getPath();
        this.statisticType = argumentsAnalyser.getStatisticType();
        this.append= argumentsAnalyser.shouldAppend();
        createStatisticType();
        if (!argumentsAnalyser.getNamePrefix().isEmpty()) {
            createFileNames();
        }

    }

    private void createStatisticType() {
        if (statisticType.equals("-s")) {
            typeStatisticMap.put("int", new ShortStatistic());
            typeStatisticMap.put("float", new ShortStatistic());
            typeStatisticMap.put("string", new ShortStatistic());
        } else {
            typeStatisticMap.put("int", new FullStatistic());
            typeStatisticMap.put("float", new FullStatistic());
            typeStatisticMap.put("string", new FullStatistic());
        }

/*
        if(argumentsAnalyser.getStatisticType().equals("-s")){
            for(Statistic s: statistic){
                s=new ShortStatistic();
            }
        }
        else{
            for(Statistic s: statistic){
                s=new FullStatistic();
            }
        }*/
    }

    private void createFileNames() {
        String prefix = argumentsAnalyser.getNamePrefix();
        typeFileNameMap.replaceAll((k, v) -> prefix + typeFileNameMap.get(k));
    }

    public void analyseFiles() throws IOException {
        for (String file:dataFiles)
            analyseFile(file);

    }

    private void analyseFile(String file) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String str;
            while((str = fileReader.readLine())!= null){
                System.out.println("str = " + str);
                String type = checkType(str);
                System.out.println(type);
                addToResultFile(str, typeFileNameMap.get(type));
                typeStatisticMap.get(type).updateStatistics();
            }

        } catch (IOException e) {
            System.out.println("К сожалению, файл " + file + " не найден.");
        }

    }

    private String checkType(String s) {
        if(s.matches("^-?\\d+$"))
            return "int";
        if(s.matches("^-?\\d+(?:\\.\\d+)?(?:[eE][-+]?\\d+)?$"))
            return "float";
        return "string";
    }

    private void addToResultFile(String s, String resultFile) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path+resultFile, append))){
            writer.write(s+"\n");
        }
        catch (IOException e){
            System.out.println("sorry");
        }
        finally {
            append=true;
        }
    }
}
