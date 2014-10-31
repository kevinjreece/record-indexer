package server.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

import shared.model.*;

/**
 * Controls access to the projects table in the database
 * @author kevinjreece
 */
public class ProjectsDAO {
	private static Logger _logger;
	
	static {
		_logger = Logger.getLogger("recordindexer");
	}

	private IndexerDatabase _db;
	
	public ProjectsDAO(IndexerDatabase db) {
		this._db = db;
	}
	
	/**
	 * Adds a new project to the database
	 * @author				kevinjreece
	 * @param	new_project	the new Project object to add
	 * @return				the id of the newly added project 
	 * */
	public int addProject(Project new_project) throws DatabaseException {
		_logger.entering("server.database.ProjectsDAO", "addProject");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "insert into `projects`" 
							+ "(title, records_per_image, first_y_coord, record_height) "
							+ "values (?, ?, ?, ?)";
			stmt = _db.getConnection().prepareStatement(query);
			stmt.setString(1, new_project.getTitle());
			stmt.setInt(2, new_project.getRecordsPerImage());
			stmt.setInt(3, new_project.getFirstYCoord());
			stmt.setInt(4, new_project.getRecordHeight());
			
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = _db.getConnection().createStatement();
				rs = keyStmt.executeQuery("select last_insert_rowid()");
				rs.next();
				int id = rs.getInt(1);
				new_project.setProjectId(id);
			}
			else {
				throw new DatabaseException("Could not insert project");
			}
		}
		catch (SQLException err) {
			throw new DatabaseException("Could not insert project", err);
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		
		return new_project.getProjectId();
	}
	
	/**
	 * Gets a project object by id
	 * @author				kevinjreece
	 * @param	project_id	the id of the project to query
	 * @return				the project object of the project that was queried
	 * */
	public Project getProject(int project_id) throws DatabaseException {
		_logger.entering("server.database.UsersDAO", "getUser");
		
		Project project = new Project();
		project.setProjectId(project_id);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from `projects` "
							+ "where project_id=?";
			stmt = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, project_id);
			rs = stmt.executeQuery();
			rs.next();
			project.setProjectId(rs.getInt("project_id"));
			project.setTitle(rs.getString("title"));
			project.setRecordsPerImage(rs.getInt("records_per_image"));
			project.setFirstYCoord(rs.getInt("first_y_coord"));
			project.setRecordHeight(rs.getInt("record_height"));
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
		
		return project;
	}
	
	/**
	 * Gets all the projects in the database
	 * @author	kevinjreece
	 * @return	a list of all the projects in the database
	 * */
	public List<Project> getAllProjects() throws DatabaseException {
	_logger.entering("server.database.UsersDAO", "getAllUsers");
			
		ArrayList<Project> result = new ArrayList<Project>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from `projects`";
			stmt  = _db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int project_id = rs.getInt("project_id");
				String title = rs.getString("title");
				int records_per_image = rs.getInt("records_per_image");
				int first_y_coord = rs.getInt("first_y_coord");
				int record_height = rs.getInt("record_height");
				
				result.add(new Project(project_id, title, records_per_image, first_y_coord, record_height));
			}
		}
		catch (SQLException err) {
			throw new DatabaseException("Unable to get all projects", err);
		}
		
		return result;
	}

	/**
	 * Creates the projects table in the database
	 * @author kevinjreece
	 * @throws DatabaseException 
	 */
	public void createProjectsTable() throws DatabaseException {
		_logger.entering("server.database.ProjectsDAO", "createProjectsTable");
		
		Statement stmt = null;
		
		try {
			String sql = "drop table if exists projects";
			stmt = _db.getConnection().createStatement();
			stmt.executeUpdate(sql);
			
			sql = "create table projects ("
					+ "project_id integer primary key autoincrement not null,"
					+ "title text not null,"
					+ "records_per_image integer not null,"
					+ "first_y_coord integer not null,"
					+ "record_height integer not null)";
			stmt = _db.getConnection().createStatement();
			stmt.executeUpdate(sql);
		}
		catch (SQLException err) {
			throw new DatabaseException("Could not create projects table", err);
		}
		
		return;
	}
}















