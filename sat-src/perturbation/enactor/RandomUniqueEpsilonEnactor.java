

package perturbation.enactor;


public class RandomUniqueEpsilonEnactor extends RandomEnactorImpl {
    private boolean firstTime;

    public RandomUniqueEpsilonEnactor() {
        super();
        RandomUniqueEpsilonEnactor.this.firstTime = true;
    }

    public RandomUniqueEpsilonEnactor(float epsilon) {
        super(epsilon);
        RandomUniqueEpsilonEnactor.this.firstTime = true;
    }

    public RandomUniqueEpsilonEnactor(int seed, float epsilon) {
        super(seed, epsilon);
        RandomUniqueEpsilonEnactor.this.firstTime = true;
    }

    @Override
    public boolean shouldBeActivated() {
        boolean activation = (super.shouldBeActivated()) && (RandomUniqueEpsilonEnactor.this.firstTime);
        RandomUniqueEpsilonEnactor.this.firstTime = !activation;
        return activation;
    }

    @Override
    public String toString() {
        return "RNDU";
    }
}

