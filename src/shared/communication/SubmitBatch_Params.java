package shared.communication;

import java.util.List;

import shared.model.Value;

/**
 * Contains the parameters needed to submit values to the database
 * @author kevinjreece
 */
public class SubmitBatch_Params {
	private int _image_id;
	private List<Value> _new_values;
	private String _username;
	private String _password;

	/**
	 * @return the _new_values
	 */
	public List<Value> getNewValues() {
		return _new_values;
	}

	/**
	 * @param new_values the new_values to set
	 */
	public void setNewValues(List<Value> new_values) {
		this._new_values = new_values;
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

	/**
	 * @return the image_id
	 */
	public int getImageId() {
		return _image_id;
	}

	/**
	 * @param image_id the image_id to set
	 */
	public void setImageId(int image_id) {
		this._image_id = image_id;
	}
}
