package experiment.explorer.bandit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        for (int i = 0; i < super.nbPull.length; i++) {
            if (super.filterArm.contains(i))
                continue;
            double probability = super.nbPull[i] > 0 ? (double) super.nbSuccessPerArm[i] / (double) super.nbPull[i] : 0.0d;
            double current = probability + Math.sqrt((this.ALPHA * Math.log(nbLap) / nbPull[i]));
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
            return super.random.nextInt(indices.size());
        }
        return indices.get(super.random.nextInt(indices.size()));
    }

    @Override
    public String toString() {
        return "UCBPolicy alpha=" + ALPHA + "\n";
    }
}
