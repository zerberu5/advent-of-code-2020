package days;

import resources.AdventDataReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day09 {

    public static void main(String[] args) throws IOException {
        String inputData = AdventDataReader.readFromInputStream("Day09_02.txt");

        List<Long> vals = formatInputData(inputData);
        int limit = 25;
        long corruptedVal = getCorruptedValue(vals, limit);
        long minMaxSumCorruptedWeakness = getMinMaxSumOfCorruptedWeakness(vals, corruptedVal);

        System.out.println(minMaxSumCorruptedWeakness);
    }

    private static long getMinMaxSumOfCorruptedWeakness(List<Long> vals, long corruptedVal) {
        List<Long> corruptedWeakness = getCorruptedWeakness(vals, corruptedVal);
        return Collections.max(corruptedWeakness) + Collections.min(corruptedWeakness);
    }

    private static List<Long> getCorruptedWeakness(List<Long> vals, long corruptedVal) {
        boolean weaknessFound = false;
        int start = 0;
        int end = 0;

        for (int i = 0; i < vals.size(); i++) {
            long sum = vals.get(i);
            for (int j = i + 1; j < vals.size() || sum < corruptedVal; j++) {
                // accumulate
                sum += vals.get(j);

                if (sum == corruptedVal) {
                    start = i;
                    end = j;
                    weaknessFound = true;
                    break;
                }
            }
            if (weaknessFound) break;
        }

        return vals.subList(start, end);
    }

    private static long getCorruptedValue(List<Long> vals, int limit) {
        boolean match;

        for (int i = limit; i < vals.size(); i++) {
            match = false;
            for (int j = i - limit; j < i; j++) {
                for (int k = j + 1; k < i; k++) {
                    if (vals.get(j) + vals.get(k) == vals.get(i)) {
                        match = true;
                        break;
                    }
                }
                if (match) break;
            }
            if (!match) {
                return vals.get(i);
            }
        }

        return 0;
    }

    private static List<Long> formatInputData(String inputData) {
        return Arrays.stream(inputData.split("\n")).map(Long::valueOf).collect(Collectors.toList());
    }

}
