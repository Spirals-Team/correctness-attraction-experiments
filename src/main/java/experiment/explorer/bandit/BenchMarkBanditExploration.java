package experiment.explorer.bandit;

import experiment.Main;
import experiment.exploration.Exploration;
import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.bandit.budget.Budget;
import experiment.explorer.bandit.budget.TimeBudget;
import experiment.explorer.bandit.policy.Policy;
import experiment.explorer.bandit.policy.UCBPolicy;
import quicksort.QuickSortManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by bdanglot on 16/06/16.
 */
public class BenchMarkBanditExploration {

    /**
     * We want to compare the results of the bandit exploration on quicksort Example.
     * Time? Performance vs Systematic Exploration? Policies?
     */

    private static final String FILE_DATA = "IntegerAddOne_CallExplorer_analysis_graph_data.txt";

    private static final String FILE_SPACE = "IntegerAddOne_CallExplorer_search_space_size_1.txt";

    private static double[] successSysExploration;

    private static double totalSuccess;

    private static final long TIME_SYS = 102582; //117963

    private static void buildResultsOfSystematicExploration(String name) {

        try {
            BufferedReader buffer = new BufferedReader(new FileReader("results/" + name + "/" +  FILE_DATA));
            //Trash header
            for (int i = 0; i < 5; i++)
                buffer.readLine();

            int nbLocations = Integer.parseInt(buffer.readLine().split(" ")[0]);
            successSysExploration = new double[nbLocations];

            buffer.readLine();//Trash the header of columns

            for (int i = 0; i < successSysExploration.length; i++) {
                String[] linesAsArray = buffer.readLine().split(" ");
                successSysExploration[i] = Double.parseDouble(linesAsArray[linesAsArray.length - 1].replace(",", "."));
            }

            buffer.close();

            buffer = new BufferedReader(new FileReader("results/" + name + "/" + FILE_SPACE));

            for (int i = 0; i < 5; i++)
                buffer.readLine();

            String[] linesAsArray = buffer.readLine().split(" ");
            totalSuccess = Double.parseDouble(linesAsArray[linesAsArray.length - 1].replace(",", "."));

            buffer.close();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private static void compareToSystematicExploration(long time, String log, String name) {
        String[] logs = log.split("\n");
        double deviation = 0.0D;
        for (int i = 0; i < successSysExploration.length; i++) {
            double success = Double.parseDouble(logs[i + 1].trim().replaceAll(" +", " ").split(" ")[1].replace(",", "."));
            deviation += Math.abs(successSysExploration[i] - (success * 100.0D));
        }
        deviation /= (double) successSysExploration.length;
        String [] lastLine = logs[logs.length - 1].trim().replaceAll(" +", " ").split(" ");
        double totalSuccessBandit = Double.parseDouble(lastLine[lastLine.length - 1].replace(",", "."));
        double totalDeviation = (Math.abs(totalSuccess - totalSuccessBandit) / totalSuccess) * 100.0D;
        double devTime = ((double)(TIME_SYS - time) / (double)TIME_SYS) * 100.0D;

        try {
            FileWriter writer = new FileWriter("results/"+name+"/compareToSystematicToBandit.txt", true);
            writer.write("avg deviation per point: " + String.format("%.2f", deviation) + "\n");
            writer.write("deviation % success: " + String.format("%.2f", totalDeviation) + "\n");
            writer.write("diff (in %) if time : " + String.format("%.2f", devTime) + "\n");
            writer.write("====================================================================\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(String.format("%.2f", deviation));
        System.out.println(String.format("%.2f", totalDeviation));
        System.out.println(String.format("%.2f", devTime));
    }

    public static void main(String[] args) {
        Main.verbose = false;

        QuickSortManager manager = new QuickSortManager(20, 100);
        buildResultsOfSystematicExploration(manager.getName());
//        int[] nbLap = new int[]{1, 10, 50, 100, 500, 1000, 2500, 5000};
        float [] percTime = new float[]{0.05f, 0.25f, 0.5f, 1.0f, 1.5f, 5.0f};

        Exploration exploration = new IntegerExplorationPlusOne();
        manager.getLocations(exploration.getType());

        for (int i = 0 ; i < percTime.length ; i++) {
            Budget budget = new TimeBudget((int)(TIME_SYS * percTime[i]));
            Policy policy = new UCBPolicy(manager.getLocations().size());
            BanditExplorer bandit = new BanditExplorer(exploration, manager, policy, budget);
            long time = System.currentTimeMillis();
            bandit.run();
            compareToSystematicExploration((System.currentTimeMillis() - time), policy.log(), manager.getName());
            System.out.println("================================================");
        }
    }

}
