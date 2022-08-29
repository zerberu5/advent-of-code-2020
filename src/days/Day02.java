package days;

// Problem Description: https://adventofcode.com/2020/day/2
public class Day02 {

    public static void main(String[] args) {
        String[] pwAndRules = {
                "1-3 a: abcde",
                "1-3 b: cdefg",
                "2-9 c: ccccccccc"
        };

        System.out.println(countValidPasswordsPart1(pwAndRules));
    }

    public static int countValidPasswordsPart1(String[] pwAndRules) {

        int pwCount = 0;
        for (String pwAndRule : pwAndRules) {
            String[] splitPwAndRules = pwAndRule.replaceAll("[a-z]|:| ", "").split("-");
            int minOcc = Integer.parseInt(splitPwAndRules[0]);
            int maxOcc = Integer.parseInt(splitPwAndRules[1]);

            int colonIndex = pwAndRule.indexOf(':');
            char pwKey = pwAndRule.charAt(colonIndex - 1);

            String password = pwAndRule.substring(colonIndex + 2);

            int occurrences = (int) password.chars().filter(key -> key == pwKey).count();
            if (occurrences >= minOcc && occurrences <= maxOcc) {
                pwCount++;
            }
        }
        return pwCount;
    }

    public static int countValidPasswordsPart2(String[] pwAndRules) {

        int pwCount = 0;
        for (String pwAndRule : pwAndRules) {
            String[] splitPwAndRules = pwAndRule.replaceAll("[a-z]|:| ", "").split("-");
            int firstPos = Integer.parseInt(splitPwAndRules[0]) - 1;
            int secondPos = Integer.parseInt(splitPwAndRules[1]) - 1;

            int colonIndex = pwAndRule.indexOf(':');
            char pwKey = pwAndRule.charAt(colonIndex - 1);

            String password = pwAndRule.substring(colonIndex + 2);
            if (password.charAt(firstPos) == pwKey ^ password.charAt(secondPos) == pwKey) {
                pwCount++;
            }

        }
        return pwCount;
    }
}
