package ru.sberbank.school.task07;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class DifferentWordsImpl implements DifferentWords<Set> {
    @Override
    public Set findSortedDifferentWords(String pathToFile) throws FileNotFoundException {

        List<String> list = new FileParserImpl().parse(pathToFile);
        Comparator<String> comparator = (o1, o2) -> {
            if (o1.length() > o2.length()) {
                return 1;
            }
            if (o1.length() == o2.length()) {
                return o1.compareToIgnoreCase(o2);
            }
            return -1;
        };

        Set<String> set = new TreeSet<>(comparator);
        for (String str : list) {
            set.addAll(Arrays.asList(str.trim().toLowerCase().split(" ")));
        }
        return set;
    }
}
