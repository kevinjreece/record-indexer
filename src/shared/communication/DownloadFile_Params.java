package shared.communication;

/**
 * Contains the parameters needed to download a file
 * @author kevinjreece
 */
public class DownloadFile_Params {
	private String _url;
	private String _username;
	private String _password;

	/**
	 * @return the _url
	 */
	public String getUrl() {
		return _url;
	}

	/**
	 * @param url the _url to set
	 */
	public void setUrl(String url) {
		this._url = url;
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
