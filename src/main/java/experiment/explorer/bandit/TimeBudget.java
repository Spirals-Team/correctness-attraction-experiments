package experiment.explorer.bandit;

import experiment.Util;

/**
 * Created by bdanglot on 06/06/16.
 */
public class TimeBudget implements Budget {

    private final long start;

    private final long budget;

    public TimeBudget(long time) {
        this.budget = time;
        this.start = System.currentTimeMillis();
    }

    @Override
    public boolean shouldRun() {
        long timeElpased = System.currentTimeMillis() - this.start;
        System.out.println(Util.getStringPerc(timeElpased, this.budget));
        return System.currentTimeMillis() - this.start < budget;
    }
}
