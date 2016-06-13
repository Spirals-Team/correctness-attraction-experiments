

package perturbation.enactor;

import java.util.Random;

public class RandomEnactorImpl implements Enactor {
    protected float epsilon;

    protected Random rnd;

    public RandomEnactorImpl() {
        RandomEnactorImpl.this.epsilon = 0.05F;
        RandomEnactorImpl.this.rnd = new Random();
    }

    public RandomEnactorImpl(float epsilon) {
        RandomEnactorImpl.this.epsilon = epsilon;
        RandomEnactorImpl.this.rnd = new Random();
    }

    public RandomEnactorImpl(int seed, float epsilon) {
        RandomEnactorImpl.this.epsilon = epsilon;
        RandomEnactorImpl.this.rnd = new Random(seed);
    }

    @Override
    public boolean shouldBeActivated() {
        return (rnd.nextFloat()) < (RandomEnactorImpl.this.epsilon);
    }

    @Override
    public String toString() {
        return "RAND";
    }
}

