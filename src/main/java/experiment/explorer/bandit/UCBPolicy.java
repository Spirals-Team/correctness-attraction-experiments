package experiment.explorer.bandit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdanglot on 06/06/16.
 */
public class UCBPolicy extends PolicyImpl {

    private final int ALPHA = 2;

    private int nbLap = 1;

    public UCBPolicy(int nbArm) {
        this(nbArm, 23L);
    }

    public UCBPolicy(int nbArm, long seed) {
        super(nbArm, seed);
    }

    @Override
    public int selectArm() {
        double max = Double.MIN_VALUE;
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < super.nbPull.length ; i++) {
            double probability = super.nbSuccessPerArm[i] / super.nbPull [i];
            double current = probability + Math.sqrt( (this.ALPHA * Math.log(nbLap) / nbPull[i]));
            if (current == max)
                indices.add(i);
            else if (current > max) {
                indices.clear();
                indices.add(i);
                max = current;
            }
        }
        this.nbLap++;
        return indices.isEmpty() ? super.random.nextInt(super.nbPull.length) : indices.get(super.random.nextInt(indices.size()));
    }
}
