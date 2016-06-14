package experiment.explorer.bandit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by bdanglot on 06/06/16.
 */
public class EpsilonGreedyPolicy extends PolicyImpl {

    protected double epsilon;

    public EpsilonGreedyPolicy(int nbArm, double epsilon) {
        this(nbArm, epsilon, 23);
    }

    public EpsilonGreedyPolicy(int nbArm, double  epsilon, long seed) {
        super(nbArm, seed);
        this.epsilon = epsilon;
    }

    @Override
    public int selectArm() {
        List<Integer> indices = new ArrayList<>();
        double max = Double.MIN_VALUE;
        if (this.random.nextDouble() > 1.0d - this.epsilon) {
            for (int i = 0 ; i < super.nbPull.length ; i++) {
                if (super.filterArm.contains(i))
                    continue;
                double probability = (double)super.nbSuccessPerArm[i] / (double)super.nbPull[i];
                if (max == probability)
                    indices.add(i);
                else if (max < probability) {
                    indices.clear();
                    indices.add(i);
                    max = probability;
                }
            }
        }

        if (indices.isEmpty()) {
            IntStream.range(0, super.nbPull.length).filter(index -> !super.filterArm.contains(index)).forEach(indices::add);
            return super.random.nextInt(indices.size());
        }
        return indices.get(super.random.nextInt(indices.size()));
    }

    @Override
    public String toString() {
        return "EpsilonGreedyPolicy " + this.epsilon + "\n" ;

    }
}
