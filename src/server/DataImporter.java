package server;
import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import server.database.DatabaseException;
import server.database.IndexerDatabase;

/**
 * Imports data from an XML file to database
 * @author kevinjreece
 */
public class DataImporter {
	
	public static void main(String[] args) {
		File xmlFile = new File(args[0]);
		
		try {
			IndexerDatabase.initialize();
			createAllTables();
			IndexerDatabase.copyDatabase();
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			Element root = doc.getDocumentElement();
			IndexerData.loadIndexerData(root);
		}
		catch (Exception err) {
			err.printStackTrace();
		}
		
		return;
	}
	
	/**
	 * Gets the children elements of a Node
	 * @author kevinjreece
	 * */
	public static ArrayList<Element> getChildElements(Node node) {
		ArrayList<Element> result = new ArrayList<Element>();
		NodeList children = node.getChildNodes();
		
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				result.add((Element)child);
			}
		}
		
		return result;
	}
	
	public static String getValue(Element elem) {
		String result = "";
		Node child = elem.getFirstChild();
		
		result = child.getNodeValue();
		
		return result;
	}
	
	/**
	 * Checks if an Element has a certain attribute
	 * @author kevinjreece
	 * */
	public static boolean hasAttribute(Element elem, String attr) {
		return elem.getElementsByTagName(attr).getLength() > 0;
	}
	
	/**
	 * Creates the necessary tables in the database
	 * @author kevinjreece
	 * */
	public static void createAllTables() throws DatabaseException{
//		System.out.println("Entering createAllTables");
		IndexerDatabase db = new IndexerDatabase();
		
		try {
			db.startTransaction();
			db.getUsersDAO().createUsersTable();
			db.getProjectsDAO().createProjectsTable();
			db.getFieldsDAO().createFieldsTable();
			db.getImagesDAO().createImagesTable();
			db.getValuesDAO().createValuesTable();
			db.endTransaction(true);
		}
		catch (DatabaseException err) {
			throw err;
		}
	}
}








