package lcs;

import experiment.CallableImpl;
import experiment.Manager;
import experiment.ManagerImpl;
import experiment.Oracle;
import javafx.util.Pair;

/**
 * Created by bdanglot on 10/06/16.
 */
public class LCSManager extends ManagerImpl<Pair<String,String>, String> {

    private static final char[] letters = new char[]{'A', 'C', 'G', 'T'};

    public LCSManager(int numberOfTask, int size) {
        this(numberOfTask, size, 23);
    }

    public LCSManager(int numberOfTask, int size, int seed) {
        super(seed);
        super.CUP = LCSInstr.class;
        super.initialize(numberOfTask, size);
    }

    @Override
    protected Pair<String,String> generateOneTask() {
        String p1 = "", p2 = "";
        for (int i = 0; i < super.sizeOfTask; i++) {
            p1 += letters[randomForGenTask.nextInt(4)];
            p2 += letters[randomForGenTask.nextInt(4)];
        }
        return new Pair<>(p1, p2);
    }

    @Override
    public CallableImpl<Pair<String,String>, String> getCallable(Pair<String,String> input) {
        return new CallableImpl<Pair<String, String>, String>(input) {
            @Override
            public String call() throws Exception {
                return LCSInstr.lcs(input.getKey(), input.getValue());
            }
        };
    }

    @Override
    public Oracle<Pair<String,String>, String> getOracle() {
        return (input, output) -> LCS.lcs(input.getKey(), input.getValue()).equals(output);
    }

    @Override
    public String getName() {
        return "lcs";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size()+ " string of " + super.sizeOfTask + " char\n" +
                "Random char generated with " + seedForGenTask + " as seed\n" +
                super.locations.size() + " perturbation points\n";
    }

    @Override
    public Pair<String, String> getTask(int indexOfTask) {
        if (indexOfTask >= super.tasks.size())
            super.getTask(indexOfTask);
        return new Pair<>(super.tasks.get(indexOfTask).getKey(), super.tasks.get(indexOfTask).getValue());
    }

}
