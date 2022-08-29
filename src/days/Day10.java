package days;

import resources.AdventDataReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {

    public static void main(String[] args) throws IOException {
        String inputData = AdventDataReader.readFromInputStream("Day10_02.txt");

        List<Integer> jolts = formatInputData(inputData);

        // Treat the charging outlet near your seat as having an effective joltage rating of 0.
        jolts.add(0);
        // Your device has a built-in joltage adapter rated for 3 jolts higher than the highest-rated adapter in your bag.
        jolts.add(Collections.max(jolts) + 3);

        System.out.println(productOfJoltDiffernces(jolts));

        long sumOfAllCombinations = getSumOfAllCombinations(jolts);
        System.out.println(sumOfAllCombinations);
    }

    private static long getSumOfAllCombinations(List<Integer> jolts) {

        // Sums of combinations per adapter
        List<Long> sums = new ArrayList<>();

        initializeAirplaneSocket(jolts, sums);

        // initialization of first two entries to avoid unnecessary if-statements in loop, which would otherwise require out ouf bounds checks
        sums.set(1, sums.get(0));
        sums.set(2, sums.get(0) + sums.get(1));

        for (int i = 3; i < sums.size(); i++) {
            if (jolts.contains(i)) {
                long sum = sums.get(i - 1) + sums.get(i - 2) + sums.get(i - 3);
                sums.set(i, sum);
            } else {
                sums.set(i, 0L);
            }
        }

        // last value is the sum of all combination
        return sums.get(sums.size() - 1);
    }

    // Treat the charging outlet near your seat as having an effective joltage rating of 0
    private static void initializeAirplaneSocket(List<Integer> jolts, List<Long> sums) {
        for (int i = 0; i <= jolts.get(jolts.size() - 1); i++) {
            if (i == 0) {
                sums.add(i, 1L);
                continue;
            }
            sums.add(i, 0L);
        }
    }

    private static int productOfJoltDiffernces(List<Integer> jolts) {
        int sum1 = 0;
        int sum3 = 0;

        Collections.sort(jolts);

        int currentJolt = jolts.get(0);

        for (int i = 1; i < jolts.size(); i++) {
            int diff = jolts.get(i) - currentJolt;
            currentJolt = diff + currentJolt;

            if (diff == 3) sum3++;
            else sum1++;
        }

        return sum1 * sum3;
    }

    private static List<Integer> formatInputData(String inputData) {
        return Arrays.stream(inputData.split("\n")).map(Integer::valueOf).collect(Collectors.toList());
    }
}
