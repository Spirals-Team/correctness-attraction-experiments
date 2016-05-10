package experiment.explorer;

import experiment.*;
import experiment.exploration.IntegerExplorationPlusOne;
import perturbation.PerturbationEngine;
import perturbation.location.PerturbationLocation;
import perturbation.log.LoggerImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;


/**
 * Created by bdanglot on 02/05/16.
 */
public class SeedExplorer extends CallExplorer {

    public static int[] seeds = new int[]{1, 2, 3, 4, 5};

    private static String nameOfSpecificExploration;

    private static String header;

    private int currentSeed;

    public SeedExplorer(boolean init, int currentSeed) {
        super(new IntegerExplorationPlusOne());
        header = "Contains data to explore the impact of the seed\n";
        header += "Seed Exploration (SE)\n";
        header += "Seed used : ";
        for (int seed : seeds)
            header += seed + " ";
        header += "\n" + Runner.locations.size() + " perturbation point\n";
        header += "N Execution Enactor\n";
        header += "PONE : Numerical Perturbator\n";
        header += "Size of each task : " + Runner.sizeOfEachTask + "\n";
        header += "Number of task : " + Runner.numberOfTask + "\n";
        nameOfSpecificExploration = "SeedExploration";
        PerturbationEngine.loggers.put(nameOfSpecificExploration, new LoggerImpl());
        this.currentSeed = currentSeed;
        if (init)
            Runner.locations.stream().forEach(this::init);
    }

    private void init(PerturbationLocation location) {
        try {
            FileWriter writer = new FileWriter("results/" + Runner.manager.getPath() + "/" + nameOfSpecificExploration + "_" + (location.getLocationIndex()) + ".txt", false);
            String format = "%-10s %-10s %-10s %-10s %-10s %-18s %-18s %-14s %-24s %-10s %-10s %-27s";
            writer.write(header);
            writer.write(String.format(format, "Seed", "IndexLoc",
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
        Tuple[][][][] results = Logger.getResults();
        try {
            FileWriter writer = new FileWriter("results/" + Runner.manager.getPath() + "/" + nameOfSpecificExploration + "_" + (location.getLocationIndex()) + ".txt", true);
            String format = "%-10s %-10s %-10s %-10s %-10s %-18s %-18s %-14s %-24s %-10s %-10s %-27s";
            Tuple result = new Tuple(6);
            int accNbOfTasks = 0;
            int accNbExecAllTask = 0;
            for (int indexTask = 0; indexTask < Runner.numberOfTask; indexTask++) {
                result = result.add(results[Runner.locations.indexOf(location)][indexTask][0][0]);
                accNbOfTasks += super.nbCallReferencePerLocationPerTask[Runner.locations.indexOf(location)][indexTask];
            }

            double avgCall = (double) result.get(3) / (double) accNbOfTasks;
            double avgPerturbation = (double) result.get(4) / (double) accNbOfTasks;

            writer.write(String.format(format, currentSeed, location.getLocationIndex(),
                    result.get(0), result.get(1), result.get(2),
                    result.get(3), String.format("%.2f", avgCall),
                    result.get(4), String.format("%.2f", avgPerturbation),
                    accNbExecAllTask, result.get(5),
                    result.get(4) == 0 ? "NaN" : Util.getStringPerc(result.get(0), result.total(3))) + "\n");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mergeFile() {
        try {
            FileWriter writer = new FileWriter("results/" + Runner.manager.getPath() + "/"+nameOfSpecificExploration+".txt", false);
            String format = "%-10s %-10s %-10s %-10s %-10s %-18s %-18s %-14s %-24s %-10s %-10s %-27s";
            writer.write(header);
            writer.write(String.format(format, "#Seed", "IndexLoc",
                    "#Success", "#Failure", "#Exception",
                    "#CallAllExecs", "AvgCallPerExec",
                    "#Perturbations", "AvgPerturbationPerExec",
                    "#Execs", "#CallRef", "%Success") + "\n");

            for (PerturbationLocation location : Runner.locations) {
                BufferedReader buffer = new BufferedReader(new FileReader("results/" + Runner.manager.getPath() + "/"+ nameOfSpecificExploration + "_" + (location.getLocationIndex()) + ".txt"));
                for (int i = 0 ; i < 9 ; i++) buffer.readLine();
                String line;
                while ((line = buffer.readLine()) != null)
                    writer.write(line + "\n");
                buffer.close();
                Files.delete(Paths.get("results/" + Runner.manager.getPath() + "/NumberTaskExplorer_" + (location.getLocationIndex()) + ".txt"));
            }
            writer.close();
        } catch (IOException ignored){}
    }


    public static void run(Class<?> classUnderPerturbation, Class<?> classCallable, Class<?> classManager, String locationType, Class<?>... inputTypes) {
        System.out.println("Explore the impact of seed ...");

        seeds = new int[5];
        Random rnd = new Random(((OracleManagerImpl) Runner.manager).seedForGenTask);
        for (int i = 0; i < 5; i++)
            seeds[i] = rnd.nextInt();

        for (int i = 0; i < seeds.length; i++) {
            int seed = seeds[i];
            System.out.println("currentSeed : \t" + seed + "\t" + Util.getStringPerc(i, seeds.length));
            OracleManagerImpl manager = null;
            try {
                manager = (OracleManagerImpl) classManager.getConstructor(int.class).newInstance(seed);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Runner.setup(classUnderPerturbation, classCallable, manager, locationType, inputTypes);
            Runner.run(new SeedExplorer(i == 0, seed));
        }
        mergeFile();
    }

}
