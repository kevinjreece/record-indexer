package server.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import shared.model.*;

/**
 * The class that controls access to the values table in the database
 * @author kevinjreece
 */
public class ValuesDAO {
	private static Logger _logger;
	
	static {
		_logger = Logger.getLogger("recordindexer");
	}

	private IndexerDatabase _db;
	
	ValuesDAO(IndexerDatabase db) {
		this._db = db;
	}
	
	/**
	 * Adds a new value to the database 
	 * @author				kevinjreece
	 * @param	new_value	the new value object to add
	 * @return				true if operation succeeded, false otherwise
	 * @throws DatabaseException 
	 * */
	public int addValue(Value new_value) throws DatabaseException {
		_logger.entering("server.database.ImagesDAO", "addImage");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "insert into `values`" 
							+ "(field_id, image_id, image_url, value, row_number, column_number) "
							+ "values (?, ?, ?, ?, ?, ?)";
			stmt = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, new_value.getFieldId());
			stmt.setInt(2, new_value.getImageId());
			stmt.setString(3, new_value.getImageUrl());
			stmt.setString(4, new_value.getValue());
			stmt.setInt(5, new_value.getRowNumber());
			stmt.setInt(6, new_value.getColumnNumber());
			
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = _db.getConnection().createStatement();
				rs = keyStmt.executeQuery("select last_insert_rowid()");
				rs.next();
				int id = rs.getInt(1);
				new_value.setValueId(id);
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
		return new_value.getValueId();
	}
	
	/**
	 * Gets a value from the database
	 * @author kevinjreece
	 * */
	public Value getValue(int value_id) throws DatabaseException {
		_logger.entering("server.database.ValuesDAO", "getValue");
		
		Value value = new Value();
		value.setValueId(value_id);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from `values` "
							+ "where value_id=?";
			stmt = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, value_id);
			rs = stmt.executeQuery();
			rs.next();
			value.setValue(rs.getString("value"));
			value.setImageId(rs.getInt("image_id"));
			value.setImageUrl(rs.getString("image_url"));
			value.setFieldId(rs.getInt("field_id"));
			value.setRowNumber(rs.getInt("row_number"));
			value.setColumnNumber(rs.getInt("column_number"));
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
		
		return value;
	}
	
	/**
	 * Gets all the values for an image
	 * @author				kevinjreece
	 * @param	image_id	the id of the image whose values are requested
	 * @return				the values from the image
	 * */
	public List<Value> getAllValues(int image_id) throws DatabaseException {
		_logger.entering("server.database.ValuesDAO", "getAllValues");
		
		ArrayList<Value> result = new ArrayList<Value>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from `values` "
							+ "where image_id=?";
			stmt  = _db.getConnection().prepareStatement(query);
			stmt.setInt(1, image_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int value_id = rs.getInt("value_id");
				String image_url = rs.getString("image_url");
				int field_id = rs.getInt("field_id");
				String value= rs.getString("value");
				int row_num = rs.getInt("row_number");
				int col_num = rs.getInt("column_number");
				
				result.add(new Value(value_id, value, image_id, image_url, field_id, row_num, col_num));
			}
		}
		catch (SQLException err) {
			throw new DatabaseException("Unable to get all values", err);
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		
		return result;
	}
	
	/**
	 * Gets all the values in the database
	 * */
	public List<Value> getAllValues() throws DatabaseException {
		_logger.entering("server.database.ValuesDAO", "getAllValues");
		
		ArrayList<Value> result = new ArrayList<Value>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from `values`";
			stmt  = _db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int value_id = rs.getInt("value_id");
				int image_id = rs.getInt("image_id");
				String image_url = rs.getString("image_url");
				int field_id = rs.getInt("field_id");
				String value= rs.getString("value");
				int row_num = rs.getInt("row_number");
				int col_num = rs.getInt("column_number");
				
				result.add(new Value(value_id, value, image_id, image_url, field_id, row_num, col_num));
			}
		}
		catch (SQLException err) {
			throw new DatabaseException("Unable to get all values", err);
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		
		return result;
	}
	
	/**
	 * Searches the values table for a string
	 * @author			kevinjreece
	 * @return			the values that contain the string
	 * */
	public List<Value> search(List<Integer> field_ids, List<String> values) throws DatabaseException {
		_logger.entering("server.database.ValuesDAO", "getAllValues");
		
		ArrayList<Value> result = new ArrayList<Value>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		StringBuilder fields_str = new StringBuilder();
		fields_str.append("(");
		StringBuilder values_str = new StringBuilder();
		values_str.append("(");
		
		for (int i = 0; i < field_ids.size(); i++) {
			if (i > 0)
				fields_str.append("or ");
			fields_str.append("field_id=" + field_ids.get(i) + " ");
		}
		
		for (int i = 0; i < values.size(); i++) {
			if (i > 0)
				values_str.append("or ");
			values_str.append("value='" + values.get(i) + "' ");
		}
		
		fields_str.append(") ");
		values_str.append(") ");
		
		try {
			String query = "select * from `values` "
					+ "where " + fields_str + "and " + values_str + "collate nocase";
			stmt  = _db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int value_id = rs.getInt("value_id");
				int image_id = rs.getInt("image_id");
				String image_url = rs.getString("image_url");
				int field_id = rs.getInt("field_id");
				String value= rs.getString("value");
				int row_num = rs.getInt("row_number");
				int col_num = rs.getInt("column_number");
				
				result.add(new Value(value_id, value, image_id, image_url, field_id, row_num, col_num));
			}
		}
		catch (SQLException err) {
			throw new DatabaseException("Unable to get all values", err);
		}
		finally {
			IndexerDatabase.safeClose(stmt);
			IndexerDatabase.safeClose(rs);
		}
		
		return result;
	}

	/**
	 * Creates the values table in the database
	 * @author kevinjreece 
	 * @throws DatabaseException 
	 */
	public void createValuesTable() throws DatabaseException {
		_logger.entering("server.database.ValuesDAO", "createValuesTable");
		
		Statement stmt = null;
		
		try {
			String sql = "drop table if exists `values`";
			stmt = _db.getConnection().createStatement();
			stmt.executeUpdate(sql);
			
			sql = "create table `values` ("
					  + "value_id integer primary key autoincrement not null,"
					  + "field_id integer not null,"
					  + "image_id integer not null,"
					  + "image_url text not null,"
					  + "value text not null collate nocase,"
					  + "row_number integer not null,"
					  + "column_number integer not null)";
			stmt = _db.getConnection().createStatement();
			stmt.executeUpdate(sql);
		}
		catch (SQLException err) {
			throw new DatabaseException("Could not create values table", err);
		}
		
		return;
	}
}
