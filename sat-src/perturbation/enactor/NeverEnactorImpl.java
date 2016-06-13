

package perturbation.enactor;


public class NeverEnactorImpl implements Enactor {
    @Override
    public boolean shouldBeActivated() {
        return false;
    }
}

