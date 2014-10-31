package server;
import java.util.ArrayList;

import org.w3c.dom.*;

import server.database.*;
import shared.model.*;

/**
 * @author kevinjreece
 *
 */
public class IndexerData {
	
	/**
	 * Loads record indexer data into memory and the database
	 * @author kevinjreece
	 */
	static public void loadIndexerData(Element root) {
		ArrayList<Element> root_elements = DataImporter.getChildElements(root);
		ArrayList<Element> user_elements = DataImporter.getChildElements(root_elements.get(0));
		ArrayList<Element> project_elements = DataImporter.getChildElements(root_elements.get(1));
		
		for (Element each : user_elements) {
			userToDatabase(each);
		}
		
		for (Element each : project_elements) {
			projectToDatabase(each);
		}
	}
	
	/**
	 * Inserts User element into database
	 * @author kevinjreece
	 * */
	static public void userToDatabase(Element user_element) {
		String username = DataImporter.getValue((Element) user_element.getElementsByTagName("username").item(0));
		String password = DataImporter.getValue((Element) user_element.getElementsByTagName("password").item(0));
		String first_name = DataImporter.getValue((Element) user_element.getElementsByTagName("firstname").item(0));
		String last_name = DataImporter.getValue((Element) user_element.getElementsByTagName("lastname").item(0));
		String email = DataImporter.getValue((Element) user_element.getElementsByTagName("email").item(0));
		int indexed_records = Integer.parseInt(DataImporter.getValue((Element) user_element.getElementsByTagName("indexedrecords").item(0)));
		IndexerDatabase db = new IndexerDatabase();
		
		try {
			db.startTransaction();
			db.getUsersDAO().addUser(new User(-1, username, password, first_name, last_name, email, indexed_records, 0));
			db.endTransaction(true);
		}
		catch (DatabaseException err) {
			err.printStackTrace();
		}
		
		return;
	}
	
	/**
	 * Inserts a Project element into database
	 * @author kevinjreece
	 * */
	static public void projectToDatabase(Element project_element) {
		int project_id = -1;
		String title = DataImporter.getValue((Element) project_element.getElementsByTagName("title").item(0));
		int records_per_image = Integer.parseInt(DataImporter.getValue((Element) project_element.getElementsByTagName("recordsperimage").item(0)));
		int first_y_coord = Integer.parseInt(DataImporter.getValue((Element) project_element.getElementsByTagName("firstycoord").item(0)));
		int record_height = Integer.parseInt(DataImporter.getValue((Element) project_element.getElementsByTagName("recordheight").item(0)));
		IndexerDatabase db = new IndexerDatabase();
		
		try {
			db.startTransaction();
			project_id = db.getProjectsDAO().addProject(
							new Project(-1, title, records_per_image, first_y_coord, record_height));
			assert(project_id > 0);
			db.endTransaction(true);
		}
		catch (DatabaseException err) {
			err.printStackTrace();
		}
		// Get project fields and images
		ArrayList<Element> children = DataImporter.getChildElements(project_element);
		ArrayList<Element> fields = DataImporter.getChildElements(children.get(4));
		ArrayList<Element> images = DataImporter.getChildElements(children.get(5));
		// Add fields to database
		int col_num = 1;
		for (Element each : fields) {
			fieldToDatabase(each, project_id, col_num++);
		}
		// Add images to database
		for (Element each : images) {
			imageToDatabase(each, project_id);
		}
		
		return;
	}

	/**
	 * Inserts a Field element into database
	 * @author kevinjreece
	 */
	private static void fieldToDatabase(Element field_element, int project_id, int col_num) {
		String title = DataImporter.getValue((Element) field_element.getElementsByTagName("title").item(0));
		int x_coord = Integer.parseInt(DataImporter.getValue((Element) field_element.getElementsByTagName("xcoord").item(0)));
		int width = Integer.parseInt(DataImporter.getValue((Element) field_element.getElementsByTagName("width").item(0)));
		String help_html = DataImporter.getValue((Element) field_element.getElementsByTagName("helphtml").item(0));
		String known_data = DataImporter.hasAttribute(field_element, "knowndata") ?
								DataImporter.getValue((Element) field_element.getElementsByTagName("knowndata").item(0)) : "";
		int column_number = col_num;
		IndexerDatabase db = new IndexerDatabase();
		
		try {
			db.startTransaction();
			db.getFieldsDAO().addField(
				new Field(-1, project_id, title, x_coord, width, help_html, known_data, column_number));
			db.endTransaction(true);
		}
		catch (DatabaseException err) {
			err.printStackTrace();
		}
		
		return;
	}

	/**
	 * Inserts an Image element into database
	 * @author kevinjreece
	 * */
	private static void imageToDatabase(Element image_element, int project_id) {
		int image_id = -1;
		String image_url = DataImporter.getValue((Element)  image_element.getElementsByTagName("file").item(0));
		ArrayList<Element> records = null;
		IndexerDatabase db = new IndexerDatabase();
		
		try {
			db.startTransaction();
			image_id = db.getImagesDAO().addImage(new Image(-1, project_id, image_url, Image.INCOMPLETE, -1));
			assert(image_id > 0);
			db.endTransaction(true);
		}
		catch (DatabaseException err) {
			err.printStackTrace();
		}
		
		if (DataImporter.hasAttribute(image_element, "records")) {
			ArrayList<Element> children = DataImporter.getChildElements(image_element);
			records = DataImporter.getChildElements(children.get(1));
			
			int row_num = 1; 
			for (Element each : records) {
				recordToDatabase(each, project_id, image_id, image_url, row_num++);
			}
		}
		
		return;
	}

	/**
	 * Inserts a Record element into the database
	 * @author kevinjreece
	 */
	private static void recordToDatabase(Element record_element, int project_id,
			int image_id, String image_url, int row_num) {
		ArrayList<Element> children = DataImporter.getChildElements(record_element);
		ArrayList<Element> values = DataImporter.getChildElements(children.get(0));
		
		int col_num = 1;
		for (Element each : values) {
			valueToDatabase(each, project_id, image_id, image_url, row_num, col_num++);
		}
		
		return;
	}

	/**
	 * Inserts a value into the database
	 * @author kevinjreece
	 */
	private static void valueToDatabase(Element value_element, int project_id,
			int image_id, String image_url, int row_num, int col_num) {
		String value = DataImporter.getValue(value_element);
		IndexerDatabase db = new IndexerDatabase();
		int field_id = -1;
		
		try {
			db.startTransaction();
			field_id = db.getFieldsDAO().getFieldId(project_id, col_num);
			assert(field_id > 0);
			db.getValuesDAO().addValue(new Value(-1, value, image_id, image_url, field_id, row_num, col_num));
			db.endTransaction(true);
		}
		catch (DatabaseException err) {
			err.printStackTrace();
		}
		
		return;
	}
}














