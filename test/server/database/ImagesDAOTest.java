package server.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.Image;

/**
 * @author kevinjreece
 */
public class ImagesDAOTest {

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
	private ImagesDAO _dbImages;
	
	@Before
	public void setUp() throws Exception {
		IndexerDatabase.emptyDatabase();
		_db = new IndexerDatabase();
		_db.startTransaction();
		_dbImages = _db.getImagesDAO();
	}
	
	@After
	public void tearDown() throws Exception {
		_db.endTransaction(false);
		_db = null;
		_dbImages = null;
	}
	
	@Test
	public void testGetAll() throws Exception {
		// Make sure the images table is empty
		List<Image> all = _dbImages.getAllImages();
		assertEquals(0, all.size());
	}
	
	@Test
	public void testAddImage() throws Exception {
		// Make sure the images table is empty
		List<Image> all = _dbImages.getAllImages();
		assertEquals(0, all.size());
		
		// Make needed variables
		int user_id_1 = 1;
		Image test_image = null;

		// Create new images
		int project_id_1 = 1;
		int project_id_2 = 2;
		Image image_1 = new Image(-1, project_id_1, "image_1.img", Image.INCOMPLETE, -1);
		Image image_2 = new Image(-1, project_id_1, "image_2.img", Image.INCOMPLETE, -1);
		Image image_3 = new Image(-1, project_id_2, "image_3.img", Image.INCOMPLETE, -1);
		
		
		// Add new images to database
		int image_id_1 = _dbImages.addImage(image_1);
		image_1.setImageId(image_id_1);
		int image_id_2 = _dbImages.addImage(image_2);
		image_2.setImageId(image_id_2);
		int image_id_3 = _dbImages.addImage(image_3);
		image_3.setImageId(image_id_3);
		
		// Make sure the images table has three images
		all = _dbImages.getAllImages();
		assertEquals(3, all.size());
		
		// Make sure all new images are in the images table
		assertTrue(safeEquals(image_1, _dbImages.getImage(image_id_1)));
		assertTrue(safeEquals(image_2, _dbImages.getImage(image_id_2)));
		assertTrue(safeEquals(image_3, _dbImages.getImage(image_id_3)));
		
		// Get sample images
		assertTrue(safeEquals(image_1, _dbImages.getSampleImage(project_id_1)));
		assertTrue(safeEquals(image_3, _dbImages.getSampleImage(project_id_2)));
		
		// Test checking out incomplete image
		test_image = _dbImages.getIncompleteImage(project_id_1);
		assertTrue(safeEquals(image_1, test_image));
		assertEquals(Image.INCOMPLETE, test_image.getStatus());
		_dbImages.assignImageToUser(image_id_1, user_id_1);
		_dbImages.setStatus(image_id_1, Image.ACTIVE);
		test_image = _dbImages.getImage(image_id_1);
		assertEquals(user_id_1, test_image.getCurrentUserId());
		assertEquals(Image.ACTIVE, test_image.getStatus());
		
		// Test checking in complete image
		_dbImages.unassignImage(image_id_1);
		_dbImages.setStatus(image_id_1, Image.COMPLETE);
		test_image = _dbImages.getImage(image_id_1);
		assertEquals(-1, test_image.getCurrentUserId());
		assertEquals(Image.COMPLETE, test_image.getStatus());
		
		// Check out next incomplete image
		test_image = _dbImages.getIncompleteImage(project_id_1);
		assertTrue(safeEquals(image_2, test_image));
		assertEquals(Image.INCOMPLETE, test_image.getStatus());
		
		// Make sure sample images are still the same
		assertTrue(safeEquals(image_1, _dbImages.getSampleImage(project_id_1)));
		assertTrue(safeEquals(image_3, _dbImages.getSampleImage(project_id_2)));
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











