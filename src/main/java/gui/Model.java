package gui;

import experiment.Manager;
import perturbation.PerturbationEngine;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.enactor.RandomEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.log.LoggerImpl;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.InvPerturbatorImpl;
import perturbation.perturbator.NothingPerturbatorImpl;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Created by bdanglot on 13/05/16.
 */
public class Model {

    private float rnd = 0.0f;

    private Manager manager;

    private List<PerturbationLocation> locations;

    private int numberOfTask;

    private int size;

    private float step = 0.001f;

    private Class<?> classManager;

    private int accExec;

    private int accExecSuccess;

    private double avgPerturbationPerExec;

    private List<String> classOfLocation;

    private String currentTypeOfLocation;

    private List<Integer> antifragileLocation;// == 100%

    private List<Integer> robustLocation;//100% to 50%

    private List<Integer> weakLocation;//<50%

    public int getAccExec() {
        return accExec;
    }

    public int getAccExecSuccess() {
        return accExecSuccess;
    }

    public double getAvgPerturbationPerExec() {
        return avgPerturbationPerExec;
    }

    public int getNumberOfTask() {
        return numberOfTask;
    }

    public void setNumberOfTask(int numberOfTask) {
        this.numberOfTask = numberOfTask;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setType(String type) {
        this.currentTypeOfLocation = type;
    }

    public Model(Class<?> manager) {
        this.accExec = 0;
        this.accExecSuccess = 0;
        this.size = 100;
        this.numberOfTask = 40;
        this.avgPerturbationPerExec = 0.0d;
        this.classManager = manager;
        this.classOfLocation = new ArrayList<>();
        this.classOfLocation.add("Antifragile");
        this.currentTypeOfLocation = "Numerical";
        try {
            this.manager = (Manager) this.classManager.getConstructor(int.class, int.class, int.class).newInstance(this.numberOfTask, this.size, (int) System.currentTimeMillis());
            this.initLocations();
            this.setUpLocations();
            PerturbationEngine.loggers.put("gui", new LoggerImpl());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void initLocations() {
        this.locations = this.manager.getLocations();
        this.locations.stream().filter(location -> !location.getType().equals("Boolean"))
                .forEach(location -> location.setPerturbator(new InvPerturbatorImpl()));
        this.locations.stream().filter(location -> location.getType().equals("Numerical"))
                .forEach(location -> location.setPerturbator(new AddNPerturbatorImpl(1)));

        this.antifragileLocation = new ArrayList<>();
        this.robustLocation = new ArrayList<>();
        this.weakLocation = new ArrayList<>();

        this.readFile("results/" + this.manager.getName() + "/IntegerAddOne_RandomExplorer_analysis_graph_data.txt");
        this.readFile("results/" + this.manager.getName() + "/BooleanNegation_RandomExplorer_analysis_graph_data.txt");

        System.out.println(this.antifragileLocation);
        System.out.println(this.robustLocation);
        System.out.println(this.weakLocation);
    }

    private void readFile(String path) {
        try {

            BufferedReader br = new BufferedReader(new FileReader(path));
            br.readLine();
            br.readLine();
            br.readLine();//Trash unused lines
            int nbRandomRate = br.readLine().split(" ").length - 3;
            br.readLine();
            br.readLine();
            br.readLine();//Trash unused lines
            int nbLocation = Integer.parseInt(br.readLine().split(" ")[0]);
            br.readLine();//Trash unused lines

            for (int loc = 0; loc < nbLocation; loc++) {
                boolean isAntifragile = true;
                boolean added = false;
                int indexLoc = -1;
                for (int random = 0; random < nbRandomRate ; random++) {
                    String[] line = br.readLine().split(" ");
                    float success = Float.parseFloat(line[line.length - 1].replace(",", "."));
                    indexLoc = Integer.parseInt(line[2]);
                    if (success < 50.0) {
                        this.weakLocation.add(indexLoc);
                        added = true;
                        break;
                    } else if (success < 100.0) {
                        isAntifragile = false;
                    }
                }
                if (!added) {
                    if (isAntifragile)
                        this.antifragileLocation.add(indexLoc);
                    else
                        this.robustLocation.add(indexLoc);
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUpLocations() {
        this.locations.forEach(location -> location.setEnactor(new NeverEnactorImpl()));//Clean up

        List<Integer> indices = new ArrayList<>();
        if (this.classOfLocation.contains("Antifragile"))
            indices.addAll(this.antifragileLocation);
        if (this.classOfLocation.contains("Robust"))
            indices.addAll(this.robustLocation);
        if (this.classOfLocation.contains("Weak"))
            indices.addAll(this.robustLocation);

        this.locations.stream().filter(location -> location.getType().equals(this.currentTypeOfLocation) && indices.contains(location.getLocationIndex()))
                .forEach(location -> location.setEnactor(new RandomEnactorImpl(rnd)));
    }

    public void incRnd() {
        this.rnd = this.rnd + this.step < 1.0f ? this.rnd + this.step : 1.0f;
        this.setUpLocations();
    }

    public void decRnd() {
        this.rnd = this.rnd - this.step > 0.0f ? this.rnd - this.step : 0.0f;
        this.setUpLocations();
    }

    public float getRnd() {
        return this.rnd;
    }

    public float runAllTask() {
        try {
            PerturbationEngine.loggers.get("gui").reset();
            this.locations.forEach(location -> PerturbationEngine.loggers.get("gui").logOn(location));
            this.manager = (Manager) this.classManager.getConstructor(int.class, int.class, int.class).newInstance(this.numberOfTask, this.size, (int) System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        int nbSuccess = IntStream.range(0, numberOfTask).reduce(0, (acc, indexTask) -> acc + runPerturbation(indexTask));
//        int nbSuccess = IntStream.range(0, numberOfTask).reduce(0, (acc, indexTask) -> acc + runLocations(indexTask));

        this.avgPerturbationPerExec = (double) IntStream.range(0, locations.size())
                .reduce(0, (acc, indexLocation) ->
                        acc + PerturbationEngine.loggers.get("gui").getEnactions(locations.get(indexLocation))
                ) / (double) numberOfTask;

        return (float) nbSuccess / (float) (numberOfTask) * 100;
//        return (float) nbSuccess / (float) (locations.size()*numberOfTask) * 100;
    }

    private int runLocations(int indexTask) {
        return IntStream.range(0, locations.size()).reduce(0, (acc, indexLocation) ->
                acc + runLocation(indexTask, this.locations.get(indexLocation))
        );
    }

    private int runLocation(int indexTask, PerturbationLocation location) {
        location.setEnactor(new RandomEnactorImpl(rnd));
        int assertion = runPerturbation(indexTask);
        location.setEnactor(new NeverEnactorImpl());
        return assertion;
    }

    private int runPerturbation(int indexTask) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        accExec++;
        try {
            Callable instanceRunner = this.manager.getCallable(manager.getTask(indexTask));
            Future future = executor.submit(instanceRunner);
            try {
                Object output = future.get(15, TimeUnit.SECONDS);
                if (this.manager.getOracle().assertPerturbation(manager.getTask(indexTask), output)) {
                    executor.shutdownNow();
                    accExecSuccess++;
                    return 1;
                } else {
                    executor.shutdownNow();
                    return 0;
                }
            } catch (TimeoutException e) {
                future.cancel(true);
                System.err.println("Time out!");
                executor.shutdownNow();
                return 0;
            }
        } catch (Exception | Error e) {
            executor.shutdownNow();
            return 0;
        }
    }

}
