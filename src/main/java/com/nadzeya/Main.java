package com.nadzeya;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    
    public static void main(String[] args) throws IOException {

        ArgumentsAnalyser argumentsAnalyser = new ArgumentsAnalyser();
        argumentsAnalyser.analyseArgs(args);

        FileInformation fileInformation = new FileInformation(argumentsAnalyser);
        fileInformation.analyseFiles();


        //TODO Проверка наличия аргументов для опций
        //TODO Обработка некорректных аргументов:
        //TODO Обработка исключений
    }
}
