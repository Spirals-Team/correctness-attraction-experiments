package experiment.explorer.bandit.policy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bdanglot on 28/07/16.
 */
public class RandomPolicy extends PolicyImpl {

	private boolean allArmsHasBeenPulledOneTime;

	public RandomPolicy(int nbArm) {
		super(nbArm, 23L);
		this.allArmsHasBeenPulledOneTime = false;
	}

	public RandomPolicy(int nbArm, long seed) {
		super(nbArm, seed);
		this.allArmsHasBeenPulledOneTime = false;
	}

	@Override
	public int selectArm() {

		List<Integer> indices = new ArrayList<>();

		for (int i = 0; i < super.nbPull.length ; i++) {
			if (!super.filterArm.contains(i))
				indices.add(i);
		}

		Collections.shuffle(indices, super.random);

		/* This ensure that we have at least pulled one time every arms */
		if (!this.allArmsHasBeenPulledOneTime) {
			for (Integer indice : indices) {
				if (nbPull[indice] == 0)
					return indice;
			}
		}
		this.allArmsHasBeenPulledOneTime = true;
		return indices.get(super.random.nextInt(indices.size()));
	}

	@Override
	public String outStateAsString() {
		String out = String.valueOf(this.nbPull[0]);
		for (int i = 1; i < this.nbPull.length; i++)
			out += " " + this.nbPull[i];
		return out;
	}

	public static Policy buildFromString(String[] states, int numberOfLocations, int position) {
		RandomPolicy randomPolicy = new RandomPolicy(numberOfLocations);
		for (int i = 0; i < randomPolicy.nbPull.length; i++) {
			randomPolicy.nbPull[i] = Integer.parseInt(states[i + position]);
		}
		return randomPolicy;
	}

	@Override
	public String toString() {
		return "Random policy\n";
	}
}
