

package perturbation.enactor;


public class AlwaysEnactorImpl implements Enactor {
    @Override
    public boolean shouldBeActivated() {
        return true;
    }

    @Override
    public String toString() {
        return "ALWA";
    }
}

