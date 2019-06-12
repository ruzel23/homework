package ru.sberbank.school.task07;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CounterImpl implements Counter {

    @Override
    public int count(String pathToFile) throws FileNotFoundException {
        List<String> list = new FileParserImpl().parse(pathToFile);
        Set<String> countDifWord = new HashSet<>();
        for (String str : list) {
            countDifWord.addAll(Arrays.asList(str.trim().toLowerCase().split(" ")));
        }
        return countDifWord.size();
    }
}
