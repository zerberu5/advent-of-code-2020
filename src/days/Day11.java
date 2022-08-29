package days;

import resources.AdventDataReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day11 {

    public static void main(String[] args) throws IOException {
        String inputData = AdventDataReader.readFromInputStream("Day11_02.txt");
        List<List<Character>> seatLayout = formatInputData(inputData);

        stabilizeSeatLayout(seatLayout);
        System.out.println(countSumOfOccupiedSeats(seatLayout));
    }

    private static int countSumOfOccupiedSeats(List<List<Character>> seatLayout) {
        int sum = 0;
        for (List<Character> row : seatLayout) {
            for (char seat : row) {
                if (seat == '#') {
                    sum++;
                }
            }
        }
        return sum;
    }

    private static void stabilizeSeatLayout(List<List<Character>> seatLayout) {

        printLayout(seatLayout);

        List<List<Character>> seatLayoutCopy = new ArrayList<>();
        copyArrayList(seatLayoutCopy, seatLayout);

        boolean isStateChanged = false;
        while (!isStateChanged) {

            List<Update> seatsToBeUpdated = new ArrayList<>();
            for (int rowI = 0; rowI < seatLayout.size(); rowI++) {

                List<Character> rowUp = rowI > 0 ? seatLayout.get(rowI - 1) : null;
                List<Character> row = seatLayout.get(rowI);
                List<Character> rowDown = rowI < seatLayout.size() - 1 ? seatLayout.get(rowI + 1) : null;

                for (int seatJ = 0; seatJ < row.size(); seatJ++) {

                    if (row.get(seatJ) != '.')
                        seatsToBeUpdated.add(checkSeat(rowUp, row, rowDown, seatJ, rowI, row.get(seatJ), row.size()));

                }

            }
            updateLayout(seatsToBeUpdated, seatLayout);
            isStateChanged = stateChanged(seatLayout, seatLayoutCopy);
            copyArrayList(seatLayoutCopy, seatLayout);
        }
        printLayout(seatLayout);
    }

    private static boolean stateChanged(List<List<Character>> seatLayout, List<List<Character>> seatLayoutCopy) {
        return seatLayout.equals(seatLayoutCopy);
    }

    private static void updateLayout(List<Update> seatsToBeUpdated, List<List<Character>> seatLayout) {
        for (Update update : seatsToBeUpdated) {
            if (update != null) seatLayout.get(update.getRow()).set(update.getSeat(), update.getState());
        }
    }

    // If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
    // If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
    private static Update checkSeat(List<Character> rowUp, List<Character> row, List<Character> rowDown, int seatJ, int rowI, char state, int limit) {

        int count = 0;

        // right
        if (seatJ < limit - 1 && row.get(seatJ + 1) == '#') count++;
        // left
        if (seatJ > 0 && row.get(seatJ - 1) == '#') count++;
        // up
        if (rowUp != null && rowUp.get(seatJ) == '#') count++;
        // down
        if (rowDown != null && rowDown.get(seatJ) == '#') count++;

        // up right
        if (rowUp != null && seatJ < limit - 1 && rowUp.get(seatJ + 1) == '#') count++;
        // up left
        if (rowUp != null && seatJ > 0 && rowUp.get(seatJ - 1) == '#') count++;
        // down left
        if (rowDown != null && seatJ < limit - 1 && rowDown.get(seatJ + 1) == '#') count++;
        // down right
        if (rowDown != null && seatJ > 0 && rowDown.get(seatJ - 1) == '#') count++;

        Update update = null;
        if (state == 'L' && count == 0) {
            update = new Update(rowI, seatJ, '#');
        }

        if (state == '#' && count >= 4) {
            update = new Update(rowI, seatJ, 'L');
        }
        return update;
    }

    private static void printLayout(List<List<Character>> seatLayout) {

        for (List<Character> row : seatLayout) {
            for (Character seat : row) {
                System.out.print(seat);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static List<List<Character>> formatInputData(String inputData) {
        String[] seatRows = inputData.split("\n");

        List<List<Character>> seatLayout = new ArrayList<>();

        for (String seatLine : seatRows) {
            char[] seatsLineArr = seatLine.toCharArray();
            List<Character> seats = new ArrayList<>();

            for (char seat : seatsLineArr) {
                seats.add(seat);
            }
            seatLayout.add(seats);
        }

        return seatLayout;
    }

    private static void copyArrayList(List<List<Character>> copy, List<List<Character>> original) {
        copy.clear();
        for (List<Character> characters : original) {
            List<Character> chars = new ArrayList<>(characters);
            copy.add(chars);
        }
    }
}

class Update {

    private int row;
    private int seat;
    private char state;

    public Update(int row, int seat, char state) {
        this.row = row;
        this.seat = seat;
        this.state = state;
    }

    public int getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }
}
