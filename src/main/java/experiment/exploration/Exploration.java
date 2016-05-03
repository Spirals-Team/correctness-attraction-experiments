package experiment.exploration;

import perturbation.perturbator.Perturbator;

import java.util.List;

/**
 * Created by beyni on 01/05/16.
 */
public interface Exploration {

    String getName();

    String getColumnName();

    String getHeader();

    String[] getPerturbatorsName();

    List<Perturbator> getPerturbators();

    String getType();

}
