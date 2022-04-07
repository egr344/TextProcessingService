import packege.TextProcessingService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Set<String> uniqueLine = TextProcessingService.readFile("src/main/resources/InputFile/lng.csv", StandardCharsets.UTF_8);
            List<Set<String>> listGroup = TextProcessingService.findGroupInSet(uniqueLine);
            listGroup.sort((set1,set2)-> Integer.compare(set1.size(),set2.size()) * -1);
            long countGroupUpperOneSize = listGroup.stream().filter(set -> set.size() > 1).count();
            TextProcessingService.writeResult(countGroupUpperOneSize,listGroup,"src/main/resources/OutputFile/result.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
