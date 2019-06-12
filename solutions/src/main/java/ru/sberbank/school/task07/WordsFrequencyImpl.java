package ru.sberbank.school.task07;

import ru.sberbank.school.task06.CountMapImpl;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WordsFrequencyImpl implements WordFrequency<HashMap> {
    @Override
    public HashMap countWords(String pathToFile) throws FileNotFoundException {
        List<String> stringsFile = new FileParserImpl().parse(pathToFile);
        List<String> wordsString = null;
        CountMapImpl countMap = new CountMapImpl();
        for(String str : stringsFile) {
            wordsString = Arrays.asList(str.toLowerCase().trim().split(" "));
            for (String word : wordsString) {
                countMap.add(word);
            }
        }
        return (HashMap) countMap.toMap();
    }
}