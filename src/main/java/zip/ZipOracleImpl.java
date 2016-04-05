package zip;

import experiment.Oracle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by spirals on 05/04/16.
 */
public class ZipOracleImpl implements Oracle<String> {

    private String PATH = "zip";

    private List<String> scenarios = new ArrayList<>();

    private int numberOfTask = 100;

    private int seedForGenTask = 23;

    private Random randomForGenTask = new Random(seedForGenTask);

    private int sizeOfEachTask = 150;

    public ZipOracleImpl(){
        while (scenarios.size() < numberOfTask)
            scenarios.add(generateOneString());
    }

    private String generateOneString() {
        String string = "";
        for (int i = 0 ; i < sizeOfEachTask ; i++) {
            string += ((char)randomForGenTask.nextInt(256));
        }
        return string;
    }


    @Override
    public String header() {
        String header = numberOfTask + " string of " + sizeOfEachTask + " char\n";
        header += "Random integer generated with " + seedForGenTask + " as seed\n";
        return header;
    }

    @Override
    public boolean check(String perturbedValue, int index) {
        return perturbedValue.equals(scenarios.get(index));
    }

    @Override
    public String get(int index) {
        return new String(scenarios.get(index));
    }

    @Override
    public int getNumberOfTask() {
        return numberOfTask;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
