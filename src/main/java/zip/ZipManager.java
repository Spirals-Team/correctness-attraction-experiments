package zip;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;

/**
 * Created by spirals on 19/04/16.
 */
public class ZipManager extends ManagerImpl<String,String> {

    public ZipManager(int numberOfTask, int size) {
        this(numberOfTask, size, 23);
    }

    public ZipManager(int numberOfTask, int size, int seed) {
        super(23);
        super.CUP = LZWInstr.class;
        super.initialize(numberOfTask, size);
    }


    @Override
    protected String generateOneTask() {
        String string = "";
        for (int i = 0; i < super.sizeOfTask ; i++) {
            string += ((char)randomForGenTask.nextInt(256));
        }
        return string;
    }

    @Override
    public CallableImpl<String, String> getCallable(String input) {
        return new ZipCallableImpl(input);
    }

    @Override
    public Oracle<String, String> getOracle() {
        return new ZipOracle();
    }

    @Override
    public String getName() {
        return "zip";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size()+ " string of " + super.sizeOfTask + " char\n" +
                "Random char generated with " + seedForGenTask + " as seed\n" +
                super.locations.size() + " perturbation points\n";
    }

    @Override
    public String getTask(int indexOfTask) {
        return new String(super.tasks.get(indexOfTask));
    }
}
