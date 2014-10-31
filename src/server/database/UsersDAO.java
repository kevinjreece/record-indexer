package server.database;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

import shared.model.*;

import java.sql.*;

/**
 * Controls access to the users table in the database
 * @author kevinjreece
 */
public class UsersDAO {
	private static Logger _logger;
	
	static {
		_logger = Logger.getLogger("recordindexer");
	}

	private IndexerDatabase _db;
	
	public UsersDAO(IndexerDatabase db) {
		this._db = db;
	}
	
	/**
	 * Adds a new user to the database
	 * @author				kevinjreece
	 * @param	new_user	the new User object to add
	 * @return				the id of the newly added User
	 * */
	public int addUser(User new_user) throws DatabaseException {
		_logger.entering("server.database.UsersDAO", "addUser");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "insert into users" 
							+ "(username, password, first_name, last_name, "
							+ "email, indexed_records, current_image_id) "
							+ "values (?, ?, ?, ?, ?, ?, ?)";
			stmt = _db.getConnection().prepareStatement(query);
			stmt.setString(1, new_user.getUsername());
			stmt.setString(2, new_user.getPassword());
			stmt.setString(3, new_user.getFirstName());
			stmt.setString(4, new_user.getLastName());
			stmt.setString(5, new_user.getEmail());
			stmt.setInt(6, new_user.getIndexedRecords());
			stmt.setInt(7, new_user.getCurrentImageId());
			
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = _db.getConnection().createStatement();
				rs = keyStmt.executeQuery("select last_insert_rowid()");
				rs.next();
				int id = rs.getInt(1);
				new_user.setUserId(id);
			}
			else {
				throw new DatabaseException("Could not insert user");
			}
		}
		catch (SQLException err) {
			throw new DatabaseException("Could not insert user", err);
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		return new_user.getUserId();
	}
	
	/**
	 * Gets a user object by username
	 * @author				kevinjreece
	 * @param	username	the username of the user to query
	 * @return				the user object of the user that was queried
	 * */
	public User getUser(String username) throws DatabaseException {
		_logger.entering("server.database.UsersDAO", "getUser");
		
		User user = new User();
		user.setUsername(username);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from `users` "
							+ "where username=?";
			stmt = _db.getConnection().prepareStatement(query);
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			rs.next();
			user.setUserId(rs.getInt(1));
			user.setPassword(rs.getString(3));
			user.setFirstName(rs.getString(4));
			user.setLastName(rs.getString(5));
			user.setEmail(rs.getString(6));
			user.setIndexedRecords(rs.getInt(7));
			user.setCurrentImageId(rs.getInt(8));
		}
		catch (SQLException err) {
			DatabaseException serverEx = new DatabaseException(err.getMessage(), err);
			_logger.throwing("server.database.UsersDAO", "getUser", serverEx);
			throw serverEx;
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		
		return user;
	}
	
	/**
	 * Gets a user object by user_id
	 * @author			kevinjreece
	 * @param	user_id	the username of the user to query
	 * @return			the user object of the user that was queried
	 * */
	public User getUser(int user_id) throws DatabaseException {
		_logger.entering("server.database.UsersDAO", "getUser");
		
		User user = new User();
		user.setUserId(user_id);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from `users` "
							+ "where user_id=?";
			stmt = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, user_id);
			rs = stmt.executeQuery();
			rs.next();
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
			user.setIndexedRecords(rs.getInt("indexed_records"));
			user.setCurrentImageId(rs.getInt("current_image_id"));
		}
		catch (SQLException err) {
			DatabaseException serverEx = new DatabaseException(err.getMessage(), err);
			_logger.throwing("server.database.UsersDAO", "getUser", serverEx);
			throw serverEx;
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		
		return user;
	}
	
	/**
	 * Gets all users in the database
	 * @author kevinjreece
	 */
	public List<User> getAllUsers() throws DatabaseException {
		_logger.entering("server.database.UsersDAO", "getAllUsers");
		
		ArrayList<User> result = new ArrayList<User>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from `users`";
			stmt  = _db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int user_id = rs.getInt("user_id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String email = rs.getString("email");
				int indexed_records = rs.getInt("indexed_records");
				int current_image_id = rs.getInt("current_image_id");
				
				result.add(new User(user_id, username, password, first_name, last_name, email, indexed_records, current_image_id));
			}
		}
		catch (SQLException err) {
			throw new DatabaseException("Unable to get all users", err);
		}
		
		return result;
	}
	
	/**
	 * Updates the id of the image a user is currently working on
	 * @author				kevinjreece
	 * @param	user_id		the id of the user working on a new image
	 * @param	image_id	the id of the image the user is working on
	 * */
	public void addCurrentImageId(int user_id, int image_id) throws DatabaseException {
		_logger.entering("server.database.UsersDAO", "addCurrentImageId");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "update `users` "
							+ "set current_image_id=? "
							+ "where user_id=?";
			stmt = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, image_id);
			stmt.setInt(2, user_id);
			stmt.executeUpdate();
		}
		catch (SQLException err) {
			DatabaseException serverEx = new DatabaseException(err.getMessage(), err);
			_logger.throwing("server.database.UsersDAO", "addCurrentImageId", serverEx);
			throw serverEx;
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
				
		return;
	}
	
	/**
	 * Clears the current image id of a user
	 * @author kevinjreece
	 * */
	public void clearCurrentImageId(int user_id) throws DatabaseException {
		addCurrentImageId(user_id, -1);
		return;
	}
	
	/**
	 * Increments the number of records a user has indexed
	 * @author			kevinjreece
	 * @param	user_id	the id of the user who indexed another record
	 * @return			true if operation succeeded, false otherwise
	 * */
	public void incrementIndexedRecords(int user_id) throws DatabaseException {
		_logger.entering("server.database.UsersDAO", "incrementIndexedRecords");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "update `users` "
							+ "set indexed_records = indexed_records + 1 "
							+ "where user_id=?";
			stmt = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, user_id);
			stmt.executeUpdate();
		}
		catch (SQLException err) {
			DatabaseException serverEx = new DatabaseException(err.getMessage(), err);
			_logger.throwing("server.database.UsersDAO", "incrementIndexedRecords", serverEx);
			throw serverEx;
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
				
		return;
	}

	/**
	 * Creates the uses table in the database
	 * @author kevinjreece
	 * @throws DatabaseException
	 */
	public void createUsersTable() throws DatabaseException {
		_logger.entering("server.database.UsersDAO", "createUsersTable");
		
		Statement stmt = null;
		
		try {
			String sql = "drop table if exists users";
			stmt = _db.getConnection().createStatement();
			stmt.executeUpdate(sql);
			
			sql = "create table `users` ("
					+ "user_id integer primary key autoincrement not null,"
					+ "username text not null,"
					+ "password text not null,"
					+ "first_name text not null,"
					+ "last_name text not null,"
					+ "email text not null,"
					+ "indexed_records integer not null,"
					+ "current_image_id integer not null)";
			stmt = _db.getConnection().createStatement();
			stmt.executeUpdate(sql);
		}
		catch (SQLException err) {
			throw new DatabaseException("Could not create users table", err);
		}
		
		return;
	}
}










