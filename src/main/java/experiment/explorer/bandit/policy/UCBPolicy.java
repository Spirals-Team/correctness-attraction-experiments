package experiment.explorer.bandit.policy;

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

	private boolean allArmsHasBeenPulledOneTime;

	public UCBPolicy(int nbArm) {
		this(nbArm, 23L);
	}

	public UCBPolicy(int nbArm, long seed) {
		super(nbArm, seed);
		this.values = new double[nbArm];
		this.allArmsHasBeenPulledOneTime = false;
	}

	@Override
	public int selectArm() {
		double max = Double.MIN_VALUE;
		List<Integer> indices = new ArrayList<>();

        /* This ensure that we have at least pulled one time every arms */ // TODO Look if this is needed and it's not too expensive.
//        if (!this.allArmsHasBeenPulledOneTime) {
//            for (int i = 0; i < super.nbPull.length; i++) {
//                if (super.nbPull[i] == 0)
//                    return i;
//            }
//        }

		this.allArmsHasBeenPulledOneTime = true;

		for (int i = 0; i < super.nbPull.length; i++) {
			if (super.filterArm.contains(i))
				continue;
//            double probability = super.nbPull[i] > 0 ? (double) super.nbSuccessPerArm[i] / (double) super.nbPull[i] : 0.0d;
			double current = this.values[i] + Math.sqrt((this.ALPHA * Math.log(this.nbLap) / super.nbPull[i]));
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
			if (indices.isEmpty())
				return super.random.nextInt(super.nbPull.length);
			return indices.get(super.random.nextInt(indices.size()));
		}
		return indices.get(super.random.nextInt(indices.size()));
	}

	@Override
	public void update(int index, int reward) {
		super.update(index, reward);
		this.values[index] = ((double) (super.nbPull[index] - 1) / (double) super.nbPull[index]) * this.values[index] + ((double) 1 / (double) super.nbPull[index]) * reward;
	}

	@Override
	public String outStateAsString() {
		String out = String.valueOf(this.values[0]);
		for (int i = 1; i < this.values.length; i++) {
			out += " " + this.values[i];
		}
		return out + " " + this.nbLap + " " + super.outStateAsString();
	}

	@Override
	public String toString() {
		return "UCBPolicy alpha=" + this.ALPHA + "\n";
	}

	public static Policy buildFromString(String[] states, int numberOfArms, int position) {
		UCBPolicy policy = new UCBPolicy(numberOfArms);
		for (int i = 0; i < numberOfArms; i++)
			policy.values[i] = Double.parseDouble(states[position + i]);
		position += numberOfArms;
		policy.nbLap = Integer.parseInt(states[position]);
		position++;
		for (int i = 0; i < numberOfArms; i++)
			policy.nbPull[i] = Integer.parseInt(states[i + position]);
		position += numberOfArms;
		for (int i = 0; i < numberOfArms; i++)
			policy.nbSuccessPerArm[i] = Integer.parseInt(states[i + position]);
		return policy;
	}

}
