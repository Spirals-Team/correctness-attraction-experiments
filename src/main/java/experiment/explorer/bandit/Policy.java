package experiment.explorer.bandit;

import java.util.List;

/**
 * Created by bdanglot on 06/06/16.
 */
public interface Policy {

    /**
     * @return the index of the arm to be pulled
     */
    int selectArm();

    /**
     * Notify that the given arm has been pulled
     * @param i
     */
    void armPulled(int i);

    /**
     * Notify that the given arm has been succeed
     * @param i
     */
    void successOnArm(int i);

    /**
     * Log state of bandit : #pull #success estimated probability...
     */
    String log();

    void filter(List<Integer> filter);
}
