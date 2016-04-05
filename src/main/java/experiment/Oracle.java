package experiment;

/**
 * Created by spirals on 05/04/16.
 */
public interface Oracle<T> {

    String header();

    boolean check(T perturbedValue, int index);

    T get(int index);

    int getNumberOfTask();

    String getPath();

}
