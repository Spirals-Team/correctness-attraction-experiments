

package perturbation.enactor;


public class NTimeEnactorImpl implements Enactor {
    private int n;

    private int timeCall;

    public NTimeEnactorImpl() {
        NTimeEnactorImpl.this.n = 1;
        NTimeEnactorImpl.this.timeCall = 0;
    }

    public NTimeEnactorImpl(int n) {
        NTimeEnactorImpl.this.n = n;
        NTimeEnactorImpl.this.timeCall = 0;
    }

    @Override
    public boolean shouldBeActivated() {
        return (NTimeEnactorImpl.this.n) > ((NTimeEnactorImpl.this.timeCall)++);
    }

    @Override
    public String toString() {
        return "NTIM";
    }
}

