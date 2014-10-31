package server.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import shared.model.*;

/**
 * The class that controls access to the images table in the database
 * @author kevinjreece
 */
public class ImagesDAO {
	private static Logger _logger;
	
	static {
		_logger = Logger.getLogger("recordindexer");
	}

	private IndexerDatabase _db;
	
	ImagesDAO(IndexerDatabase db) {
		this._db = db;
	}
	
	/**
	 * Adds a new image to the database
	 * @author				kevinjreece
	 * @param	new_image	The new image object to add
	 * @return				true if operation succeeded, false otherwise
	 * @throws				DatabaseException 
	 * */
	public int addImage(Image new_image) throws DatabaseException {
		_logger.entering("server.database.ImagesDAO", "addImage");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "insert into images" 
							+ "(project_id, image_url, status, current_user_id) "
							+ "values (?, ?, ?, ?)";
			stmt = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, new_image.getProjectId());
			stmt.setString(2, new_image.getImageUrl());
			stmt.setInt(3, new_image.getStatus());
			stmt.setInt(4, new_image.getCurrentUserId());
			
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = _db.getConnection().createStatement();
				rs = keyStmt.executeQuery("select last_insert_rowid()");
				rs.next();
				int id = rs.getInt(1);
				new_image.setImageId(id);
			}
			else {
				throw new DatabaseException("Could not insert image");
			}
		}
		catch (SQLException err) {
			throw new DatabaseException("Could not insert image", err);
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		return new_image.getImageId();
	}
	
	/**
	 * Gets an image object by image_id
	 * @author kevinjreece 
	 * @throws DatabaseException 
	 * */
	public Image getImage(int image_id) throws DatabaseException {
		_logger.entering("server.database.ImagesDAO", "getImage");
		
		Image image = new Image();
		image.setImageId(image_id);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from `images` "
							+ "where image_id=?";
			stmt  = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, image_id);
			rs = stmt.executeQuery();
			rs.next();
			image.setProjectId(rs.getInt("project_id"));
			image.setImageUrl(rs.getString("image_url"));
			image.setStatus(rs.getInt("status"));
			image.setCurrentUserId(rs.getInt("current_user_id"));
		}
		catch (SQLException err) {
			throw new DatabaseException("Unable to get image", err);
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		
		return image;
	}
	
	/**
	 * Gets all images in the database
	 * @author kevinjreece
	 */
 	public List<Image> getAllImages() throws DatabaseException {
		_logger.entering("server.database.ImagesDAO", "getAllImages");
		
		ArrayList<Image> result = new ArrayList<Image>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from `images`";
			stmt  = _db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int image_id = rs.getInt("image_id");
				int project_id = rs.getInt("project_id");
				String file_url = rs.getString("image_url");
				int status = rs.getInt("status");
				int current_user_id = rs.getInt("current_user_id");
				
				result.add(new Image(image_id, project_id, file_url, status, current_user_id));
			}
		}
		catch (SQLException err) {
			throw new DatabaseException("Unable to get all images", err);
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		
		return result;
	}
	
	/**
	 * Gets a sample image for a project
	 * @author				kevinjreece
	 * @param	project_id	the id of the project the sample image is in
	 * @return				the sample image
	 * */
	public Image getSampleImage(int project_id) throws DatabaseException {
		_logger.entering("server.database.ImagesDAO", "getImage");
		
		Image image = new Image();
		image.setProjectId(project_id);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from `images` "
							+ "where project_id=?";
			stmt  = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, project_id);
			rs = stmt.executeQuery();
			rs.next();
			image.setImageId(rs.getInt("image_id"));
			image.setImageUrl(rs.getString("image_url"));
			image.setStatus(rs.getInt("status"));
			image.setCurrentUserId(rs.getInt("current_user_id"));
		}
		catch (SQLException err) {
			throw new DatabaseException("Unable to get sample image", err);
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		
		return image;
	}
	
	/**
	 * Gets an incomplete image for a project
	 * @author				kevinjreece
	 * @param	project_id	the id of the project the incomplete image is in
	 * @return				the incomplete image
	 * */
	public Image getIncompleteImage(int project_id) throws DatabaseException {
		_logger.entering("server.database.ImagesDAO", "getIncompleteImage");
		
		Image image = new Image();
		image.setProjectId(project_id);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from `images` "
							+ "where project_id=? and status=?";
			stmt  = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, project_id);
			stmt.setInt(2, Image.INCOMPLETE);
			rs = stmt.executeQuery();
			rs.next();
			image.setImageId(rs.getInt("image_id"));
			image.setImageUrl(rs.getString("image_url"));
			image.setStatus(rs.getInt("status"));
			image.setCurrentUserId(rs.getInt("current_user_id"));
		}
		catch (SQLException err) {
			throw new DatabaseException("Unable to get incomplete image", err);
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		
		return image;	
	}
	
	/**
	 * Assigns an image to a user
	 * @author				kevinjreece
	 * @param	image_id	the id of the image being assigned
	 * @param	user_id		the id of the user being assigned the image
	 * @return				true if operation succeeded, false otherwise
	 * */
	public void assignImageToUser(int image_id, int user_id) throws DatabaseException {
		_logger.entering("server.database.ImagesDAO", "assignImageToUser");
		
		PreparedStatement stmt = null;
		
		try {
			String query = "update `images` "
							+ "set current_user_id=? "
							+ "where image_id=?";
			stmt = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, user_id);
			stmt.setInt(2, image_id);
			stmt.executeUpdate();
		}
		catch (SQLException err) {
			DatabaseException serverEx = new DatabaseException(err.getMessage(), err);
			_logger.throwing("server.database.ImagesDAO", "assignImageToUser", serverEx);
			throw serverEx;
		}
		finally {
			IndexerDatabase.safeClose(stmt);
		}
				
		return;
	}
	
	/**
	 * Unassigns an image from the current user
	 * @author				kevinjreece
	 * @param	image_id	the id of the image being unassigned
	 * @return				true if operation succeeded, false otherwise
	 * */
	public void unassignImage(int image_id) throws DatabaseException {
		assignImageToUser(image_id, -1);
		return;
	}
	
	/**
	 * Sets the status of an image to 0 (inactive), 1 (active), or 2 (complete)
	 * @author				kevinjreece
	 * @param	image_id	the id of the image whose status to change
	 * @param	status		the status to set on the images
	 * @return				true if operation succeeded, false otherwise
	 * */
	public void setStatus(int image_id, int status) throws DatabaseException {
		_logger.entering("server.database.ImagesDAO", "setStatus");
		
		if (status != Image.INCOMPLETE
			&& status != Image.ACTIVE 
			&& status != Image.COMPLETE)
			throw new DatabaseException("Incorrect value for image status");
		
		PreparedStatement stmt = null;
		
		try {
			String query = "update `images` "
							+ "set status=? "
							+ "where image_id=?";
			stmt = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, status);
			stmt.setInt(2, image_id);
			stmt.executeUpdate();
		}
		catch (SQLException err) {
			DatabaseException serverEx = new DatabaseException(err.getMessage(), err);
			_logger.throwing("server.database.ImagesDAO", "setStatus", serverEx);
			throw serverEx;
		}
		finally {
			IndexerDatabase.safeClose(stmt);
		}
		
		return;
	}

	/**
	 * Creates the images table in the database
	 * @author kevinjreece
	 * @throws DatabaseException
	 */
	public void createImagesTable() throws DatabaseException {
		_logger.entering("server.database.ImagesDAO", "createImagesTable");
		
		Statement stmt = null;
		
		try {
			String sql = "drop table if exists images";
			stmt = _db.getConnection().createStatement();
			stmt.executeUpdate(sql);
			
			sql = "create table images ("
					+ "image_id integer primary key autoincrement not null,"
					+ "project_id integer not null,"
					+ "image_url text not null,"
					+ "status integer not null,"
					+ "current_user_id integer not null)";
			stmt = _db.getConnection().createStatement();
			stmt.executeUpdate(sql);	
		}
		catch (SQLException err) {
			throw new DatabaseException("Could not create images table", err);
		}
		
		return;
	}
}








