package shared.model;

/**
 * Java class to model record indexer images
 * @author kevinjreece
 */
public class Image {
	public static int INCOMPLETE = 0;
	public static int ACTIVE = 1;
	public static int COMPLETE = 2;
	
	private int _image_id = -1;
	private int _project_id = -1;
	private String _image_url = null;
	private int _status = 0;
	private int _current_user_id = -1;
	
	/**
	 * Creates an Image object from individual parameters
	 * @author kevinjreece
	 */
	public Image(int image_id, int project_id, String file_url, int status, int current_user_id) {
		assert(project_id > 0 && file_url != "");
		setImageId(image_id);
		setProjectId(project_id);
		setImageUrl(file_url);
		setStatus(status);
		setCurrentUserId(current_user_id);
	}
	
	/**
	 * Creates a default Image object
	 * @author kevinjreece
	 * */
	public Image() {
		setImageId(-1);
		setProjectId(-1);
		setImageUrl(null);
		setStatus(INCOMPLETE);
		setCurrentUserId(-1);
	}

	/***/
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o.getClass() != this.getClass())
			return false;
		if (o == this)
			return true;
		
		Image other = (Image) o;
		
		return (_image_id == other.getImageId()
				&& _project_id == other.getProjectId()
				&& _image_url.equals(other.getImageUrl())
//				&& _status == other.getStatus()
				&& _current_user_id == other.getCurrentUserId());
	}
	
	/**
	 * @return the _image_id
	 */
	public int getImageId() {
		return _image_id;
	}
	
	/**
	 * @param image_id the image_id to set
	 */
	public void setImageId(int image_id) {
		this._image_id = image_id;
	}
	
	/**
	 * @return the _project_id
	 */
	public int getProjectId() {
		return _project_id;
	}
	
	/**
	 * @param project_id the project_id to set
	 */
	public void setProjectId(int project_id) {
		this._project_id = project_id;
	}
	
	/**
	 * @return the _image_url
	 */
	public String getImageUrl() {
		return _image_url;
	}
	
	/**
	 * @param image_url the image_url to set
	 */
	public void setImageUrl(String file_url) {
		this._image_url = file_url;
	}

	/**
	 * @return the _status
	 */
	public int getStatus() {
		return _status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this._status = status;
	}

	/**
	 * @return the _current_user_id
	 */
	public int getCurrentUserId() {
		return _current_user_id;
	}

	/**
	 * @param current_user_id the current_user_id to set
	 */
	public void setCurrentUserId(int current_user_id) {
		this._current_user_id = current_user_id;
	}
}
