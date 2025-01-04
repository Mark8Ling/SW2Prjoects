import components.statement.Statement;
import components.statement.StatementKernel.Condition;

/**
 * Utility class with method to count the number of calls to primitive
 * instructions (move, turnleft, turnright, infect, skip) in a given
 * {@code Statement}.
 *
 * @author Put your name here
 *
 */
public final class CountPrimitiveCalls {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CountPrimitiveCalls() {
    }

    /**
     * Reports the number of calls to primitive instructions (move, turnleft,
     * turnright, infect, skip) in a given {@code Statement}.
     *
     * @param s
     *            the {@code Statement}
     * @return the number of calls to primitive instructions in {@code s}
     * @ensures <pre>
     * countOfPrimitiveCalls =
     *  [number of calls to primitive instructions in s]
     * </pre>
     */
    public static int countOfPrimitiveCalls(Statement s) {
        int count = 0;
        switch (s.kind()) {
            case BLOCK: {

                for (int i = 0; i < s.lengthOfBlock(); i++) {

                    Statement subStatement = s.removeFromBlock(i);

                    count += countOfPrimitiveCalls(subStatement);

                    s.addToBlock(i, subStatement);

                }
                break;
            }
            case IF: {
                Statement body = s.newInstance();

                Condition t = s.disassembleIf(body);

                count += countOfPrimitiveCalls(body);

                s.assembleIf(t, body);
                break;
            }
            case IF_ELSE: {
                Statement thenBlock = s.newInstance();
                Statement elseBlock = s.newInstance();

                Condition t = s.disassembleIfElse(thenBlock, elseBlock);

                count += countOfPrimitiveCalls(thenBlock);
                count += countOfPrimitiveCalls(elseBlock);

                s.assembleIfElse(t, thenBlock, elseBlock);

                break;
            }
            case WHILE: {
                Statement body = s.newInstance();

                Condition t = s.disassembleWhile(body);

                count += countOfPrimitiveCalls(body);

                s.assembleWhile(t, body);

                break;
            }
            case CALL: {
                String instruction = s.disassembleCall();

                if (instruction.equals("move") || instruction.equals("turnleft")
                        || instruction.equals("turnright") || instruction.equals("infect")
                        || instruction.equals("skip")) {

                    count = 1;
                } else {
                    count = 0;
                }

                s.assembleCall(instruction);
                break;
            }
            default: {
                //all types are used
                break;
            }
        }
        return count;
    }

}
