package server.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

public class IndexerDatabase {
	private static final String DATABASE_DIRECTORY = "database";
	private static final String DATABASE_FILE = "recordindexer.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY +
												File.separator + DATABASE_FILE;
	
	private static Logger _logger;
	
	static {
		_logger = Logger.getLogger("indexermanager");
	}

	public static void initialize() throws DatabaseException {
		_logger.entering("server.database.IndexerDatabase", "initialize");
//		System.out.println("IndexerDatabase.initialize");
		
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch (ClassNotFoundException err) {
			DatabaseException serverEx = new DatabaseException("Could not load database driver", err);
			_logger.throwing("server.database.IndexerDatabase", "initialize", serverEx);
			throw serverEx;
		}
		
		_logger.exiting("server.database.IndexerDatabase", "initialize");
	}

	private UsersDAO _usersDAO;
	private ProjectsDAO _projectsDAO;
	private ImagesDAO _imagesDAO;
	private FieldsDAO _fieldsDAO;
	private ValuesDAO _valuesDAO;
	private Connection _connection;
	
	public IndexerDatabase() {
		_usersDAO = new UsersDAO(this);
		_projectsDAO = new ProjectsDAO(this);
		_imagesDAO = new ImagesDAO(this);
		_fieldsDAO = new FieldsDAO(this);
		_valuesDAO = new ValuesDAO(this);
		_connection = null;
	}
	
	public UsersDAO getUsersDAO() {
		return _usersDAO;
	}
	
	public ProjectsDAO getProjectsDAO() {
		return _projectsDAO;
	}
	
	public ImagesDAO getImagesDAO() {
		return _imagesDAO;
	}
	
	public FieldsDAO getFieldsDAO() {
		return _fieldsDAO;
	}
	
	public ValuesDAO getValuesDAO() {
		return _valuesDAO;
	}
	
	public Connection getConnection() {
		return _connection;
	}

	public void startTransaction() throws DatabaseException {
		_logger.entering("server.database.IndexerDatabase", "startTransaction");
//		System.out.println("Entering IndexerDatabase.startTransaction");
		
		try {
			assert (_connection == null);			
			_connection = DriverManager.getConnection(DATABASE_URL);
			_connection.setAutoCommit(false);
		}
		catch (SQLException err) {
			throw new DatabaseException("Could not connect to database. Make sure " + 
				DATABASE_FILE + " is available in ./" + DATABASE_DIRECTORY, err);
		}
		
//		System.out.println("Exiting IndexerDatabase.startTransaction");
		_logger.exiting("server.database.IndexerDatabase", "startTransaction");
	}
	
	public void endTransaction(boolean commit) {
		_logger.entering("server.database.Database", "endTransaction");
		
		if (_connection != null) {		
			try {
				if (commit) {
					_connection.commit();
				}
				else {
					_connection.rollback();
				}
			}
			catch (SQLException e) {
				System.out.println("Could not end transaction");
				e.printStackTrace();
			}
			finally {
				safeClose(_connection);
				_connection = null;
			}
		}
		
		_logger.exiting("server.database.Database", "endTransaction");
	}
		
	public static void safeClose(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException err) {
				err.printStackTrace();
			}
		}
	}
	

	public static void safeClose(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(PreparedStatement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void emptyDatabase() throws IOException {
		File active_db = new File("database/recordindexer.sqlite");
		File empty_db = new File("database/blank_database/recordindexer.sqlite");
		FileUtils.copyFile(empty_db, active_db);
		return;
	}
	
	public static void copyDatabase() throws IOException {
		File active_db = new File("database/recordindexer.sqlite");
		File empty_db = new File("database/blank_database/recordindexer.sqlite");
		FileUtils.copyFile(active_db, empty_db);
		return;
	}
}
















