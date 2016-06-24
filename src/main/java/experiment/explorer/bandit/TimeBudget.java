package experiment.explorer.bandit;

import experiment.Main;
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
        long timeElapsed = System.currentTimeMillis() - this.start;
        if (Main.verbose)
            System.out.println(Util.getStringPerc(timeElapsed, this.budget));
        return System.currentTimeMillis() - this.start < budget;
    }

    @Override
    public String toString() {
        return "TimeBudget " + this.budget + " ms\n" ;
    }
}
