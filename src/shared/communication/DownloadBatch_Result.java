package shared.communication;

import java.util.List;

import shared.model.Field;
import shared.model.Image;
import shared.model.Project;


/**
 * Contain the return value of downloading a batch from a project
 * @author kevinjreece
 */
public class DownloadBatch_Result {
	private Project _project;
	private Image _image;
	private List<Field> _fields;

	/**
	 * @return the _image
	 */
	public Image getImage() {
		return _image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this._image = image;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return _project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this._project = project;
	}

	/**
	 * @return the fields
	 */
	public List<Field> getFields() {
		return _fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<Field> fields) {
		this._fields = fields;
	}
}
