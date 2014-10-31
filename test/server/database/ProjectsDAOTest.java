package server.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.Project;

/**
 * @author kevinjreece
 */
public class ProjectsDAOTest {
	
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
	private ProjectsDAO _dbProjects;
	
	@Before
	public void setUp() throws Exception {
		IndexerDatabase.emptyDatabase();
		_db = new IndexerDatabase();
		_db.startTransaction();
		_dbProjects = _db.getProjectsDAO();
	}
	
	@After
	public void tearDown() throws Exception {
		_db.endTransaction(false);
		_db = null;
		_dbProjects = null;
	}

	@Test
	public void testGetAll() throws Exception {
		// Make sure the projects table is empty
		List<Project> all = _dbProjects.getAllProjects();
		assertEquals(0, all.size());
	}
	
	@Test
	public void testAddProject() throws Exception {
		// Make sure the projects table is empty
		List<Project> all = _dbProjects.getAllProjects();
		assertEquals(0, all.size());
		
		// Create new Projects
		Project project_1 = new Project(-1, "Project One", 0, 1, 2);
		Project project_2 = new Project(-1, "Project Two", 0, 1, 2);
		
		// Add new Projects to database
		int project_id_1 = _dbProjects.addProject(project_1);
		project_1.setProjectId(project_id_1);
		int project_id_2 = _dbProjects.addProject(project_2);
		project_2.setProjectId(project_id_2);
		
		// Make sure the projects table has two users
		all = _dbProjects.getAllProjects();
		assertEquals(2, all.size());
		
		// Try to find the two new projects by project_id
		assertTrue(safeEquals(project_1, _dbProjects.getProject(project_1.getProjectId())));
		assertTrue(safeEquals(project_2, _dbProjects.getProject(project_2.getProjectId())));
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











