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
     * Log state of bandit : #pull #success estimated probability...
     */
    String log();

    /**
     * Update state of bandit
     * @param index of the arm has been pulled
     * @param reward of the pull
     */
    void update(int index, int reward);

    void filter(List<Integer> filter);
}
