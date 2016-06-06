package experiment.explorer.bandit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by bdanglot on 06/06/16.
 */
public class EpsilonGreedyPolitic extends PoliticImpl {

    public EpsilonGreedyPolitic(int nbArm, double  epsilon) {
        this(nbArm, epsilon, 23);
    }

    public EpsilonGreedyPolitic(int nbArm, double  epsilon, long seed) {
        super(nbArm, epsilon, seed);
    }

    @Override
    public int selectArm() {
        List<Integer> indicesMax = new ArrayList<>();
        double max = Double.MIN_VALUE;
        if (this.random.nextDouble() <= 1.0d - this.epsilon) {
            for (int i = 0 ; i < probability.length; i++) {
                if (max == probability[i])
                    indicesMax.add(i);
                else if (max < probability[i]) {
                    indicesMax.clear();
                    indicesMax.add(i);
                    max = probability[i];
                }
            }
        }
        return !indicesMax.isEmpty() ? indicesMax.get(this.random.nextInt(indicesMax.size())) : this.random.nextInt(probability.length);
    }
}
