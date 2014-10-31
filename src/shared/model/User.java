package shared.model;

import org.w3c.dom.*;

import server.DataImporter;

/**
 * Models record indexer users
 * @author kevinjreece
 * */
public class User {
	private int _user_id = -1;
	private String _username = "";
	private String _password = "";
	private String _first_name = "";
	private String _last_name = "";
	private String _email = "";
	private int _indexed_records = 0;
	private int _current_image_id = -1;
	
	/**
	 * Creates a User object from a User element
	 * @author kevinjreece
	 */
	public User(Element user_element) {
		_username = DataImporter.getValue((Element) user_element.getElementsByTagName("username").item(0));
		_password = DataImporter.getValue((Element) user_element.getElementsByTagName("password").item(0));
		_first_name = DataImporter.getValue((Element) user_element.getElementsByTagName("firstname").item(0));
		_last_name = DataImporter.getValue((Element) user_element.getElementsByTagName("lastname").item(0));
		_email = DataImporter.getValue((Element) user_element.getElementsByTagName("email").item(0));
		_indexed_records = Integer.parseInt(DataImporter.getValue((Element) user_element.getElementsByTagName("indexedrecords").item(0)));
	}
	
	/**
	 * Creates a User object from individual parameters
	 * @author kevinjreece
	 */
	public User(int user_id, String username, String password, String first_name,
			String last_name, String email, int indexed_records, int current_image_id) {
		assert(username != "" && password != "" && first_name != "" && last_name != "" && email != "");
		setUserId(user_id);
		setUsername(username);
		setPassword(password);
		setFirstName(first_name);
		setLastName(last_name);
		setEmail(email);
		setIndexedRecords(indexed_records);
		setCurrentImageId(current_image_id);
	}

	/**
	 * Creates a default User object
	 */
	public User() {
		setUserId(-1);
		setUsername("new_user");
		setPassword(null);
		setFirstName(null);
		setLastName(null);
		setEmail(null);
		setIndexedRecords(0);
	}

	/**
	 * Converts a User object into a String
	 * @author kevinjreece
	 * */
	public String toString() {
		StringBuilder output = new StringBuilder();
		
		output.append("User ID: " + _user_id + "\n");
		output.append("Username: " + _username + "\n");
		output.append("Password: " + _password + "\n");
		output.append("First Name: " + _first_name + "\n");
		output.append("Last Name: " + _last_name + "\n");
		output.append("Email: " + _email + "\n");
		output.append("Indexed Records: " + _indexed_records + "\n");
		output.append("Current Image ID: " + _current_image_id + "\n");
		
		return output.toString();
	}
	
	/***/
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o.getClass() != this.getClass())
			return false;
		if (o == this)
			return true;
		
		User other = (User) o;
		
		return (_user_id == other.getUserId()
				&& _username.equals(other.getUsername())
				&& _password.equals(other.getPassword())
				&& _first_name.equals(other.getFirstName())
				&& _last_name.equals(other.getLastName())
				&& _email.equals(other.getEmail()));
	}
	
	/**
	 * @return the _user_id
	 */
	public int getUserId() {
		return _user_id;
	}
	
	/**
	 * @param user_id the user_id to set
	 */
	public void setUserId(int user_id) {
		this._user_id = user_id;
	}
	
	/**
	 * @return the _user_name
	 */
	public String getUsername() {
		return _username;
	}
	
	/**
	 * @param user_name the user_name to set
	 */
	public void setUsername(String user_name) {
		this._username = user_name;
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
	 * @return the _first_name
	 */
	public String getFirstName() {
		return _first_name;
	}
	
	/**
	 * @param first_name the first_name to set
	 */
	public void setFirstName(String first_name) {
		this._first_name = first_name;
	}
	
	/**
	 * @return the _last_name
	 */
	public String getLastName() {
		return _last_name;
	}
	
	/**
	 * @param last_name the last_name to set
	 */
	public void setLastName(String last_name) {
		this._last_name = last_name;
	}
	
	/**
	 * @return the _email
	 */
	public String getEmail() {
		return _email;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this._email = email;
	}
	
	/**
	 * @return the _indexed_records
	 */
	public int getIndexedRecords() {
		return _indexed_records;
	}
	
	/**
	 * @param indexed_records the indexed_records to set
	 */
	public void setIndexedRecords(int indexed_records) {
		this._indexed_records = indexed_records;
	}

	/**
	 * @return the _current_image_id
	 */
	public int getCurrentImageId() {
		return _current_image_id;
	}

	/**
	 * @param current_image_id the current_image_id to set
	 */
	public void setCurrentImageId(int current_image_id) {
		this._current_image_id = current_image_id;
	}
}
