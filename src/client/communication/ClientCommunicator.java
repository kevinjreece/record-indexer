package client.communication;

import shared.communication.*;

/**
 * Allows the client to communicate with the server
 * @author kevinjreece
 */
public class ClientCommunicator {
	
	/**
	 * Determines if a username/password combination is valid
	 * @author			kevinjreece
	 * @param	params	the parameters needed to validate the user (username and password)
	 * @return			true if the username/password combination is in the database, false otherwise
	 * */
	public ValidateUser_Result validateUser(ValidateUser_Params params) {
		return null;
	}
	
	/**
	 * Gets all the projects in the database
	 * @author	kevinjreece
	 * @return	all projects in the database
	 * */
	public GetProjects_Result getAllProjects() {
		return null;
	}
	
	/**
	 * Gets a sample image for a project
	 * @author			kevinjreece
	 * @param	params	the parameters needed to download a sample image
	 * @return			a sample image for the given project
	 * */
	public GetSampleImage_Result getSampleImage(GetSampleImage_Params params) {
		return null;
	}
	
	/**
	 * Gets an incomplete batch for the given project
	 * @author			kevinjreece
	 * @param	params	the parameters needed to download a batch from a specific project
	 * @return			an incomplete batch from the given project
	 * */
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params) {
		return null;
	}

	/**
	 * Submits a completed batch to the database
	 * @author			kevinjreece
	 * @param	params	the parameters needed to insert new values into the database
	 * */
	public void submitBatch(SubmitBatch_Params params) {
		return;
	}
	
	/**
	 * Gets the fields for a given project
	 * @author			kevinjreece
	 * @param	params	the parameters needed to get fields for a project
	 * @return			the fields for the given project
	 * */
	public GetFields_Result getFields(GetFields_Params params) {
		return null;
	}

	/**
	 * Searches the database for the given string
	 * @author			kevinjreece
	 * @param	params	the parameters needed to search the database
	 * @return			the images containing the search_string
	 * */
	public Search_Result search(Search_Params params) {
		return null;
	}
	
	/**
	 * Downloads a file from the server
	 * @author			kevinjreece
	 * @param	params	the parameters needed to download the file
	 * @return			the file
	 * */
	public DownloadFile_Result downloadFile(DownloadFile_Params params) {
		return null;
	}
}
