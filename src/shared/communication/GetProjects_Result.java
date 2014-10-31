package shared.communication;

import java.util.List;
import shared.model.*;

/**
 * Contains the return value from getting all projects
 * @author kevinjreece
 */
public class GetProjects_Result {
	private List<Project> _all_projects;

	/**
	 * @return the _all_projects
	 */
	public List<Project> getAllProjects() {
		return _all_projects;
	}

	/**
	 * @param all_projects the all_projects to set
	 */
	public void setAllProjects(List<Project> all_projects) {
		this._all_projects = all_projects;
	}
	
}
