package experiment.explorer.bandit;

import experiment.Main;
import experiment.Util;

/**
 * Created by bdanglot on 06/06/16.
 */
public class LapBudget implements Budget{

    private int nbLap;

    private final int nbLapInit;

    public LapBudget(int nb) {
        this.nbLapInit = nb;
        this.nbLap = nb;
    }

    @Override
    public boolean shouldRun() {
        if (Main.verbose)
            System.out.println(Util.getStringPerc(this.nbLapInit - this.nbLap, this.nbLapInit));
        return this.nbLap-- > 0;
    }

    @Override
    public String toString() {
        return "LapBudget : " + this.nbLapInit + "\n";
    }

    @Override
    public String outStateAsString() {
        return this.nbLap + " " + this.nbLapInit + "\n";
    }

    public static Budget buildFromString(String budget) {
        LapBudget lapBudget = new LapBudget(Integer.parseInt(budget.split(" ")[1]));
        lapBudget.nbLap = Integer.parseInt(budget.split(" ")[0]);
        return lapBudget;
    }
}
