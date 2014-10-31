package shared.communication;

import shared.model.User;

/**
 * Contains the return value of the user validation
 * @author kevinjreece
 */
public class ValidateUser_Result {
	private boolean _is_valid;
	private User _user;

	/**
	 * @return the _is_valid
	 */
	public boolean isValid() {
		return _is_valid;
	}

	/**
	 * @param is_valid the is_valid to set
	 */
	public void setIsValid(boolean is_valid) {
		this._is_valid = is_valid;
	}

	/**
	 * @return the _user
	 */
	public User getUser() {
		return _user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this._user = user;
	}
}
