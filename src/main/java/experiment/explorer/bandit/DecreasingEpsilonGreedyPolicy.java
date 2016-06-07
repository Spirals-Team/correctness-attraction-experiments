package experiment.explorer.bandit;

/**
 * Created by bdanglot on 06/06/16.
 */
public class DecreasingEpsilonGreedyPolicy extends EpsilonGreedyPolicy {

    private int t;

    public DecreasingEpsilonGreedyPolicy(int nbArm, double epsilon) {
        this(nbArm, epsilon, 23);
    }

    public DecreasingEpsilonGreedyPolicy(int nbArm, double epsilon, long seed) {
        super(nbArm, epsilon, seed);
        this.t = 1;
    }

    private double alpha() {
        double value = 1 / Math.log(2+t);
        this.t++;
        return value;
    }

    @Override
    public int selectArm() {
        int index  = super.selectArm();
        super.epsilon = super.epsilon * alpha();
        return index;
    }

}
