package main;


/**
 * @author kevinjreece
 *
 */
public class IndexerUnitTestDriver {

	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"server.DataImporterTest",
				"server.database.UsersDAOTest",
				"server.database.ProjectsDAOTest",
				"server.database.FieldsDAOTest",
				"server.database.ImagesDAOTest",
				"server.database.ValuesDAOTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}

}
