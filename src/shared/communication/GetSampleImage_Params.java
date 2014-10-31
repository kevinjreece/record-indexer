package shared.communication;

/**
 * Contains the parameters needed to get a sample image of a project
 * @author kevinjreece
 */
public class GetSampleImage_Params {
	private int _project_id;
	private String _username;
	private String _password;
	
	/**
	 * @return the _project_id
	 */
	public int getProjectId() {
		return _project_id;
	}
	
	/**
	 * @param project_id the project_id to set
	 */
	public void setProjectId(int project_id) {
		this._project_id = project_id;
	}
	
	/**
	 * @return the _username
	 */
	public String getUsername() {
		return _username;
	}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this._username = username;
	}
	
	/**
	 * @return the _password
	 */
	public String getPassword() {
		return _password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this._password = password;
	}
}
