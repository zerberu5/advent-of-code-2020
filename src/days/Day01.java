package days;

// Problem Description: https://adventofcode.com/2020/day/1
public class Day01 {

    public static void main(String[] args) {
        int[] vals = {
                1721,
                979,
                366,
                299,
                675,
                1456
        };

        System.out.println(getProductOfValidPairPart2(vals));
    }

    public static int getProductOfValidPairPart1(int[] vals) {

        int summand1 = 0;
        int summand2 = 0;
        for (int i = 0; i < vals.length; i++) {
            for (int j = i; j < vals.length; j++) {
                if (vals[i] + vals[j] == 2020) {
                    summand1 = vals[i];
                    summand2 = vals[j];
                    break;
                }
            }
        }
        return summand1 * summand2;
    }

    // Brute-Force, Refactor by presorting, hashset .. ?
    public static int getProductOfValidPairPart2(int[] vals) {

        int summand1 = 0;
        int summand2 = 0;
        int summand3 = 0;
        for (int i = 0; i < vals.length; i++) {
            for (int j = i; j < vals.length; j++) {
                for (int k = j; k < vals.length; k++) {
                    if (vals[i] + vals[j] + vals[k] == 2020) {
                        summand1 = vals[i];
                        summand2 = vals[j];
                        summand3 = vals[k];
                        break;
                    }
                }
            }
        }
        return summand1 * summand2 * summand3;
    }
}
