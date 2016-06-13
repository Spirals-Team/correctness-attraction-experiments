

package perturbation.location;

import java.util.ArrayList;
import perturbation.enactor.Enactor;
import java.lang.reflect.Field;
import perturbation.perturbator.InvPerturbatorImpl;
import java.util.List;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.perturbator.NothingPerturbatorImpl;
import perturbation.perturbator.Perturbator;

public class PerturbationLocationImpl implements PerturbationLocation {
    private final String locationType;

    private final int locationIndex;

    private final String locationInCode;

    private Perturbator perturbator = new NothingPerturbatorImpl();

    private Enactor enactor = new NeverEnactorImpl();

    @Override
    public int getLocationIndex() {
        return PerturbationLocationImpl.this.locationIndex;
    }

    @Override
    public String getLocationInCode() {
        return PerturbationLocationImpl.this.locationInCode;
    }

    @Override
    public String getType() {
        return PerturbationLocationImpl.this.locationType;
    }

    private PerturbationLocationImpl() {
        this.locationInCode = "";
        this.locationIndex = -1;
        this.locationType = "";
    }

    public PerturbationLocationImpl(String location, int index, String type) {
        this.locationInCode = location;
        this.locationIndex = index;
        this.locationType = type;
        PerturbationLocationImpl.this.perturbator = new InvPerturbatorImpl();
    }

    public Perturbator getPerturbator() {
        return PerturbationLocationImpl.this.perturbator;
    }

    public void setPerturbator(Perturbator pertubator) {
        PerturbationLocationImpl.this.perturbator = pertubator;
    }

    @Override
    public Enactor getEnactor() {
        return PerturbationLocationImpl.this.enactor;
    }

    @Override
    public void setEnactor(Enactor enactor) {
        PerturbationLocationImpl.this.enactor = enactor;
    }

    @Override
    public String toString() {
        return ((((locationIndex) + "\t") + (locationInCode)) + "\t") + (locationType);
    }

    @Override
    public boolean equals(Object that) {
        return (that instanceof PerturbationLocationImpl) && ((PerturbationLocationImpl.this.locationIndex) == (((PerturbationLocationImpl) (that)).locationIndex));
    }

    public static List<PerturbationLocation> getLocationFromClass(Class clazz) {
        Field[] fields = clazz.getFields();
        List<PerturbationLocation> locations = new ArrayList<PerturbationLocation>();
        for (int i = 0; i < (fields.length); i++) {
            fields[i].setAccessible(true);
            if (fields[i].getName().startsWith("__L"))
                try {
                    locations.add(((PerturbationLocation) (fields[i].get(null))));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            
        }
        return locations;
    }
}

