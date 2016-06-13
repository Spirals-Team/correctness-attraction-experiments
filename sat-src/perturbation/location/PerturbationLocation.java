

package perturbation.location;

import perturbation.enactor.Enactor;
import perturbation.perturbator.Perturbator;

public interface PerturbationLocation {
    int getLocationIndex();

    String getLocationInCode();

    String getType();

    Perturbator getPerturbator();

    void setPerturbator(Perturbator pertubator);

    Enactor getEnactor();

    void setEnactor(Enactor enactor);
}

