package server.database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.Field;

/**
 * @author kevinjreece
 *
 */
public class FieldsDAOTest {

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
	private FieldsDAO _dbFields;
	
	@Before
	public void setUp() throws Exception {
		IndexerDatabase.emptyDatabase();
		_db = new IndexerDatabase();
		_db.startTransaction();
		_dbFields = _db.getFieldsDAO();
	}
	
	@After
	public void tearDown() throws Exception {
		_db.endTransaction(false);
		_db = null;
		_dbFields = null;
	}
	
	@Test
	public void testGetAll() throws Exception {
		// Make sure the fields table is empty
		List<Field> all = _dbFields.getAllFields();
		assertEquals(0, all.size());
	}
	
	@Test
	public void testAddField() throws Exception {
		// Make sure the fields table is empty
		List<Field> all = _dbFields.getAllFields();
		assertEquals(0, all.size());
		
		// Create needed variables
		int project_id_1 = 1;
		int project_id_2 = 2;
		
		// Create new Fields
		Field field_1 = new Field(-1, project_id_1, "Field 1", 0, 0, "field_1_help.html", "field_1_known.html", 0);
		Field field_2 = new Field(-1, project_id_1, "Field 2", 0, 0, "field_2_help.html", "field_2_known.html", 1);
		Field field_3 = new Field(-1, project_id_1, "Field 3", 0, 0, "field_3_help.html", "field_3_known.html", 2);
		Field field_4 = new Field(-1, project_id_2, "Field 4", 0, 0, "field_4_help.html", "field_4_known.html", 0);
		Field field_5 = new Field(-1, project_id_2, "Field 5", 0, 0, "field_5_help.html", "field_5_known.html", 1);
		
		// Add new Fields to database
		int field_id_1 = _dbFields.addField(field_1);
		field_1.setFieldId(field_id_1);
		int field_id_2 = _dbFields.addField(field_2);
		field_2.setFieldId(field_id_2);
		int field_id_3 = _dbFields.addField(field_3);
		field_3.setFieldId(field_id_3);
		int field_id_4 = _dbFields.addField(field_4);
		field_4.setFieldId(field_id_4);
		int field_id_5 = _dbFields.addField(field_5);
		field_5.setFieldId(field_id_5);
		
		// Make sure new fields are in the database
		all = _dbFields.getAllFields();
		assertEquals(5, all.size());
		assertTrue(safeEquals(field_1, _dbFields.getField(field_id_1)));
		assertTrue(safeEquals(field_2, _dbFields.getField(field_id_2)));
		assertTrue(safeEquals(field_3, _dbFields.getField(field_id_3)));
		assertTrue(safeEquals(field_4, _dbFields.getField(field_id_4)));
		assertTrue(safeEquals(field_5, _dbFields.getField(field_id_5)));
		
		// Make sure project 1 has the right fields
		all = _dbFields.getAllFields(project_id_1);
		assertEquals(3, all.size());
		boolean field_1_found = false;
		boolean field_2_found = false;
		boolean field_3_found = false;
		for (Field each : all) {
			if (!field_1_found)
				field_1_found = safeEquals(field_1, each);
			if (!field_2_found)
				field_2_found = safeEquals(field_2, each);
			if (!field_3_found)
				field_3_found = safeEquals(field_3, each);
		}
		assertTrue(field_1_found && field_2_found && field_3_found);
		
		// Make sure project 2 has the right fields
		boolean field_4_found = false;
		boolean field_5_found = false;
		all = _dbFields.getAllFields(project_id_2);
		assertEquals(2, all.size());
		for (Field each : all) {
			if (!field_4_found)
				field_4_found = safeEquals(field_4, each);
			if (!field_5_found)
				field_5_found = safeEquals(field_5, each);
		}
		assertTrue(field_4_found && field_5_found);
		
		// Test getting field by project and column
		assertEquals(field_id_1, _dbFields.getFieldId(project_id_1, 0));
		assertEquals(field_id_3, _dbFields.getFieldId(project_id_1, 2));
		assertEquals(field_id_5, _dbFields.getFieldId(project_id_2, 1));
		
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














