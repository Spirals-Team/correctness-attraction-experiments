package shadow.budget;

import experiment.Util;

/**
 * Created by bdanglot on 16/08/16.
 */
public class NbRequestBudget implements Budget {

	private int nbRequest;

	private int nbRequestDone;

	public NbRequestBudget(int nbRequest) {
		this.nbRequest = nbRequest;
		this.nbRequestDone = 0;
	}

	public boolean hasToRun() {
		return this.nbRequest > this.nbRequestDone;
	}

	public void update() {
		this.nbRequestDone++;
	}

	@Override
	public String toString() {
		return Util.getStringPerc(this.nbRequestDone, this.nbRequest);
	}
}
