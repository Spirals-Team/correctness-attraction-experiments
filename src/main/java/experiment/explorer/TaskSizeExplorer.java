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

/**
 * Created by bdanglot on 02/05/16.
 */
public class TaskSizeExplorer extends CallExplorer {

    public static int[] sizeOfTask = new int[]{5,10,20,50,100,250};

    private String header;

    private String nameOfSpecificExploration;

    public TaskSizeExplorer(boolean init, int[] arrayOfTask) {
        super(new IntegerExplorationPlusOne());
        //Logger contains : Success Failure Exception Call Perturbation NumberOfExecution
        Logger.init(Runner.locations.size(), Runner.numberOfTask, super.perturbators.size());
        PerturbationEngine.loggers.put(super.name, new LoggerImpl());

        header = "Contains data to explore the size of task required for saturation\n";
        header += "Task Size Exploration (TSE)\n";
        header += "Task Size : ";
        for (int nTask : arrayOfTask)
            header += nTask + " ";
        header += "\n" + Runner.locations.size() + " perturbation point\n";
        header += "N Execution Enactor\n";
        header += "PONE : Numerical Perturbator\n";
        header += "One task of different size\n";
        header += "Seed used for generate task is " + ((OracleManagerImpl)Runner.manager).seedForGenTask + "\n";

        PerturbationEngine.loggers.remove(super.name);
        nameOfSpecificExploration = "SizeTaskExploration";
        PerturbationEngine.loggers.put(super.name, new LoggerImpl());

        if (init)
            Runner.locations.stream().forEach(this::init);
    }

    private void init(PerturbationLocation location) {
        try {
            FileWriter writer = new FileWriter("results/" + Runner.manager.getPath() + "/" + nameOfSpecificExploration + "_" + (location.getLocationIndex()) + ".txt", false);
            String format = "%-10s %-10s %-10s %-10s %-10s %-18s %-18s %-14s %-24s %-10s %-10s %-27s";
            writer.write(header);
            writer.write(String.format(format, "Size", "IndexLoc",
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

            writer.write(String.format(format, Runner.sizeOfEachTask, location.getLocationIndex(),
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

    private static void mergeFile(int[] sizeOfTask) {
        String  header = "Contains data to explore the size of task required for saturation\n";
        header += "Size Task Exploration (STE)\n";
        header += "Size Task : ";
        for (int size : sizeOfTask)
            header += size + " ";
        header += "\n" + Runner.locations.size() + " perturbation point\n";
        header += "N Execution Enactor\n";
        header += "PONE : Numerical Perturbator\n";
        header += "One task of different size\n";
        header += "Seed used for generate task is " + ((OracleManagerImpl)Runner.manager).seedForGenTask + "\n";

        String name = "SizeTaskExploration";

        try {
            FileWriter writer = new FileWriter("results/" + Runner.manager.getPath() + "/"+name+".txt", false);
            String format = "%-10s %-10s %-10s %-10s %-10s %-18s %-18s %-14s %-24s %-10s %-10s %-27s";
            writer.write(header);
            writer.write(String.format(format, "#Tasks", "IndexLoc",
                    "#Success", "#Failure", "#Exception",
                    "#CallAllExecs", "AvgCallPerExec",
                    "#Perturbations", "AvgPerturbationPerExec",
                    "#Execs", "#CallRef", "%Success") + "\n");

            for (PerturbationLocation location : Runner.locations) {
                BufferedReader buffer = new BufferedReader(new FileReader("results/" + Runner.manager.getPath() + "/"+name+"_" + (location.getLocationIndex()) + ".txt"));
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
        System.out.println("Explore the saturation by the size of tasks...");
        for (int i = 0 ; i < sizeOfTask.length ; i++) {
            int size = sizeOfTask[i];
            System.out.println("Size of task : \t" + size + "\t" +Util.getStringPerc(i, sizeOfTask.length));
            Runner.sizeOfEachTask = size;
            OracleManagerImpl manager = null;
            try {
                manager = (OracleManagerImpl) classManager.getConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Runner.setup(classUnderPerturbation, classCallable, manager, locationType, inputTypes);
            Runner.run(new TaskSizeExplorer(i == 0, sizeOfTask));
        }
        mergeFile(sizeOfTask);
    }
}
