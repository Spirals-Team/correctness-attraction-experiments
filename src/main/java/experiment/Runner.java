package experiment;

import perturbation.location.PerturbationLocation;
import sort.QuickSort;
import sort.SortOracleImpl;
import zip.LZW;
import zip.Main;
import zip.ZipOracleImpl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by spirals on 05/04/16.
 */
public class Runner {

    public static Oracle oracle;
    public static Class<?> CUP;// Class Under Perturbation
    public static Method MUP;// Method Under Perturbation
    public static List<PerturbationLocation> locations;
    public static Explorer explorer;

    public static void run(Explorer explorerUnderPerturbation) {
        explorer = explorerUnderPerturbation;
        for (int indexOfTask = 0 ; indexOfTask < oracle.getNumberOfTask() ; indexOfTask++) {
            runLocations(indexOfTask);
        }
    }

    public static void runLocations(int indexOfTask) {
        for (PerturbationLocation location : locations) {
            System.out.println(String.format("%.2f", perc(locations.indexOf(location),locations.size())));
            explorer.run(indexOfTask, location);
        }
        explorer.log();
    }

    public static Tuple runPerturbation(int indexOfTask) {
        Tuple result = new Tuple(3);
        try {
            boolean checked = oracle.check(MUP.invoke(CUP, oracle.get(indexOfTask)), indexOfTask);
            if (checked)
                result.set(0, 1);
            else
                result.set(1, 1);
            return result;
        } catch (IllegalArgumentException arg) {
            result.set(2, 1);
            arg.printStackTrace();
            return result;
        } catch (Exception | Error e) {
            result.set(2, 1);
            return result;
        }
    }

    public static void setup(Class<?> classUnderPerturbation, String nameOfEntryMethod, Oracle oracleImpl, Class<?>... argsOfMethods) {
        CUP = classUnderPerturbation;
        try {
            MUP = CUP.getMethod(nameOfEntryMethod, argsOfMethods);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        locations = PerturbationLocation.getLocationFromClass(CUP).stream().filter(location ->
                location.getType().equals("Numerical")).collect(Collectors.toList()
        );
        oracle = oracleImpl;
    }

    /**
     * Method for setting up the class under Perturbation (CUP)
     * @param classUnderPerturbation
     * @param classRunner Class which contains the entry method nameOfEntryMethod in case of the CUP doesn't contain it
     * @param nameOfEntryMethod
     * @param oracleImpl
     * @param argsOfMethods
     */
    public static void setup(Class<?> classUnderPerturbation, Class<?> classRunner, String nameOfEntryMethod, Oracle oracleImpl, Class<?>... argsOfMethods) {
        CUP = classRunner;
        try {
            MUP = CUP.getMethod(nameOfEntryMethod, argsOfMethods);
        } catch (NoSuchMethodException e) {
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
        run(new AddNExplorerImpl());
        run(new RndExplorerImpl());
    }

    public static void main(String[] args) {
        System.out.println("Run sort...");
        setup(QuickSort.class, "sort", new SortOracleImpl(), List.class);
        runAllCampaign();
        System.out.println("Run LZW...");
        setup(LZW.class, Main.class, "run", new ZipOracleImpl(), String.class);
        runAllCampaign();
    }



}
