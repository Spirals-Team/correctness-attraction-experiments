package experiment;

/**
 * Created by spirals on 05/04/16.
 */

/**
 * Interface Oracle to check the result of perturbed execution of subjects.
 * @param <T> input type of the subject
 * @param <P> output type of the subject
 */
public interface Oracle<T,P> {

    /**
     * @return a log header to have the detail of the experimenation
     */
    String header();

    /**
     * Method to check the result of perturbed execution
     * @param perturbedValue the output of the perturbed execution
     * @param index index of the task that was perturbed
     * @return true if perturbedValue is the result expected, false otherwise
     */
    boolean check(P perturbedValue, int index);

    /**
     *
     * @param index of the task to be executed
     * @return a clone of the task to be executed
     */
    T get(int index);

    /**
     * @return the path to the results directories
     */
    String getPath();

}
