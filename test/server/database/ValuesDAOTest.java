package server.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.Value;

/**
 * @author kevinjreece
 *
 */
public class ValuesDAOTest {
	
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
	private ValuesDAO _dbValues;
	
	@Before
	public void setUp() throws Exception {
		IndexerDatabase.emptyDatabase();
		_db = new IndexerDatabase();
		_db.startTransaction();
		_dbValues = _db.getValuesDAO();
	}
	
	@After
	public void tearDown() throws Exception {
		_db.endTransaction(false);
		_db = null;
		_dbValues = null;
	}
	
	@Test
	public void testGetAll() throws Exception {
		// Make sure the users table is empty
		List<Value> all = _dbValues.getAllValues();
		assertEquals(0, all.size());
	}
	
	@Test
	public void testAddValue() throws Exception {
		// Make sure the users table is empty
		List<Value> all = _dbValues.getAllValues();
		assertEquals(0, all.size());
		
		// Create needed variables
		int image_id_1 = 1;
		int image_id_2 = 2;
		int image_id_3 = 3;
		int field_id_1 = 1;
		int field_id_2 = 2;
		int field_id_3 = 3;
		int field_id_4 = 4;
		String image_url_1 = "image_1/img";
		String image_url_2 = "image_2/img";
		String image_url_3 = "image_3/img";
		
		// Create new values
		// Image 1 - Project 1
		//   | 1 | 2 |
		// 1 | A | B |
		// 2 | C | A |
		// Image 2 - Project 1
		//   | 1 | 2 |
		// 1 | A | A |
		// 2 | B | C |
		// Image 3 - Project 2
		//   | 3 | 4 |
		// 1 | A | C |
		// 2 | A | B |
		Value value_1 = new Value(-1, "A", image_id_1, image_url_1, field_id_1, 1, 1);
		Value value_2 = new Value(-1, "B", image_id_1, image_url_1, field_id_2, 1, 2);
		Value value_3 = new Value(-1, "C", image_id_1, image_url_1, field_id_1, 2, 1);
		Value value_4 = new Value(-1, "A", image_id_1, image_url_1, field_id_2, 2, 2);
		Value value_5 = new Value(-1, "A", image_id_2, image_url_2, field_id_1, 1, 1);
		Value value_6 = new Value(-1, "A", image_id_2, image_url_2, field_id_2, 1, 2);
		Value value_7 = new Value(-1, "B", image_id_2, image_url_2, field_id_1, 2, 1);
		Value value_8 = new Value(-1, "C", image_id_2, image_url_2, field_id_2, 2, 2);
		Value value_9 = new Value(-1, "A", image_id_3, image_url_3, field_id_3, 1, 1);
		Value value_10 = new Value(-1, "C", image_id_3, image_url_3, field_id_4, 1, 2);
		Value value_11 = new Value(-1, "A", image_id_3, image_url_3, field_id_3, 2, 1);
		Value value_12 = new Value(-1, "B", image_id_3, image_url_3, field_id_4, 2, 2);
		
		// Add new values to database
		int value_id_1 = _dbValues.addValue(value_1);
		value_1.setValueId(value_id_1);
		int value_id_2 = _dbValues.addValue(value_2);
		value_2.setValueId(value_id_2);
		int value_id_3 = _dbValues.addValue(value_3);
		value_3.setValueId(value_id_3);
		int value_id_4 = _dbValues.addValue(value_4);
		value_4.setValueId(value_id_4);
		int value_id_5 = _dbValues.addValue(value_5);
		value_5.setValueId(value_id_5);
		int value_id_6 = _dbValues.addValue(value_6);
		value_6.setValueId(value_id_6);
		int value_id_7 = _dbValues.addValue(value_7);
		value_7.setValueId(value_id_7);
		int value_id_8 = _dbValues.addValue(value_8);
		value_8.setValueId(value_id_8);
		int value_id_9 = _dbValues.addValue(value_9);
		value_9.setValueId(value_id_9);
		int value_id_10 = _dbValues.addValue(value_10);
		value_10.setValueId(value_id_10);
		int value_id_11 = _dbValues.addValue(value_11);
		value_11.setValueId(value_id_11);
		int value_id_12 = _dbValues.addValue(value_12);
		value_12.setValueId(value_id_12);
		
		// Make sure the values table has 12 values
		all = _dbValues.getAllValues();
		assertEquals(12, all.size());
		
		// Make sure the values table has all new values
		assertTrue(safeEquals(value_1, _dbValues.getValue(value_id_1)));
		assertTrue(safeEquals(value_2, _dbValues.getValue(value_id_2)));
		assertTrue(safeEquals(value_3, _dbValues.getValue(value_id_3)));
		assertTrue(safeEquals(value_4, _dbValues.getValue(value_id_4)));
		assertTrue(safeEquals(value_5, _dbValues.getValue(value_id_5)));
		assertTrue(safeEquals(value_6, _dbValues.getValue(value_id_6)));
		assertTrue(safeEquals(value_7, _dbValues.getValue(value_id_7)));
		assertTrue(safeEquals(value_8, _dbValues.getValue(value_id_8)));
		assertTrue(safeEquals(value_9, _dbValues.getValue(value_id_9)));
		assertTrue(safeEquals(value_10, _dbValues.getValue(value_id_10)));
		assertTrue(safeEquals(value_11, _dbValues.getValue(value_id_11)));
		assertTrue(safeEquals(value_12, _dbValues.getValue(value_id_12)));
		
		// Test getAllValues for an image
		all = _dbValues.getAllValues(image_id_1);
		assertEquals(4, all.size());
		boolean value_1_found = false;
		boolean value_2_found = false;
		boolean value_3_found = false;
		boolean value_4_found = false;
		for (Value each : all) {
			if (!value_1_found)
				value_1_found = safeEquals(value_1, each);
			if (!value_2_found)
				value_2_found = safeEquals(value_2, each);
			if (!value_3_found)
				value_3_found = safeEquals(value_3, each);
			if (!value_4_found)
				value_4_found = safeEquals(value_4, each);
		}
		assertTrue(value_1_found && value_2_found && value_3_found && value_4_found);
		
		// Test search
		ArrayList<Integer> field_ids = new ArrayList<Integer>();
		field_ids.add(field_id_1);
		field_ids.add(field_id_3);
		field_ids.add(field_id_4);
		ArrayList<String> values = new ArrayList<String>();
		values.add("B");
		values.add("c");
		all = _dbValues.search(field_ids, values);
		value_3_found = false;
		boolean value_7_found = false;
		boolean value_10_found = false;
		boolean value_12_found = false;
		for (Value each : all) {
			if (!value_3_found)
				value_3_found = safeEquals(value_3, each);
			if (!value_7_found)
				value_7_found = safeEquals(value_7, each);
			if (!value_10_found)
				value_10_found = safeEquals(value_10, each);
			if (!value_12_found)
				value_12_found = safeEquals(value_12, each);
		}
		assertTrue(value_3_found && value_7_found 
					&& value_10_found && value_12_found);
		
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
