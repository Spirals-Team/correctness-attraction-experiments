package experiment.explorer.bandit;

/**
 * Created by bdanglot on 06/06/16.
 */
public class DecreasingEpsilonGreedyPolitic extends EpsilonGreedyPolitic {

    private int t;

    public DecreasingEpsilonGreedyPolitic(int nbArm, double epsilon) {
        this(nbArm, epsilon, 23);
    }

    public DecreasingEpsilonGreedyPolitic(int nbArm, double epsilon, long seed) {
        super(nbArm, epsilon, seed);
        this.t = 1;
    }

    private double alpha() {
        double value = 1 / Math.log(2+t);
        t++;
        return value;
    }

    @Override
    public void update() {
        super.update();
        super.epsilon = super.epsilon * alpha();
    }
}
