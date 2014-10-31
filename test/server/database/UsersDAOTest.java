package server.database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.User;

/**
 * Tests the UsersDAO class
 * @author kevinjreece
 */
public class UsersDAOTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		IndexerDatabase.initialize();
		return;
	}
	
	@AfterClass
	public static void tearDownBeforeClass() throws Exception {
		return;
	}
	
	private IndexerDatabase _db;
	private UsersDAO _dbUsers;
	
	@Before
	public void setUp() throws Exception {
		IndexerDatabase.emptyDatabase();
		_db = new IndexerDatabase();
		_db.startTransaction();
		_dbUsers = _db.getUsersDAO();
	}
	
	@After
	public void tearDown() throws Exception {
		_db.endTransaction(false);
		_db = null;
		_dbUsers = null;
	}
	
	@Test
	public void testGetAll() throws Exception {
		// Make sure the users table is empty
		List<User> all = _dbUsers.getAllUsers();
		assertEquals(0, all.size());
	}
	
	@Test
	public void testAddUser() throws Exception {
		// Make sure the users table is empty
		List<User> all = _dbUsers.getAllUsers();
		assertEquals(0, all.size());
		
		// Create new Users
		User user_1 = new User(1, "user_1", "pass_1", "User", "One", "user_1@test.com", 0, 0);
		User user_2 = new User(2, "user_2", "pass_2", "User", "Two", "user_2@test.com", 0, 0);
		
		// Add new Users to database
		int user_id_1 = _dbUsers.addUser(user_1);
		user_1.setUserId(user_id_1);
		int user_id_2 = _dbUsers.addUser(user_2);
		user_2.setUserId(user_id_2);
		
		// Make sure the users table has two users
		all = _dbUsers.getAllUsers();
		assertEquals(2, all.size());
		
		// Try to find the two new users by username
		assertTrue(safeEquals(user_1, _dbUsers.getUser(user_1.getUsername())));
		assertTrue(safeEquals(user_2, _dbUsers.getUser(user_2.getUsername())));
		
		// Try to find the two new users by user id
		assertTrue(safeEquals(user_1, _dbUsers.getUser(user_1.getUserId())));
		assertTrue(safeEquals(user_2, _dbUsers.getUser(user_2.getUserId())));
	}
	
	@Test
	public void testUpdateUsers() throws Exception {
		// Make sure the users table is empty
		List<User> all = _dbUsers.getAllUsers();
		assertEquals(0, all.size());
		
		// Create new Users
		User user_1 = new User(1, "user_1", "pass_1", "User", "One", "user_1@test.com", 0, 0);
		User user_2 = new User(2, "user_2", "pass_2", "User", "Two", "user_2@test.com", 0, 0);
		
		// Add new Users to database
		int user_id_1 = 0;
		int user_id_2 = 0;	
		user_id_1 = _dbUsers.addUser(user_1);
		user_1.setUserId(user_id_1);
		user_id_2 = _dbUsers.addUser(user_2);
		user_2.setUserId(user_id_2);
		
		// Make sure the users table has two users
		all = _dbUsers.getAllUsers();
		assertEquals(2, all.size());
		
		// Add current image id to User 1
		_dbUsers.addCurrentImageId(user_id_1, 10);
		assertEquals(10, _dbUsers.getUser(user_id_1).getCurrentImageId());
		_dbUsers.addCurrentImageId(user_id_1, -1);
		assertEquals(-1, _dbUsers.getUser(user_id_1).getCurrentImageId());
		
		// Increment indexed records for User 2
		int prev_indexed = user_2.getIndexedRecords();
		_dbUsers.incrementIndexedRecords(user_id_2);
		
		// Make sure indexed records was updated
		assertEquals(prev_indexed + 1, _dbUsers.getUser(user_id_2).getIndexedRecords());
		
		return;
	}
	
	private boolean safeEquals(Object a, Object b) {
		if (a == null || b == null) {
			return (a == null && b == null);
		}
		else {
			return a.equals(b);
		}
	}
}
