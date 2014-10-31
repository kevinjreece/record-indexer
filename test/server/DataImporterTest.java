package server;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import server.database.IndexerDatabase;
import shared.model.Field;
import shared.model.Image;
import shared.model.Project;
import shared.model.User;
import shared.model.Value;

/**
 * @author kevinjreece
 *
 */
public class DataImporterTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		IndexerDatabase.initialize();
		return;
	}

	@Test
	public void test() throws Exception {
		String[] args = {
				"src/server/Records.xml"
		};
		DataImporter.main(args);
		
		IndexerDatabase db = new IndexerDatabase();
		db.startTransaction();
		List<User> users = null;
		List<Project> projects = null;
		List<Image> images = null;
		List<Field> fields = null;
		List<Value> values = null;
		
		
		users = db.getUsersDAO().getAllUsers();
		projects = db.getProjectsDAO().getAllProjects();
		fields = db.getFieldsDAO().getAllFields();
		images = db.getImagesDAO().getAllImages();
		values = db.getValuesDAO().getAllValues();

		assertEquals(3, users.size());
		assertEquals(3, projects.size());
		assertEquals(13, fields.size());
		assertEquals(60, images.size());
		assertEquals(560, values.size());
		
		db.endTransaction(false);
		IndexerDatabase.emptyDatabase();
	}

}
