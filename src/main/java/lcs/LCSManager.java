package lcs;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by bdanglot on 10/06/16.
 */
public class LCSManager extends ManagerImpl<String[], String> {

    private BufferedReader sativa;
    private BufferedReader thaliana;

    private String currentSativa;

    public LCSManager(int numberOfTask, int size) {
        this(numberOfTask, size, 23);
    }

    public LCSManager(int numberOfTask, int size, int seed) {
        super(seed);
        try {
            sativa = new BufferedReader(new FileReader("resources/lcs/sativa.fa"));
            sativa.readLine();//Trash Header
            currentSativa = sativa.readLine();
            thaliana = new BufferedReader(new FileReader("resources/lcs/thaliana.fa"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.CUP = LCSInstr.class;
        super.initialize(numberOfTask, size);
    }

    @Override
    protected String[] generateOneTask() {
        try {
            if (thaliana.readLine() == null) {
                thaliana.close();
                thaliana = new BufferedReader(new FileReader("resources/lcs/thaliana.fa"));
                if (sativa.readLine() == null) {
                    System.err.println("Too many task has been generated (over 12k)");
                    System.exit(-1);
                }
                currentSativa = sativa.readLine();
            }
            thaliana.readLine();
            return new String[] {currentSativa, thaliana.readLine()};
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    @Override
    public CallableImpl<String[], String> getCallable(String[] input) {
        return new CallableImpl<String[], String>(input) {
            @Override
            public String call() throws Exception {
                return LCSInstr.lcs(input[0], input[1]);
            }
        };
    }

    @Override
    public Oracle<String[], String> getOracle() {
        return (input, output) -> LCS.lcs(input[0], input[1]).equals(output);
    }

    @Override
    public String getName() {
        return "lcs";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " string of " + super.sizeOfTask + " char\n" +
                "RNA sequence of thaliana and sativa from miRBase (mature.fa)\n"+
                super.locations.size() + " perturbation points\n";
    }

    @Override
    public String[] getTask(int indexOfTask) {
        if (indexOfTask >= super.tasks.size())
            super.getTask(indexOfTask);
        return new String [] {super.tasks.get(indexOfTask)[0], super.tasks.get(indexOfTask)[1]};
    }

    @Override
    public void stop() {
        super.stop();
        try {
            this.thaliana.close();
            this.sativa.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
