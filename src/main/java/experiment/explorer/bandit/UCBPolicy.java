package experiment.explorer.bandit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by bdanglot on 06/06/16.
 */
public class UCBPolicy extends PolicyImpl {

    private double[] values;

    private final int ALPHA = 2;

    private int nbLap = 1;

    public UCBPolicy(int nbArm) {
        this(nbArm, 23L);
    }

    public UCBPolicy(int nbArm, long seed) {
        super(nbArm, seed);
        this.values = new double[nbArm];
    }

    @Override
    public int selectArm() {
        double max = Double.MIN_VALUE;
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < super.nbPull.length; i++) {
            if (super.filterArm.contains(i))
                continue;
//            double probability = super.nbPull[i] > 0 ? (double) super.nbSuccessPerArm[i] / (double) super.nbPull[i] : 0.0d;
            double current = this.values[i] + Math.sqrt((this.ALPHA * Math.log(nbLap) / nbPull[i]));
            if (current == max)
                indices.add(i);
            else if (current > max) {
                indices.clear();
                indices.add(i);
                max = current;
            }
        }
        this.nbLap++;
        if (indices.isEmpty()) {
            IntStream.range(0, super.nbPull.length).filter(index -> !super.filterArm.contains(index)).forEach(indices::add);
//            if (indices.isEmpty())
//                return super.random.nextInt(super.nbPull.length);
            return indices.get(super.random.nextInt(indices.size()));
        }
        return indices.get(super.random.nextInt(indices.size()));
    }

    @Override
    public void update(int index, int reward) {
        super.update(index, reward);
        this.values[index] = ((double) (nbPull[index] - 1) / (double) nbPull[index]) * this.values[index] + ((double) 1 / (double) nbPull[index]) * reward;
    }

    @Override
    public String toString() {
        return "UCBPolicy alpha=" + ALPHA + "\n";
    }
}
