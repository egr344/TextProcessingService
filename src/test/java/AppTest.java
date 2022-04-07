import org.junit.jupiter.api.Test;
import Service.TextProcessingService;
import org.junit.jupiter.api.Assertions;

import java.util.*;

public class AppTest {


    @Test
    void testFindGroupInSet() {
        var expected = initializeExpectedData();
        var actual = TextProcessingService.findGroupInSet(initializeTestData());
        Assertions.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertArrayEquals(expected.get(i).toArray(),actual.get(i).toArray());
        }
    }

    private List<Set<String>> initializeExpectedData(){
        var expected = new ArrayList<Set<String>>();
        var group1 = new HashSet<String>();
        var group2 = new HashSet<String>();
        group1.add("AA;BB;CC");
        group1.add("DD;EE;FF");
        group1.add("GG;HH;KK");
        group1.add("AA;EE;KK");
        group2.add("a;g;h");
        expected.add(group1);
        expected.add(group2);
        return expected;
    }

    private Set<String> initializeTestData(){
        Set<String> testData = new TreeSet<>();
        testData.add("AA;BB;CC");
        testData.add("DD;EE;FF");
        testData.add("GG;HH;KK");
        testData.add("a;g;h");
        testData.add("AA;EE;KK");
        return testData;
    }
}
