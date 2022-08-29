package days;

import resources.AdventDataReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Refactor spaghetti lol
public class Day08 {

    public static void main(String[] args) throws IOException {
        String inputData = AdventDataReader.readFromInputStream("Day08_01.txt");

        List<Instruction> instructions = formatInputData(inputData);

//      int loopAccumulator = getLastAccBeforeLoop(originalInstructions);
//      System.out.println(loopAccumulator);

        int countChangableOps = getCountOfChangableOperations(instructions);
        int changeCount = 0;

        boolean isLooped = containsLoop(instructions);
        while (isLooped && changeCount < countChangableOps) {
            changeCount++;
            instructions = formatInputData(inputData);

            List<Instruction> changedInstructions = changeInstruction(instructions, changeCount);

            for (Instruction instr : changedInstructions) {
                instr.setExecuted(false);
            }

            isLooped = containsLoop(changedInstructions);
        }

        for (Instruction instr : instructions) {
            instr.setExecuted(false);
        }

        int terminatedAccumulator = getLastAccAfterTermination(instructions);
        System.out.println(terminatedAccumulator);
    }

    private static int getCountOfChangableOperations(List<Instruction> originalInstructions) {
        return (int) originalInstructions.stream().filter(instr -> instr.getOperation().equals("jmp") || instr.getOperation().equals("nop")).count();
    }

    private static List<Instruction> changeInstruction(List<Instruction> instructions, int changeCount) {
        int count = 0;
        for (Instruction instr : instructions) {
            if (instr.getOperation().equals("jmp") || instr.getOperation().equals("nop")) {
                count++;
                if (count == changeCount) {
                    if (instr.getOperation().equals("jmp")) {
                        instr.setOperation("nop");
                    } else if (instr.getOperation().equals("nop")) {
                        instr.setOperation("jmp");
                    }
                    break;
                }
            }
        }
        return instructions;
    }

    private static int getLastAccAfterTermination(List<Instruction> instructions) {
        int accumulator = 0;
        for (int i = 0; i < instructions.size(); i++) {
            Instruction instr = instructions.get(i);
            if (instr.getOperation().equals("acc")) {
                accumulator += instr.getArgument();
            }
            if (instr.getOperation().equals("jmp")) {
                i += instructions.get(i).getArgument() - 1;
            }
        }

        return accumulator;
    }

    private static boolean containsLoop(List<Instruction> instructions) {
        for (int i = 0; i < instructions.size(); i++) {
            Instruction instr = instructions.get(i);
            if ((instr.getOperation().equals("nop") || instr.getOperation().equals("acc")) && instr.isExecuted()) {
                return true;
            }
            if (instr.getOperation().equals("jmp")) {
                // -1, because loop adds one after iteration
                if (instr.isExecuted()) {
                    return true;
                }
                i += instructions.get(i).getArgument() - 1;
            }
            instr.setExecuted(true);

            // last instruction is only legit if it's a jump
            if (i == instructions.size() - 1 && !instructions.get(i).getOperation().equals("jmp")) {
                return false;
            }
        }

        return false;
    }

    private static int getLastAccBeforeLoop(List<Instruction> instructions) {
        int accumulator = 0;
        for (int i = 0; i < instructions.size(); i++) {
            Instruction instr = instructions.get(i);
            if (instr.getOperation().equals("acc")) {
                if (instr.isExecuted()) {
                    break;
                } else {
                    instr.setExecuted(true);
                }
                accumulator += instr.getArgument();
            }
            if (instr.getOperation().equals("jmp")) {
                i += instructions.get(i).getArgument() - 1;
            }
        }

        return accumulator;
    }

    private static List<Instruction> formatInputData(String inputData) {
        String[] data = inputData.split("[ \n]");

        List<Instruction> instructions = new ArrayList<>();
        for (int i = 0; i < data.length; i += 2) {
            instructions.add(new Instruction(data[i], Integer.parseInt(data[i + 1])));
        }
        return instructions;
    }
}

class Instruction {
    private String operation;
    private int argument;
    private boolean executed;

    public Instruction(String operation, int argument) {
        this.operation = operation;
        this.argument = argument;
        this.executed = false;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getArgument() {
        return argument;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
}
