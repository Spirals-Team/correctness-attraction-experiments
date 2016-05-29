package partial_mutant;

import experiment.Manager;
import experiment.Util;
import md5.MD5CallableImpl;
import md5.MD5Instr;
import md5.MD5Manager;
import perturbation.enactor.AlwaysEnactorImpl;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.NothingPerturbatorImpl;
import sudoku.SudokuCallableImpl;
import sudoku.SudokuInstr;
import sudoku.SudokuManager;
import zip.LZWInstr;
import zip.ZipCallableImpl;
import zip.ZipManager;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Created by bdanglot on 19/05/16.
 */
public class MutantExplorer {

    private static final int[] numberOfTasks = new int[]{1, 2, 5, 10, 25, 50, 75, 100, 125, 500};

    private int numberOfTask;

    private Manager manager;

    private List<Integer> listOfSuccessTask;

    private Map<PerturbationLocation, List<Integer>> indexSuccessTaskPerPartialMutant;

    private Map<PerturbationLocation, List<Integer>> indexFailureTaskPerPartialMutant;

    private List<Integer> listOfFailureTask;

    private List<PerturbationLocation> sosies;

    private List[] sosiesByTask;

    private List<PerturbationLocation> partialMutant;

    private List[] partialMutantByTask;

    public MutantExplorer(Manager manager) {
        this.manager = manager;
        this.indexSuccessTaskPerPartialMutant = new HashMap<>();
        this.indexFailureTaskPerPartialMutant = new HashMap<>();
        this.listOfSuccessTask = new ArrayList<>();
        this.listOfFailureTask = new ArrayList<>();
        this.sosies = new ArrayList<>();
        this.partialMutant = new ArrayList<>();
        this.sosiesByTask = new List[numberOfTasks.length];
        this.partialMutantByTask = new List[numberOfTasks.length];
    }

    public Map<PerturbationLocation, List<Integer>> getIndexSuccessTaskPerPartialMutant() {
        return indexSuccessTaskPerPartialMutant;
    }

    public Map<PerturbationLocation, List<Integer>> getIndexFailureTaskPerPartialMutant() {
        return indexFailureTaskPerPartialMutant;
    }

    public void setNumberOfTask(int numberOfTask) {
        this.numberOfTask = numberOfTask;
    }

    public List<PerturbationLocation> getPartialMutant() {
        this.partialMutant = new ArrayList<>();
        List<PerturbationLocation> locations =  this.manager.getLocations();
        locations.forEach(this::run);
        return this.partialMutant;
    }

    public void run() {
        IntStream.range(0, numberOfTasks.length)
                 .forEach(this::runNumberOfTask);
        log();
    }

    private void log() {
        String path = "results/"+manager.getName()+"/mutant";
        try {
            /* number exploration results */
            FileWriter writer = new FileWriter(path + "_number.txt", false);
            String format = "%-10s %-10s %-10s";
            for (int numberTask : numberOfTasks)
                writer.write(numberTask + " ");
            writer.write("\n");
            writer.write(String.format(format, "#Task", "#Sosie", "#PMutant") + "\n");
            for (int i = 0; i < numberOfTasks.length; i++) {
                writer.write(String.format(format,
                        numberOfTasks[i],
                        this.sosiesByTask[i].size(),
                        this.partialMutantByTask[i].size()) + "\n");
            }
            writer.write("Index of sosies : ");
            for (PerturbationLocation sosie : this.sosies)
                writer.write(sosie.getLocationIndex()+ " ");
            writer.write("\n");
            writer.write("List of partial mutant : \n");
            writer.write(String.format("%-10s %-12s", "LocMutant", "#TaskFailed") + " ListOfIndexFailed \n");
            for (PerturbationLocation mutant : this.indexFailureTaskPerPartialMutant.keySet()) {
                writer.write(String.format("%-10s %-12s", mutant.getLocationIndex(), this.getIndexFailureTaskPerPartialMutant().get(mutant).size())+ " ");
                for (Integer task : this.getIndexFailureTaskPerPartialMutant().get(mutant))
                    writer.write(task + " ");
                writer.write("\n");
            }
            writer.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private void runNumberOfTask(int indexOfNumberOfTask) {
        this.sosies = new ArrayList<>();
        this.partialMutant = new ArrayList<>();
        this.numberOfTask = numberOfTasks[indexOfNumberOfTask];
        System.out.println(this.numberOfTask + " " + Util.getStringPerc(indexOfNumberOfTask, numberOfTasks.length));
        List<PerturbationLocation> locations =  this.manager.getLocations();
        locations.forEach(this::run);
        this.sosiesByTask[indexOfNumberOfTask] = this.sosies;
        this.partialMutantByTask[indexOfNumberOfTask] = this.partialMutant;
    }

    private void run(PerturbationLocation location) {
        location.setEnactor(new AlwaysEnactorImpl());
        location.setPerturbator(new AddNPerturbatorImpl(1));
        if (isPartialMutant(location)) {
            this.partialMutant.add(location);
            List<Integer> successes = new ArrayList<>();
            successes.addAll(this.listOfSuccessTask);
            List<Integer> failures = new ArrayList<>();
            failures.addAll(this.listOfFailureTask);
            this.indexSuccessTaskPerPartialMutant.put(location, successes);
            this.indexFailureTaskPerPartialMutant.put(location, failures);
        }
        this.listOfSuccessTask.clear();
        this.listOfFailureTask.clear();
    }

    private boolean isPartialMutant(PerturbationLocation location) {
        double perc = runTasks();
        if (perc == 1.0d)
            this.sosies.add(location);
        location.setEnactor(new NeverEnactorImpl());
        location.setPerturbator(new NothingPerturbatorImpl());
        return perc > 0.0d && perc < 1.0d;
    }

    private double runTasks() {
        return ((double) IntStream.range(0, this.numberOfTask)
                .reduce(0, (acc, indexTask) ->
                        acc + run(indexTask)
                )) / (double) this.numberOfTask;
    }

    public int run(int indexTask) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Callable instanceRunner = this.manager.getCallable(this.manager.getTask(indexTask));
            Future future = executor.submit(instanceRunner);
            try {
                Object output = future.get(60, TimeUnit.SECONDS);
                boolean assertion = this.manager.getOracle().assertPerturbation(this.manager.getTask(indexTask), output);
                executor.shutdownNow();
                if (assertion) {
                    this.listOfSuccessTask.add(indexTask);
                    return 1;
                }
                this.listOfFailureTask.add(indexTask);
                return 0;
            } catch (TimeoutException e) {
                future.cancel(true);
                System.err.println("Time out!");
                executor.shutdownNow();
                this.listOfFailureTask.add(indexTask);
                return 0;
            }
        } catch (Exception | Error e) {
            executor.shutdownNow();
            this.listOfFailureTask.add(indexTask);
            return 0;
        }
    }

    public static void main(String[] args) {
        //Class<?> CUP, OracleManager manager, Constructor callableConstructor, Class<?>... inputTypes
        MutantExplorer explorer = new MutantExplorer(new ZipManager(numberOfTasks[numberOfTasks.length-1], 100));
        explorer.run();
    }

}
