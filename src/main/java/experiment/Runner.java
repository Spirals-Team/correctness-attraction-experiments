package experiment;

import md5.MD5CallableImpl;
import perturbation.location.PerturbationLocation;
import sort.QuickSortCallableImpl;
import sudoku.SudokuCallableImpl;
import zip.ZipCallableImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * Created by spirals on 05/04/16.
 */
public class Runner {

    public static Oracle oracle;
    public static Class<?> CUP;// Class Under Perturbation
    public static Class<?> classCallable;// Class to run exp have to be static
    public static Constructor constructorRunner;
    public static List<PerturbationLocation> locations;
    public static Explorer explorer;

    public static boolean verbose = true;

    public static void run(Explorer explorerUnderPerturbation) {
        explorer = explorerUnderPerturbation;
        for (int indexOfTask = 0 ; indexOfTask < oracle.getNumberOfTask() ; indexOfTask++) {
            runLocations(indexOfTask);
        }
    }

    public static void runLocations(int indexOfTask) {
        for (PerturbationLocation location : locations) {
            if (verbose)
                System.out.println(location.getLocationIndex()+" \t "+getStringPerc(locations.indexOf(location) , locations.size()));
            explorer.run(indexOfTask, location);
        }
        explorer.log();
    }

    public static Tuple runPerturbation(int indexOfTask) {
        Tuple result = new Tuple(3);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Callable instanceRunner = (Callable)constructorRunner.newInstance(oracle.get(indexOfTask));
            Future future = executor.submit(instanceRunner);
            try {
                Object perturbedValue = (future.get(1, TimeUnit.SECONDS));
                boolean checked = oracle.check(perturbedValue, indexOfTask);
                if (checked)
                    result.set(0, 1); // success
                else
                    result.set(1, 1); // failures
                executor.shutdownNow();
                return result;

            } catch (TimeoutException e) {
                future.cancel(true);
                result.set(2, 1); // error computation time
                System.err.println("Time out!");
                executor.shutdownNow();
                return result;
            }

        } catch (Exception | Error e) {
            result.set(2, 1);
            executor.shutdownNow();
            return result;
        }
    }

    /**
     * Method for setting up the class under Perturbation (CUP)
     * @param classUnderPerturbation
     * @param classCallable Class which contains the entry method nameOfEntryMethod
     * @param nameOfEntryMethod
     * @param oracleImpl
     * @param argsOfMethods
     */
    public static void setup(Class<?> classUnderPerturbation, Class<?> classCallable, String nameOfEntryMethod, Oracle oracleImpl, Class<?>... argsOfMethods) {
        CUP = classUnderPerturbation;
        Runner.classCallable = classCallable;
        try {
            Runner.constructorRunner = classCallable.getConstructor(argsOfMethods);
        } catch (Exception e) {
            e.printStackTrace();
        }
        locations = PerturbationLocation.getLocationFromClass(classUnderPerturbation).stream().filter(location ->
                location.getType().equals("Numerical")).collect(Collectors.toList()
        );
        oracle = oracleImpl;
    }

    public static String getStringPerc(int nb, int total) {
        double perc = perc(nb,total);
        String ret = dash(perc);
        return ret + " " + String.format("%.2f", perc);
    }

    public static double perc(int nb, int total) {
        return (double)nb / (double) total * 100;
    }

    public static String dash(double perc) {
        String dash = "";
        for (int d = 0 ; d < perc / 5 ; d++) dash += "-";
        return dash;
    }

    public static void runAllCampaign() {
        run(new AddOneExplorerImpl());
        //getting the top-ten of the first campaign
        readAntifragileFile();
        run(new AddNExplorerImpl());
        run(new RndExplorerImpl());
    }

    public static void readAntifragileFile() {
        if (locations.size() <= 15)
            return;

        BufferedReader br = null;
        List<PerturbationLocation> topLocations = new ArrayList<>();
        String[] pathTofileToGetIndices = new String[]{
                "results/"+oracle.getPath()+"/AddOneExplorer_super_anti_fragile.txt",
                "results/"+oracle.getPath()+"/AddOneExplorer_anti_fragile.txt",
        };
        try {
            for (String path : pathTofileToGetIndices) {
                br = new BufferedReader(new FileReader(path));
                br.readLine(); // thrash header line
                String[] indices = br.readLine().split(" ");
                for (int i = 0; i < indices.length && topLocations.size() < 15 ; i++) {
                    final int index = i;
                    topLocations.add(locations.stream().filter(location ->
                            location.getLocationIndex() == Integer.parseInt(indices[index])
                    ).collect(Collectors.toList()).get(0));
                }
                if (topLocations.size() == 15) {
                    locations = topLocations;
                    return;
                }
            }

            if (topLocations.size() < 15) {
                List<Integer> blackList = new ArrayList<>();
                br = new BufferedReader(new FileReader( "results/"+oracle.getPath()+"/AddOneExplorer_oracle_fragile.txt"));
                br.readLine(); // thrash header line
                String[] indices = br.readLine().split(" ");
                for (String index : indices)
                    blackList.add(Integer.parseInt(index));

                br = new BufferedReader(new FileReader( "results/"+oracle.getPath()+"/AddOneExplorer_exception_fragile.txt"));
                br.readLine(); // thrash header line
                indices = br.readLine().split(" ");
                for (String index : indices)
                    blackList.add(Integer.parseInt(index));

                topLocations.add(locations.stream().filter(location ->
                        !blackList.contains(location.getLocationIndex())
                ).collect(Collectors.toList()).get(0));

                locations = topLocations;

                return;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        QuickSortCallableImpl.run();
        ZipCallableImpl.run();
        MD5CallableImpl.run();
        SudokuCallableImpl.run();
    }





}
