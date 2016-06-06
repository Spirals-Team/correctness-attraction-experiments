package experiment.explorer.bandit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by bdanglot on 06/06/16.
 */
public class UCBPolitic extends PoliticImpl {

    private final int ALPHA = 2;

    private int nbLap = 1;

    public UCBPolitic(int nbArm, double epsilon) {
        this(nbArm, epsilon, 23L);
    }

    public UCBPolitic(int nbArm, double epsilon, long seed) {
        super(nbArm, epsilon, seed);
    }

    @Override
    public int selectArm() {
        double max = Double.MIN_VALUE;
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < this.probability.length; i++) {
            double current = this.probability[i] + Math.sqrt( (this.ALPHA * Math.log(nbLap) / nbPull[i]));
            if (current == max)
                indices.add(i);
            else if (current > max) {
                indices.clear();
                indices.add(i);
                max = current;
            }
        }
        this.nbLap++;
        return indices.isEmpty() ? this.random.nextInt(this.probability.length) : indices.get(this.random.nextInt(indices.size()));
    }
}
