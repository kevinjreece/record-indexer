package shared.model;

import org.w3c.dom.Element;

import server.DataImporter;

/**
 * Models record indexer projects
 * @author kevinjreece
 */
public class Project {
	private int _project_id = -1;
	private String _title = "";
	private int _records_per_image = -1;
	private int _first_y_coord = -1;
	private int _record_height = -1;
	
	/**
	 * Creates a Project object from a Project element
	 * @author kevinjreece
	 */
	public Project(Element project_element) {
		_title = DataImporter.getValue((Element) project_element.getElementsByTagName("title").item(0));
		_records_per_image = Integer.parseInt(DataImporter.getValue((Element) project_element.getElementsByTagName("recordsperimage").item(0)));
		_first_y_coord = Integer.parseInt(DataImporter.getValue((Element) project_element.getElementsByTagName("firstycoord").item(0)));
		_record_height = Integer.parseInt(DataImporter.getValue((Element) project_element.getElementsByTagName("recordheight").item(0)));
	}
	
	/**
	 * Creates a Project object from individual parameters
	 * @author kevinjreece
	 */
	public Project(int project_id, String title, int records_per_image, int first_y_coord,
			int record_height) {
		assert(title != "" && records_per_image > 0 && first_y_coord > 0 && record_height > 0);
		setProjectId(project_id);
		setTitle(title);
		setRecordsPerImage(records_per_image);
		setFirstYCoord(first_y_coord);
		setRecordHeight(record_height);
	}
	
	public Project() {
		setProjectId(-1);
		setTitle("New Project");
		setRecordsPerImage(-1);
		setFirstYCoord(-1);
		setRecordHeight(-1);
	}
	
	/**
	 * Converts a Project object into a String
	 * @author kevinjreece
	 * */
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		
		output.append("Project ID: " + _project_id + "\n");
		output.append("Title: " + _title + "\n");
		output.append("Records Per Image: " + _records_per_image + "\n");
		output.append("First Y Coordinate: " + _first_y_coord + "\n");
		output.append("Record Height: " + _record_height + "\n");
		
		return output.toString();
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
		
		Project other = (Project) o;
		
		return (_project_id == other.getProjectId()
				&& _title.equals(other.getTitle())
				&& _records_per_image == other.getRecordsPerImage()
				&& _first_y_coord == other.getFirstYCoord()
				&& _record_height == other.getRecordHeight());
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
	 * @return the _title
	 */
	public String getTitle() {
		return _title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this._title = title;
	}
	
	/**
	 * @return the _records_per_image
	 */
	public int getRecordsPerImage() {
		return _records_per_image;
	}
	
	/**
	 * @param records_per_image the records_per_image to set
	 */
	public void setRecordsPerImage(int records_per_image) {
		this._records_per_image = records_per_image;
	}
	
	/**
	 * @return the _first_y_coord
	 */
	public int getFirstYCoord() {
		return _first_y_coord;
	}
	
	/**
	 * @param first_y_coord the first_y_coord to set
	 */
	public void setFirstYCoord(int first_y_coord) {
		this._first_y_coord = first_y_coord;
	}
	
	/**
	 * @return the _record_height
	 */
	public int getRecordHeight() {
		return _record_height;
	}
	
	/**
	 * @param record_height the record_height to set
	 */
	public void setRecordHeight(int record_height) {
		this._record_height = record_height;
	}
}
