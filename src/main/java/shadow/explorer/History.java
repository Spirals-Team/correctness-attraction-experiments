package shadow.explorer;

import perturbation.location.PerturbationLocation;
import shadow.manager.Manager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bdanglot on 22/08/16.
 */
public class History {

	private Manager perturbationManager;
	private Map<HttpServletRequest, Set<PerturbationLocation>> activePointForRequest;
	private Map<PerturbationLocation, Integer> nbExecutionOfPoint;

	public History(Manager perturbationManager) {
		this.perturbationManager = perturbationManager;
		this.activePointForRequest = new HashMap<>();
		this.nbExecutionOfPoint = new HashMap<>();
	}

	public List<Integer> get(HttpServletRequest request) {
		List<Integer> indicesPoints = new ArrayList<>();
		int minExecution = Integer.MAX_VALUE;
		List<PerturbationLocation> activePointForThisRequest = (List<PerturbationLocation>) this.activePointForRequest.get(request);
		if (activePointForThisRequest == null)
			return null;
		for (PerturbationLocation perturbationLocation : activePointForThisRequest) {
			if (this.nbExecutionOfPoint.get(perturbationLocation) < minExecution) {
				minExecution = this.nbExecutionOfPoint.get(perturbationLocation);
				indicesPoints.clear();
				indicesPoints.add(this.perturbationManager.indexOfLocations(perturbationLocation));
			} else if (this.nbExecutionOfPoint.get(perturbationLocation) == minExecution) {
				indicesPoints.add(this.perturbationManager.indexOfLocations(perturbationLocation));
			}
		}
		return indicesPoints;
	}

	public void put(HttpServletRequest request, List<Integer> lst) {
		this.perturbationManager.getLocations().forEach(location -> {
			if (lst.contains(this.perturbationManager.getLocations().indexOf(location))) {
				this.activePointForRequest.get(request).add(location);
				this.nbExecutionOfPoint.replace(location, this.nbExecutionOfPoint.get(location) + 1);
			}
		});
	}

}
