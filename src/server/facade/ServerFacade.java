package server.facade;

import java.util.List;

import server.ServerException;
import server.database.*;
import shared.model.*;
import shared.communication.*;

/**
 * @author kevinjreece
 */
public class ServerFacade {
	
	public static void initialize() throws ServerException {		
		try {
			IndexerDatabase.initialize();		
		}
		catch (DatabaseException err) {
			throw new ServerException(err.getMessage(), err);
		}		
	}
	
	/**
	 * Validates a username/password combination
	 * @author kevinjreece
	 * @throws ServerException 
	 * */
	public static ValidateUser_Result validateUser(ValidateUser_Params params) throws ServerException {
		IndexerDatabase db = new IndexerDatabase();
		boolean is_valid = false;
		String username = params.getUsername();
		String password = params.getPassword();
		User user = null;
		
		try {
			db.startTransaction();
			user = db.getUsersDAO().getUser(username);
			is_valid = user.getUsername().equals(password);
			db.endTransaction(true);
		}
		catch (DatabaseException err) {
			db.endTransaction(false);
			throw new ServerException(err.getMessage(), err);
		}
		
		if (!is_valid)
			user = new User();
		
		ValidateUser_Result result = new ValidateUser_Result();
		result.setIsValid(is_valid);
		result.setUser(user);
		
		return result;
	}
	
	/**
	 * Gets all the projects in the database
	 * @author kevinjreece
	 * */
	public static GetProjects_Result getProjects(GetProjects_Params params) throws ServerException {
		ValidateUser_Params validate = new ValidateUser_Params();
		validate.setUsername(params.getUsername());
		validate.setPassword(params.getPassword());
		boolean is_valid = validateUser(validate).isValid();
		
		if (!is_valid)
			throw new ServerException("Invalid user credentials");
		
		IndexerDatabase db = new IndexerDatabase();
		List<Project> projects = null;
		
		try {
			db.startTransaction();
			projects = db.getProjectsDAO().getAllProjects();
			db.endTransaction(true);
		}
		catch (DatabaseException err) {
			db.endTransaction(false);
			throw new ServerException(err.getMessage(), err);
		}
		
		GetProjects_Result result = new GetProjects_Result();
		result.setAllProjects(projects);
		
		return result;
	}
	
	/**
	 * Gets a sample image from a project
	 * @author kevinjreece
	 * */
	public static GetSampleImage_Result getSampleImage(GetSampleImage_Params params) throws ServerException {
		ValidateUser_Params validate = new ValidateUser_Params();
		validate.setUsername(params.getUsername());
		validate.setPassword(params.getPassword());
		boolean is_valid = validateUser(validate).isValid();
		
		if (!is_valid)
			throw new ServerException("Invalid user credentials");
		
		IndexerDatabase db = new IndexerDatabase();
		Image image = null;
		
		try {
			db.startTransaction();
			image = db.getImagesDAO().getSampleImage(params.getProjectId());
			db.endTransaction(true);
		}
		catch (DatabaseException err) {
			db.endTransaction(false);
			throw new ServerException(err.getMessage());
		}
		
		GetSampleImage_Result result = new GetSampleImage_Result();
		result.setSampleImage(image);
		
		return result;
	}
	
	/**
	 * Downloads an incomplete image from a project.
	 * This includes information from images, projects, and fields
	 * @author kevinjreece
	 * */
	public static DownloadBatch_Result downloadBatch(DownloadBatch_Params params) throws ServerException {
		ValidateUser_Params validate = new ValidateUser_Params();
		validate.setUsername(params.getUsername());
		validate.setPassword(params.getPassword());
		boolean is_valid = validateUser(validate).isValid();
		
		if (!is_valid)
			throw new ServerException("Invalid user credentials");
		
		IndexerDatabase db = new IndexerDatabase();
		int project_id = params.getProjectId(); 
		Image image = null;
		Project project = null;
		List<Field> fields = null;
		
		try {
			db.startTransaction();
			image = db.getImagesDAO().getIncompleteImage(project_id);
			project = db.getProjectsDAO().getProject(project_id);
			fields = db.getFieldsDAO().getAllFields(project_id);
			db.endTransaction(true);
		}
		catch (DatabaseException err) {
			db.endTransaction(false);
			throw new ServerException(err.getMessage(), err);
		}
		
		DownloadBatch_Result result = new DownloadBatch_Result();
		result.setImage(image);
		result.setProject(project);
		result.setFields(fields);
		
		return result;
	}
	
	/**
	 * Submits values from a batch into the database
	 * @author kevinjreece
	 * */
	public static void submitBatch(SubmitBatch_Params params) throws ServerException {
		ValidateUser_Params validate = new ValidateUser_Params();
		validate.setUsername(params.getUsername());
		validate.setPassword(params.getPassword());
		boolean is_valid = validateUser(validate).isValid();
		
		if (!is_valid)
			throw new ServerException("Invalid user credentials");
		
		// Values have all member variables set except for image_url and field_id
		IndexerDatabase db = new IndexerDatabase();
		int image_id = params.getImageId();
		List<Value> values = params.getNewValues();
		Image image = null;
		String image_url = null;
		int project_id = 0;
		
		try {
			db.startTransaction();
			image = db.getImagesDAO().getImage(image_id);
			image_url = image.getImageUrl();
			project_id = image.getProjectId();
			
			for (Value each : values) {
				int field_id = db.getFieldsDAO().getFieldId(project_id, each.getColumnNumber());
				each.setFieldId(field_id);
				each.setImageUrl(image_url);
				db.getValuesDAO().addValue(each);
			}
		}
		catch (DatabaseException err) {
			db.endTransaction(false);
			throw new ServerException(err.getMessage(), err);
		}
		
		return;
	}
	
	/**
	 * Gets the fields for a project
	 * @author kevinjreece
	 * */
	public static GetFields_Result getFields(GetFields_Params params) throws ServerException {
		ValidateUser_Params validate = new ValidateUser_Params();
		validate.setUsername(params.getUsername());
		validate.setPassword(params.getPassword());
		boolean is_valid = validateUser(validate).isValid();
		
		if (!is_valid)
			throw new ServerException("Invalid user credentials");
		
		IndexerDatabase db = new IndexerDatabase();
		int project_id = params.getProjectId();
		List<Field> fields = null;
		
		try {
			db.startTransaction();
			fields = project_id > 0 ? db.getFieldsDAO().getAllFields(project_id) : db.getFieldsDAO().getAllFields();
			db.endTransaction(true);
		}
		catch (DatabaseException err) {
			db.endTransaction(false);
			throw new ServerException(err.getMessage(), err);
		}
		
		GetFields_Result result = new GetFields_Result();
		result.setFields(fields);
		
		return result;
	}
	
	/**
	 * Searches certain fields for certain values
	 * @author kevinjreece
	 * */
	public static Search_Result search(Search_Params params) throws ServerException {
		ValidateUser_Params validate = new ValidateUser_Params();
		validate.setUsername(params.getUsername());
		validate.setPassword(params.getPassword());
		boolean is_valid = validateUser(validate).isValid();
		
		if (!is_valid)
			throw new ServerException("Invalid user credentials");
		
		IndexerDatabase db = new IndexerDatabase();
		List<Value> values = null;
		
		try {
			db.startTransaction();
			values = db.getValuesDAO().search(params.getFieldIds(), params.getValues());
			db.endTransaction(true);
		}
		catch (DatabaseException err) {
			db.endTransaction(false);
			throw new ServerException(err.getMessage(), err);
		}
		
		Search_Result result = new Search_Result();
		result.setFoundImages(values);
		
		return result;
	}
	
	/**
	 * Downloads a file from the server TODO
	 * @author kevinjreece
	 * */
	public static DownloadFile_Result downloadFile(DownloadFile_Params params) throws ServerException {
		ValidateUser_Params validate = new ValidateUser_Params();
		validate.setUsername(params.getUsername());
		validate.setPassword(params.getPassword());
		boolean is_valid = validateUser(validate).isValid();
		
		if (!is_valid)
			throw new ServerException("Invalid user credentials");
		
		
		
		
		return null;
	}
}











