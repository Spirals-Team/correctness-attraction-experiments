package experiment;

/**
 * T : generic input type of subject
 */
public interface OracleManager<T> {

    /**
     * getter of task of type T
     * @param index
     * @return
     */
    T get(int index);

    Oracle<T, ?> getOracle();

    /**
     * the path to identify where logs of the subject will be print
     * @return
     */
    String getPath();

    /**
     * Identification of the oracle used
     * @return
     */
    String getHeader();

}
