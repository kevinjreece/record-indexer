package shared.communication;

/**
 * Contains the parameters needed to validate a user
 * @author kevinjreece
 */
public class ValidateUser_Params {
	private String _username;
	private String _password;
	
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
