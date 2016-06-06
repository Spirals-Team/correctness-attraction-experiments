package experiment.explorer.bandit;

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
        return System.currentTimeMillis() - this.start < budget;
    }
}
