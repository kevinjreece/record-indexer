package server.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import shared.model.*;

/**
 * Controls access to the fields table of the database
 * @author kevinjreece
 */
public class FieldsDAO {
	private static Logger _logger;
	
	static {
		_logger = Logger.getLogger("recordindexer");
	}

	private IndexerDatabase _db;
	
	FieldsDAO(IndexerDatabase db) {
		this._db = db;
	}
	
	/**
	 * Adds a new field to the database
	 * @author				kevinjreece
	 * @param	new_field	the new image object to add
	 * @return				true if operation succeeded, false otherwise
	 * @throws				DatabaseException 
	 * */
	public int addField(Field new_field) throws DatabaseException {
		_logger.entering("server.database.FielsDAO", "addField");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "insert into fields" 
							+ "(project_id, title, x_coord, width, "
							+ "help_html, known_data, column_number) "
							+ "values (?, ?, ?, ?, ?, ?, ?)";
			stmt = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, new_field.getProjectId());
			stmt.setString(2, new_field.getTitle());
			stmt.setInt(3, new_field.getXCoord());
			stmt.setInt(4, new_field.getWidth());
			stmt.setString(5, new_field.getHelpHtml());
			stmt.setString(6, new_field.getKnownData());
			stmt.setInt(7, new_field.getColumnNumber());
			
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = _db.getConnection().createStatement();
				rs = keyStmt.executeQuery("select last_insert_rowid()");
				rs.next();
				int id = rs.getInt(1);
				new_field.setFieldId(id);
			}
			else {
				throw new DatabaseException("Could not insert field");
			}
		}
		catch (SQLException err) {
			throw new DatabaseException("Could not insert field", err);
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		return new_field.getFieldId();
	}
	
	/**
	 * Gets a Field object from the database
	 * */
	public Field getField(int field_id) throws DatabaseException {
		_logger.entering("server.database.FieldsDAO", "getField");
		
		Field field = new Field();
		field.setFieldId(field_id);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from `fields` "
							+ "where field_id=?";
			stmt  = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, field_id);
			rs = stmt.executeQuery();
			rs.next();
			field.setProjectId(rs.getInt("project_id"));
			field.setTitle(rs.getString("title"));
			field.setXCoord(rs.getInt("x_coord"));
			field.setWidth(rs.getInt("width"));
			field.setHelpHtml(rs.getString("help_html"));
			field.setKnownData(rs.getString("known_data"));
			field.setColumnNumber(rs.getInt("column_number"));
		}
		catch (SQLException err) {
			throw new DatabaseException("Unable to get image", err);
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		
		return field;
	}
	
	/**
	 * Gets all fields for a project
	 * @author				kevinjreece
	 * @param	project_id	the id of the project whose fields are requested
	 * @return				the fields from the project
	 * */
	public List<Field> getAllFields(int project_id) throws DatabaseException {
		_logger.entering("server.database.FieldsDAO", "getAllFields");
		
		ArrayList<Field> result = new ArrayList<Field>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from `fields` "
							+ "where project_id=?";
			stmt  = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, project_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int field_id = rs.getInt("field_id");
				String title = rs.getString("title");
				int x_coord = rs.getInt("x_coord");
				int width = rs.getInt("width");
				String help_html = rs.getString("help_html");
				String known_data = rs.getString("known_data");
				int column_number = rs.getInt("column_number");
				
				result.add(new Field(field_id, project_id, title, x_coord, width, help_html, known_data, column_number));
			}
		}
		catch (SQLException err) {
			throw new DatabaseException("Unable to get all fields", err);
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		
		return result;
	}

	/**
	 * Gets all fields in the database
	 * @author kevinjreece
	 * */
	public List<Field> getAllFields() throws DatabaseException{
		_logger.entering("server.database.FieldsDAO", "getAllFields");
		
		ArrayList<Field> result = new ArrayList<Field>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from `fields`";
			stmt  = _db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int field_id = rs.getInt("field_id");
				int project_id = rs.getInt("project_id");
				String title = rs.getString("title");
				int x_coord = rs.getInt("x_coord");
				int width = rs.getInt("width");
				String help_html = rs.getString("help_html");
				String known_data = rs.getString("known_data");
				int column_number = rs.getInt("column_number");
				
				result.add(new Field(field_id, project_id, title, x_coord, width, help_html, known_data, column_number));
			}
		}
		catch (SQLException err) {
			throw new DatabaseException("Unable to get all fields", err);
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		
		return result;
	}
	
	/**
	 * Gets the id for the field of a project at a certain column
	 * @author kevinjreece
	 * */
	public int getFieldId(int project_id, int col_num) throws DatabaseException {
		_logger.entering("server.database.FieldsDAO", "getFieldId");
		
		int field_id = -1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select field_id from `fields` "
							+ "where project_id=? and column_number=?";
			stmt  = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, project_id);
			stmt.setInt(2, col_num);
			rs = stmt.executeQuery();
			rs.next();
			field_id = rs.getInt("field_id");
		}
		catch (SQLException err) {
			throw new DatabaseException("Unable to get image", err);
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		
		return field_id;
	}
	
	/**
	 * Creates the fields table in the database
	 * @author kevinjreece
	 * @throws DatabaseException 
	 */
	public void createFieldsTable() throws DatabaseException {
		_logger.entering("server.database.FieldsDAO", "createFieldsTable");
		
		Statement stmt = null;
		
		try {
			String sql = "drop table if exists fields";
			stmt = _db.getConnection().createStatement();
			stmt.executeUpdate(sql);
			
			sql = "create table fields ("
					+ "field_id integer primary key autoincrement not null,"
					+ "project_id integer not null,"	
					+ "title text not null,"
					+ "x_coord integer not null,"
					+ "width integer not null,"
					+ "help_html text not null,"
					+ "known_data text not null,"
					+ "column_number integer not null)";
			stmt = _db.getConnection().createStatement();
			stmt.executeUpdate(sql);
		}
		catch (SQLException err) {
			throw new DatabaseException("Could not create fields table", err);
		}
		
		return;
	}
}
