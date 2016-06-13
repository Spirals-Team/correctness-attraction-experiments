

package perturbation.enactor;


public class EnactorDecorator implements Enactor {
    protected Enactor decoratedEnactor;

    public EnactorDecorator(Enactor decoratedEnactor) {
        super();
        EnactorDecorator.this.decoratedEnactor = decoratedEnactor;
    }

    @Override
    public boolean shouldBeActivated() {
        return EnactorDecorator.this.decoratedEnactor.shouldBeActivated();
    }

    @Override
    public String toString() {
        return EnactorDecorator.this.decoratedEnactor.toString();
    }
}

