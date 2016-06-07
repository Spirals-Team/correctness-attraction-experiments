package experiment.explorer.bandit;

import experiment.Util;

import java.util.Random;

/**
 * Created by bdanglot on 06/06/16.
 */
public abstract class PolicyImpl implements Policy {

    protected int [] nbSuccessPerArm;

    protected int [] nbPull;

    protected Random random;

    public PolicyImpl(int nbArm, long seed) {
        this.random = new Random(seed);
        this.nbSuccessPerArm = new int[nbArm];
        this.nbPull = new int[nbArm];
    }

    @Override
    public void armPulled(int i) {
        this.nbPull[i]++;
    }

    @Override
    public void successOnArm(int i) {
        this.nbSuccessPerArm[i]++;
    }

    @Override
    public void log() {
        int totalSuccess = 0;
        int totalPull = 0;
        String format = "%-3s %-5s %-4s %-4s";
        System.out.println(String.format(format, "Loc", "P", "Succ", "Pull"));
        for (int i = 0; i < this.nbPull.length; i++) {
            double probability = this.nbSuccessPerArm[i] / this.nbPull [i];
            totalSuccess += this.nbSuccessPerArm[i];
            totalPull +=  this.nbPull[i];

            System.out.println(String.format(format, i, String.format("%.2f", probability), this.nbSuccessPerArm[i], this.nbPull[i]));
        }
        System.out.println(Util.getStringPerc(totalSuccess, totalPull));
    }
}
