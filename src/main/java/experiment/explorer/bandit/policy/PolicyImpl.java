package experiment.explorer.bandit.policy;

import experiment.Main;
import experiment.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by bdanglot on 06/06/16.
 */
public abstract class PolicyImpl implements Policy {

    protected int[] nbPull;

    protected int[] nbSuccessPerArm;

    protected Random random;

    protected List<Integer> filterArm;

    public PolicyImpl(int nbArm, long seed) {
        this.random = new Random(seed);
        this.nbSuccessPerArm = new int[nbArm];
        this.nbPull = new int[nbArm];
        this.filterArm = new ArrayList<>();
    }

    @Override
    public void filter(List<Integer> filter) {
        this.filterArm = filter;
    }

    @Override
    public void update(int index, int reward) {
        this.nbPull[index]++;
        this.nbSuccessPerArm[index] += reward;
    }

    @Override
    public String log() {
        String out = "";
        int totalSuccess = 0;
        int totalPull = 0;
        String format = "%-3s %-5s %-4s %-4s";
        if (Main.verbose)
            System.out.println(String.format(format, "Loc", "P", "Succ", "Pull"));
        out += String.format(format, "Loc", "P", "Succ", "Pull") + "\n";
        for (int i = 0; i < this.nbPull.length; i++) {
            double probability = (double) this.nbSuccessPerArm[i] / (double) this.nbPull[i];
            totalSuccess += this.nbSuccessPerArm[i];
            totalPull += this.nbPull[i];
            if (Main.verbose && this.nbPull[i] > 0)
                System.out.println(String.format(format, i, String.format("%.2f", probability), this.nbSuccessPerArm[i], this.nbPull[i]));
            out += String.format(format, i, String.format("%.2f", probability), this.nbSuccessPerArm[i], this.nbPull[i]) + "\n";
        }
        if (Main.verbose)
            System.out.println(Util.getStringPerc(totalSuccess, totalPull));
        out += Util.getStringPerc(totalSuccess, totalPull) + "\n";
        return out;
    }

    @Override
    public String outStateAsString() {
        String out = String.valueOf(this.nbPull[0]);
        for (int i = 1; i < this.nbPull.length; i++)
            out += " " + this.nbPull[i];
        out += " " + this.nbSuccessPerArm[0];
        for (int i = 1; i < this.nbSuccessPerArm.length; i++)
            out += " " + this.nbSuccessPerArm[i];
        return out;
    }
}
