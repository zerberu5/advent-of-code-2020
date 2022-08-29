package days;

// Problem Description: https://adventofcode.com/2020/day/3
public class Day03 {

    public static void main(String[] args) {
        String[] area = {
                "..##.......", "#...#...#..", ".#....#..#.", "..#.#...#.#", ".#...##..#.", "..#.##.....", ".#.#.#....#", ".#........#", "#.##...#...", "#...##....#", ".#..#...#.#"
        };

        System.out.println(countTreesPart2(area));
    }

    public static long countTreesPart1(String[] area) {
        int right = 0;
        int treeCount = 0;

        for (int i = 1; i < area.length; i++) {
            right = right + 3;

            // prevent out of bounds exception and start from beginning
            if (right >= area[i].length()) {
                right = right - area[i].length();
            }
            if (area[i].charAt(right) == '#') {
                treeCount++;
            }
        }
        return treeCount;
    }

    public static long countTreesPart2(String[] area) {
        int[] rightIncr = {1, 3, 5, 7, 1};
        int[] downIncr = {1, 1, 1, 1, 2};
        int[] counts = new int[rightIncr.length];

        for (int i = 0; i < rightIncr.length; i++) {
            int treeCount = 0;
            int right = 0;

            for (int j = downIncr[i]; j < area.length; j = j + downIncr[i]) {
                right = right + rightIncr[i];

                // prevent out of bounds exception and start from beginning
                if (right >= area[j].length()) {
                    right = right - area[j].length();
                }
                if (area[j].charAt(right) == '#') {
                    treeCount++;
                }
            }
            counts[i] = treeCount;
        }

        long result = 1;
        for (int count : counts) {
            result *= count;
        }

        return result;
    }
}
