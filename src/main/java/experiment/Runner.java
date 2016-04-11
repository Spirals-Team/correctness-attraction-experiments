package experiment;

import md5.MD5;
import md5.MD5CallableImpl;
import md5.MD5OracleImpl;
import perturbation.location.PerturbationLocation;
import sort.QuickSort;
import sort.QuickSortCallableImpl;
import sort.SortOracleImpl;
import sudoku.Sudoku;
import sudoku.SudokuCallableImpl;
import sudoku.SudokuOracleImpl;
import zip.LZW;
import zip.ZipCallableImpl;
import zip.ZipOracleImpl;

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
//        readAntifragileFile();
        run(new AddNExplorerImpl());
        run(new RndExplorerImpl());
    }

    public static void readAntifragileFile() {
        List<PerturbationLocation> topTenLocations = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("results/"+oracle.getPath()+"/AddOneExplorer_AntiFragile"));
            br.readLine(); // thrash header line
            String [] indices = br.readLine().split(" ");
            for (int i = 0 ; i < Math.min(indices.length, 10) ; i++) {
                final int index = i;
                topTenLocations.add(locations.stream().filter(location ->
                        location.getLocationIndex() == Integer.parseInt(indices[index])
                ).collect(Collectors.toList()).get(0));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        locations = topTenLocations;
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        System.out.println("Run sort...");
        setup(QuickSort.class, QuickSortCallableImpl.class, "sort", new SortOracleImpl(), List.class);
        runAllCampaign();
        System.out.println("Run LZW...");
        setup(LZW.class, ZipCallableImpl.class, "run", new ZipOracleImpl(), String.class);
        runAllCampaign();
        System.out.println("Run md5...");
        Runner.setup(MD5.class, MD5CallableImpl.class, "runMd5", new MD5OracleImpl(), String.class);
        Runner.runAllCampaign();
        System.out.println("Run sudoku...");
        Runner.setup(Sudoku.class, SudokuCallableImpl.class, "runSudoku", new SudokuOracleImpl() , int[][].class);
        Runner.run(new AddOneExplorerImpl());
//        readAntifragileFile();
        Runner.run(new AddNExplorerImpl(1,2,5));
        Runner.run(new RndExplorerImpl());
        System.err.println(System.currentTimeMillis()-time +" ms");
    }



}
