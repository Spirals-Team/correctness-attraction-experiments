package experiment;

import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by spirals on 12/04/16.
 */
public class Util {

    private static ClassLoader loader = ClassLoader.getSystemClassLoader();

    private static List<Class> classes = new ArrayList<>();

    public static List<PerturbationLocation> getAllLocations(String project, String packagaPath, String type) {
        final List<PerturbationLocation> locations = new ArrayList<>();
        iterateFolders(project, packagaPath);
        System.out.println("Number of classes " +  classes.size());
        classes.stream().forEach(clazz -> {
                    PerturbationLocationImpl.getLocationFromClass(clazz).forEach(location -> {
                        if (!locations.contains(location))
                            locations.add(location);
                    });
                }
        );
        return locations.stream().filter(location -> location.getType().equals(type)).collect(Collectors.toList());
    }

    private static void iterateFolders(String path, String currentPackage) {
        File root = new File(path);
        assert root.listFiles() != null;
        for (File subFile : root.listFiles()) {
            if (subFile.isDirectory())
                iterateFolders(path + subFile.getName() + "/", currentPackage + "." + subFile.getName());
            else if (isJava(subFile.getName())) {
                try {
                    Class<?> clazz = loader.loadClass(currentPackage + "." + removeExt(subFile.getName()));
                    System.out.println(currentPackage + "." + removeExt(subFile.getName()));
                    classes.add(clazz);
                } catch (ClassNotFoundException e) {
                    continue;
                }
            }
        }
    }

    private static String removeExt(String name) {
        return name.substring(0, name.length() - ".java".length());
    }

    private static boolean isJava(String name) {
        return name.endsWith(".java");
    }


    public static String getStringPerc(long nb, long total) {
        double perc = perc(nb, total);
        String ret = dash(perc);
        return ret + " " + String.format("%.2f", perc);
    }

    public static double perc(long nb, long total) {
        return (double) nb / (double) total * 100;
    }

    public static String dash(double perc) {
        String dash = "";
        for (int d = 0; d < perc / 5; d++) dash += "-";
        return dash;
    }

}
