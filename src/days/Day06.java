package days;

import java.util.*;

public class Day06 {

    public static void main(String[] args) {

        String unformattedYesBatch = "abc\n" +
                "\n" +
                "a\n" +
                "b\n" +
                "c\n" +
                "\n" +
                "ab\n" +
                "ac\n" +
                "\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "\n" +
                "b";

        List<List<String>> yesBatch = formatYesBatch(unformattedYesBatch);
        System.out.println(countAllYesPart(yesBatch));
    }

    // Refactor o(n³)
    private static int countAllYesPart(List<List<String>> yesBatch) {
        List<Integer> countsPerGroup = new ArrayList<>();

        for (List<String> group : yesBatch) {
            String mostAnswered = Collections.max(group, Comparator.comparing(String::length));

            int groupCount = 0;

            for (int i = 0; i < mostAnswered.length(); i++) {
                int memberCount = 0;
                char keyChar = mostAnswered.charAt(i);
                for (String member : group) {
                    if (member.indexOf(keyChar) != -1) {
                        memberCount++;
                    }
                }
                if (memberCount == group.size()) {
                    groupCount++;
                }
            }
            countsPerGroup.add(groupCount);
        }
        return countsPerGroup.stream().mapToInt(Integer::intValue).sum();
    }

    private static List<List<String>> formatYesBatch(String yesBatch) {
        String[] groupListUnformatted = yesBatch.split("\n\n");
        List<List<String>> groupList = new ArrayList<>();
        for (String group : groupListUnformatted) {
            List<String> singleMembers = new ArrayList<>(Arrays.asList(group.split("\n")));
            groupList.add(singleMembers);
        }
        return groupList;
    }

//    private static int countAllYesPart1(List<String> yesBatch) {
//        List<Integer> countsPerGroup = new ArrayList<>();
//        for (int i = 0; i < yesBatch.size(); i++) {
//            countsPerGroup.add((int) yesBatch.get(i).chars().distinct().count());
//        }
//        return countsPerGroup.stream().mapToInt(Integer::intValue).sum();
//    }

//    private static List<String> formatYesBatch(String yesBatch) {
//        List<String> yesList = Arrays.asList(yesBatch.split("\n\n"));
//        return yesList.stream().map(str -> str.replace("\n", "")).collect(Collectors.toList());
//    }


}
