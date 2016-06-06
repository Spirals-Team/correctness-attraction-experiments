package experiment.explorer.bandit;

/**
 * Created by bdanglot on 06/06/16.
 */
public interface Budget {

    /**
     * Method used to know if we can still run bandit
     * @return true if we can, false otherwise
     */
    boolean shouldRun();

}
