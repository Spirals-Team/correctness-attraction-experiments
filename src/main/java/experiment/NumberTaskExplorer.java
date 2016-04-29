package experiment;

import md5.MD5CallableImpl;
import md5.MD5Instr;
import md5.MD5Manager;
import mersenne.MersenneCallableImpl;
import mersenne.MersenneManager;
import mersenne.MersenneTwisterInstr;
import perturbation.location.PerturbationLocation;
import quicksort.QuickSortCallableImpl;
import quicksort.QuickSortInstr;
import quicksort.QuickSortManager;
import zip.LZWInstr;
import zip.ZipCallableImpl;
import zip.ZipManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by bdanglot on 29/04/16.
 */
public class NumberTaskExplorer extends AddOneExplorerImpl {

    public NumberTaskExplorer(boolean init, int[] arrayOfTask) {
        header = "Contains data to explore the number of task required for saturation\n";
        header += "Task Number Exploration (TNE)\n";
        header += "Number Task :";
        for (int nTask : arrayOfTask)
            header += nTask + " ";
        header += "\n" + Runner.locations.size() + " perturbation point\n";
        header += "N Execution Enactor\n";
        header += "PONE : Numerical Perturbator\n";
        header += "Each task is an arrays of " + Runner.sizeOfEachTask + "\n";
        header += "Seed used for generate task is " + Runner.manager.seedForGenTask + "\n";

        name = "NumberTaskExplorer";

        if (init)
            Runner.locations.stream().forEach(this::init);
    }

    private void init(PerturbationLocation location) {
        try {
            FileWriter writer = new FileWriter("results/" + Runner.manager.getPath() + "/" + name + "_" + (location.getLocationIndex()) + ".txt", false);
            String format = "%-10s %-10s %-10s %-10s %-10s %-18s %-18s %-14s %-24s %-10s %-10s %-27s";
            writer.write(header);
            writer.write(String.format(format, "#Tasks", "IndexLoc",
                    "#Success", "#Failure", "#Exception",
                    "#CallAllExecs", "AvgCallPerExec",
                    "#Perturbations", "AvgPerturbationPerExec",
                    "#Execs", "#CallRef", "%Success") + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void log() {
        Runner.locations.stream().forEach(this::log);
    }

    private void log(PerturbationLocation location) {
        Tuple[][][] results = Logger.getResults();
        try {
            FileWriter writer = new FileWriter("results/" + Runner.manager.getPath() + "/" + name + "_" + (location.getLocationIndex()) + ".txt", true);
            String format = "%-10s %-10s %-10s %-10s %-10s %-18s %-18s %-14s %-24s %-10s %-10s %-27s";
            Tuple result = new Tuple(5);
            int accNbOfTasks = 0;
            int accNbExecAllTask = 0;
            for (int indexTask = 0; indexTask < Runner.numberOfTask; indexTask++) {
                result = result.add(results[Runner.locations.indexOf(location)][indexTask][0]);
                accNbOfTasks += nbCallReferencePerLocationPerTask[Runner.locations.indexOf(location)][indexTask];
                accNbExecAllTask += nbExecsPerLocationPerTaskPerMagnitude[Runner.locations.indexOf(location)][indexTask][0];
            }

            double avgCall = (double) result.get(3) / (double) accNbOfTasks;
            double avgPerturbation = (double) result.get(4) / (double) accNbOfTasks;

            writer.write(String.format(format, Runner.numberOfTask, location.getLocationIndex(),
                    result.get(0), result.get(1), result.get(2),
                    result.get(3), String.format("%.2f", avgCall),
                    result.get(4), String.format("%.2f", avgPerturbation),
                    accNbExecAllTask, accNbOfTasks,
                    result.get(4) == 0 ? "NaN" : Util.getStringPerc(result.get(0), result.total(3))) + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mergeFile(int[] arrayOfTask) {
        String header = "Contains data to explore the number of task required for saturation\n";
        header += "Task Number Exploration (TNE)\n";
        header += "Number Task : ";
        for (int nTask : arrayOfTask)
            header += nTask + " ";
        header += "\n" + Runner.locations.size() + " perturbation point\n";
        header += "N Execution Enactor\n";
        header += "PONE : Numerical Perturbator\n";
        header += "Each task is an arrays of " + Runner.sizeOfEachTask + "\n";
        header += "Seed used for generate task is " + Runner.manager.seedForGenTask + "\n";

        try {
            FileWriter writer = new FileWriter("results/" + Runner.manager.getPath() + "/NumberTaskExplorer.txt", false);
            String format = "%-10s %-10s %-10s %-10s %-10s %-18s %-18s %-14s %-24s %-10s %-10s %-27s";
            writer.write(header);
            writer.write(String.format(format, "#Tasks", "IndexLoc",
                    "#Success", "#Failure", "#Exception",
                    "#CallAllExecs", "AvgCallPerExec",
                    "#Perturbations", "AvgPerturbationPerExec",
                    "#Execs", "#CallRef", "%Success") + "\n");

            for (PerturbationLocation location : Runner.locations) {
                BufferedReader buffer = new BufferedReader(new FileReader("results/" + Runner.manager.getPath() + "/NumberTaskExplorer_" + (location.getLocationIndex()) + ".txt"));
                for (int i = 0 ; i < 9 ; i++) buffer.readLine();
                String line;
                while ((line = buffer.readLine()) != null)
                    writer.write(line + "\n");
                buffer.close();
            }
            writer.close();
        } catch (IOException e){}

    }

    public static void run(Class<?> classUnderPerturbation, Class<?> classCallable, Class<?> classManager, String locationType, Class<?>... inputTypes) {
        System.out.println("Explore the saturation by the number of tasks...");
        int[] numberOfTask = new int[]{1,2,5,10,25,50,100,200};
        for (int i = 0 ; i < numberOfTask.length ; i++) {
            int nbTask = numberOfTask[i];
            System.out.println("Number of task : \t" + nbTask + "\t" +Util.getStringPerc(i, numberOfTask.length));
            Runner.numberOfTask = nbTask;
            OracleManager manager = null;
            try {
                manager = (OracleManager) classManager.getConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Runner.setup(classUnderPerturbation, classCallable, manager, locationType, inputTypes);
            Runner.run(new NumberTaskExplorer(nbTask == 1, numberOfTask));
        }
        mergeFile(numberOfTask);
    }

    public static void main(String[] args) {
        System.out.println("Quicksort");
        NumberTaskExplorer.run(QuickSortInstr.class, QuickSortCallableImpl.class, QuickSortManager.class, "Numerical", List.class);
        System.out.println("MD5");
        NumberTaskExplorer.run(MD5Instr.class, MD5CallableImpl.class, MD5Manager.class, "Numerical", String.class);
        System.out.println("MT");
        NumberTaskExplorer.run(MersenneTwisterInstr.class, MersenneCallableImpl.class, MersenneManager.class,"Numerical", Long.class);
        System.out.println("LZW");
        NumberTaskExplorer.run(LZWInstr.class, ZipCallableImpl.class, ZipManager.class, "Numerical", String.class);
    }

}
