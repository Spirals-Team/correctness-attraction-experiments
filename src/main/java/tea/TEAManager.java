package tea;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;

/**
 * Created by bdanglot on 24/05/16.
 */
public class TEAManager extends ManagerImpl<int[], int[]> {

    private final int [] key = new int[]{0xDEADBEE1, 0xDEADBEE2, 0xDEADBEE3, 0xDEADBEE4};

    public TEAManager(int numberOfTask, int size) {
        this(numberOfTask, size, 23);
    }

    public TEAManager(int numberOfTask, int size, int seed) {
        super(seed);
        super.CUP = TEAInstr.class;
        super.initialize(numberOfTask, size);
    }

    @Override
    protected int[] generateOneTask() {
        return new int[] {randomForGenTask.nextInt(0x7FFFFFFF), randomForGenTask.nextInt(0x7FFFFFFF)};
    }

    @Override
    public CallableImpl<int[], int[]> getCallable(int[] input) {
        return new CallableImpl<int[], int[]>(input) {
            @Override
            public int[] call() throws Exception {
                return TEAInstr.decrypt(TEAInstr.encrypt(input, key), key);
            }
        };
    }

    @Override
    public Oracle<int[], int[]> getOracle() {
        return (in, out) -> in[0] == out[0] && in[1] == out[1];
    }

    @Override
    public String getName() {
        return "tea";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " string of " + super.sizeOfTask + " char\nRandom char generated with " + seedForGenTask + " as seed\n" +
                super.locations.size() + " perturbation points\n";
    }

    @Override
    public int[] getTask(int indexOfTask) {
        return new int[]{super.tasks.get(indexOfTask)[0],super.tasks.get(indexOfTask)[1]};
    }
}
