package Service;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class TextProcessingService {

    public static Set<String> readFile(String path, Charset charset) throws IOException {
            return Files.lines(Paths.get(path), charset)
                    .filter(line -> line.split(";").length == 3)
                    .collect(Collectors.toSet());
    }

    public static void writeResult(long countGroupUpperOneSize, List<Set<String>> result, String path) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write("Число групп с более чем одним эллементом: " + countGroupUpperOneSize + "\n");
            for (int i = 0; i < result.size(); i++) {
                writer.write("Группа " + (i + 1) + "\n");
                for (String line : result.get(i)) {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Set<String>> findGroupInSet(Set<String> set) {
        List<Set<String>> group = new ArrayList<>();
        Map<String, Integer[]> dict = new HashMap<>();
        List<Integer> columnsToGroup = new ArrayList<>();
        for (String line : set) {
            var lineArray = line.split(";");
            addInformationInDictAndColumnsToGroup(dict,columnsToGroup,lineArray);
            if (columnsToGroup.isEmpty()) {
                group.add(new HashSet<>());
                group.get(group.size() - 1).add(line);
                replaceGroup(dict, lineArray, group.size() - 1);
            } else {
                columnsToGroup.sort((groupNumber, groupNumber2) -> groupNumber.compareTo(groupNumber2) * -1);
                int first = columnsToGroup.get(columnsToGroup.size() - 1);
                columnsToGroup.stream()
                        .limit(columnsToGroup.size() - 1)
                        .peek(groupNumber -> group.get(first).addAll(group.get(groupNumber)))
                        .forEach(groupNumber -> group.remove(groupNumber.intValue()));
                group.get(first).add(line);
                replaceGroup(dict, lineArray, first);
            }
            columnsToGroup.clear();
        }
        return group;

    }

    private static void replaceGroup(Map<String, Integer[]> dict, String[] line, Integer groupNumber) {
        for (int i = 0; i < line.length; i++) {
            dict.get(line[i])[i] = groupNumber;
        }
    }

    private static void addInformationInDictAndColumnsToGroup(Map<String, Integer[]> dict, List<Integer> columnsToGroup,String[] lineArray){
        for (int i = 0; i < lineArray.length; i++) {
            if (dict.containsKey(lineArray[i])) {
                if (dict.get(lineArray[i])[i] != null)
                    columnsToGroup.add(dict.get(lineArray[i])[i]);
            } else {
                dict.put(lineArray[i], new Integer[lineArray.length]);
            }
        }
    }

}
