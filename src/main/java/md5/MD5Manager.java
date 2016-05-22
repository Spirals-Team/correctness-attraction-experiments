package md5;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;

/**
 * Created by spirals on 19/04/16.
 */
public class MD5Manager extends ManagerImpl<String, byte[]> {

    public MD5Manager(int numberOfTask, int size) {
        this(numberOfTask, size, 23);

    }

    public MD5Manager(int numberOfTask, int size, int seed) {
        super(seed);
        super.CUP = MD5Instr.class;
        super.initialize(numberOfTask, size);
    }

    @Override
    protected String generateOneTask() {
        String string = "";
        for (int i = 0 ; i < super.sizeOfTask ; i++) {
            string += ((char)randomForGenTask.nextInt(256));
        }
        return string;
    }

    @Override
    public CallableImpl<String, byte[]> getCallable(String input) {
        return new MD5CallableImpl(input);
    }

    @Override
    public String getName() {
        return "md5";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " string of " + super.sizeOfTask + " char\nRandom char generated with " + seedForGenTask + " as seed\n" +
                super.locations.size() + " perturbation points\n";
    }

    @Override
    public String getTask(int index) {
        return new String(super.tasks.get(index));
    }

    @Override
    public Oracle<String, byte[]> getOracle() {
        return new MD5Oracle();
    }
}
