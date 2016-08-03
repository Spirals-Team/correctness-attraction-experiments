package experiment.explorer.bandit.budget;

import experiment.Util;

/**
 * Created by bdanglot on 06/06/16.
 */
public class TimeBudget implements Budget {

    private final long start;

    private final long budget;

    private long timeRemaining;

    public TimeBudget(long time) {
        this.budget = time;
        this.start = System.currentTimeMillis();
        this.timeRemaining = this.budget;
    }

    @Override
    public boolean shouldRun() {
        long timeElapsed = System.currentTimeMillis() - this.start;
//        if (Main.verbose)
            System.err.println(Util.getStringPerc(timeElapsed, this.budget));
        this.timeRemaining = this.budget - timeElapsed;
        return this.timeRemaining > 0;
    }

    @Override
    public String toString() {
        return "TimeBudget " + this.budget + " ms\n" ;
    }

    @Override
    public String outStateAsString() {
        return String.valueOf(this.timeRemaining);
    }

    public static Budget buildFromString(String budget) {
        return new TimeBudget(Long.parseLong(budget));
    }
}
