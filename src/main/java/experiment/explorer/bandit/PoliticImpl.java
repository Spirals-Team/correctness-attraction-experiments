package experiment.explorer.bandit;

import experiment.Util;

import javax.xml.bind.SchemaOutputResolver;
import java.util.Random;

/**
 * Created by bdanglot on 06/06/16.
 */
public abstract class PoliticImpl implements Politic {

    protected double [] probability;

    protected int [] nbSuccessPerArm;

    protected int [] nbPull;

    protected Random random;

    protected double epsilon;

    public PoliticImpl(int nbArm, double  epsilon, long seed) {
        this.epsilon = epsilon;
        this.random = new Random(seed);
        this.probability = new double[nbArm];
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
    public void update() {
        for (int i = 0; i < this.probability.length ; i++)
            this.probability[i] = this.nbPull[i] > 0 ? (double) this.nbSuccessPerArm[i] / (double) this.nbPull[i] : 0.0d;
    }

    @Override
    public void log() {
        int totalSuccess = 0;
        int totalPull = 0;
        String format = "%-3s %-5s %-4s %-4s";
        System.out.println(String.format(format, "Loc", "P", "Succ", "Pull"));
        for (int i = 0; i < this.probability.length; i++) {
            totalSuccess += this.nbSuccessPerArm[i];
            totalPull +=  this.nbPull[i];
            System.out.println(String.format(format, i, String.format("%.2f", this.probability[i]), this.nbSuccessPerArm[i], this.nbPull[i]));
        }
        System.out.println(Util.getStringPerc(totalSuccess, totalPull));
    }
}
