package experiment.explorer.bandit;

/**
 * Created by bdanglot on 06/06/16.
 */
public class LapBudget implements Budget{

    private int nbLap;

    public LapBudget(int nb) {
        this.nbLap = nb;
    }

    @Override
    public boolean shouldRun() {
        return this.nbLap-- > 0;
    }

}
