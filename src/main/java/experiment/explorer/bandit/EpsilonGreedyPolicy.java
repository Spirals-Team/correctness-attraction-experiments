package experiment.explorer.bandit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdanglot on 06/06/16.
 */
public class EpsilonGreedyPolicy extends PolicyImpl {

    protected double epsilon;

    public EpsilonGreedyPolicy(int nbArm, double  epsilon) {
        this(nbArm, epsilon, 23);
    }

    public EpsilonGreedyPolicy(int nbArm, double  epsilon, long seed) {
        super(nbArm, seed);
        this.epsilon = epsilon;
    }

    @Override
    public int selectArm() {
        List<Integer> indicesMax = new ArrayList<>();
        double max = Double.MIN_VALUE;
        if (this.random.nextDouble() <= 1.0d - this.epsilon) {
            for (int i = 0 ; i < super.nbPull.length; i++) {
                double probability = super.nbSuccessPerArm[i] / super.nbPull[i];
                if (max == probability)
                    indicesMax.add(i);
                else if (max < probability) {
                    indicesMax.clear();
                    indicesMax.add(i);
                    max = probability;
                }
            }
        }
        return !indicesMax.isEmpty() ? indicesMax.get(this.random.nextInt(indicesMax.size())) : this.random.nextInt(super.nbPull.length);
    }
}
