package days;

import java.util.*;

// Problem Description: https://adventofcode.com/2020/day/4
public class Day04 {

    public static void main(String[] args) {
        String passportBatch = "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\n" +
                "byr:1937 iyr:2017 cid:147 hgt:183cm\n" +
                "\n" +
                "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884\n" +
                "hcl:#cfa07d byr:1929\n" +
                "\n" +
                "hcl:#ae17e1 iyr:2013\n" +
                "eyr:2024\n" +
                "ecl:brn pid:760753108 byr:1931\n" +
                "hgt:179cm\n" +
                "\n" +
                "hcl:#cfa07d eyr:2025 pid:166559648\n" +
                "iyr:2011 ecl:brn hgt:59in";

        System.out.println(countValidPassports(passportBatch));
    }

    public static int countValidPassports(String passportBatch) {
        int passportCount = 0;
        List<Map<String, String>> formattedPassports = formatPassports(passportBatch);

        for (Map<String, String> passport : formattedPassports) {
            int conditionsCount = 0;
            if (passport.size() == 8 || (passport.size() == 7 && !passport.containsKey("cid"))) {

                for (Map.Entry<String, String> entry : passport.entrySet()) {
                    String passportField = entry.getKey();
                    String passportFieldValue = entry.getValue();

                    if (isValidCondition(passportField, passportFieldValue)) {
                        conditionsCount++;
                    } else {
                        break;
                    }
                }
            }

            if (conditionsCount == passport.size()) {
                passportCount++;
            }
        }

        return passportCount;
    }

    private static boolean isValidCondition(String passportField, String passportFieldValue) {

        switch(passportField){
            case "byr":
                return Integer.parseInt(passportFieldValue) >= 1920 && Integer.parseInt(passportFieldValue) <= 2002;
            case "iyr":
                return Integer.parseInt(passportFieldValue) >= 2010 && Integer.parseInt(passportFieldValue) <= 2020;
            case "eyr":
                return Integer.parseInt(passportFieldValue) >= 2020 && Integer.parseInt(passportFieldValue) <= 2030;
            case "hgt":
                // Height: If cm, the number must be at least 150 and at most 193. *OR* If in, the number must be at least 59 and at most 76.
                return  passportFieldValue.matches("^1(([5][0-9])|([6-8][0-9])|([9][0-3]))cm$") || passportFieldValue.matches("^((59)|(6[0-9])|(7[0-6]))in$");
            case "hcl":
                // Hair Color: a # followed by exactly six characters 0-9 or a-f
                return passportFieldValue.length() == 7 && passportFieldValue.matches("^#([a-f0-9]{6}|[a-f0-9]{3})$");
            case "ecl":
                List<String> eyeColors = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
                return eyeColors.contains(passportFieldValue);
            case "pid":
                // Passport id: between 0-9, with 9 digits
                return passportFieldValue.matches("^[0-9]{9}$");
            case "cid":
                // true, because this criteria doesn't matter at all IF it exists
                return true;
            default:
                return false;
        }
    }

    private static List<Map<String, String>> formatPassports(String passportBatch) {
        String[] splitPassports = passportBatch.split("\n\n");

        List<Map<String, String>> formattedPassports = new ArrayList<>();

        for (String unformattedPassport : splitPassports) {
            String[] passportParts = unformattedPassport.split("[: \\n]");
            Map<String, String> formattedPassport = new HashMap<>();
            for (int i = 0; i < passportParts.length; i = i + 2) {
                formattedPassport.put(passportParts[i], passportParts[i + 1]);
            }
            formattedPassports.add(formattedPassport);
        }

        return formattedPassports;
    }
}
