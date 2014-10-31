package shared.model;

/**
 * Java class to model record indexer fields
 * @author kevinjreece
 */
public class Field {
	private int _field_id = -1;
	private int _project_id = -1;
	private String _title = "";
	private int _x_coord = -1;
	private int _width = -1;
	private String _help_html = "";
	private String _known_data = "";
	private int _column_number = -1;
	
	/**
	 * Creates a Field object from individual parameters
	 * @author kevinjreece
	 */
	public Field(int field_id, int project_id, String title, int x_coord, int width,
			String help_html, String known_data, int column_number) {
		assert(project_id > 0 && title != "" && x_coord > 0 && width > 0 && column_number >= 0);
		setFieldId(field_id);
		setProjectId(project_id);
		setTitle(title);
		setXCoord(x_coord);
		setWidth(width);
		setHelpHtml(help_html);
		setKnownData(known_data);
		setColumnNumber(column_number);
	}
	
	/**
	 * Creates a default Field object
	 * @author kevinjreece
	 * */
	public Field() {
		setFieldId(-1);
		setProjectId(-1);
		setTitle("New Field");
		setXCoord(-1);
		setWidth(-1);
		setHelpHtml(null);
		setKnownData(null);
		setColumnNumber(-1);
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
		
		Field other = (Field) o;
		
		return (_field_id == other.getFieldId()
				&& _project_id == other.getProjectId()
				&& _title.equals(other.getTitle())
				&& _x_coord == other.getXCoord()
				&& _width == other.getWidth()
				&& _help_html.equals(other.getHelpHtml())
				&& _known_data.equals(other.getKnownData())
				&& _column_number == other.getColumnNumber());
	}

	/**
	 * @return the _field_id
	 */
	public int getFieldId() {
		return _field_id;
	}
	
	/**
	 * @param field_id the field_id to set
	 */
	public void setFieldId(int field_id) {
		this._field_id = field_id;
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
	 * @return the _x_coord
	 */
	public int getXCoord() {
		return _x_coord;
	}
	
	/**
	 * @param x_coord the x_coord to set
	 */
	public void setXCoord(int x_coord) {
		this._x_coord = x_coord;
	}
	
	/**
	 * @return the _width
	 */
	public int getWidth() {
		return _width;
	}
	
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this._width = width;
	}
	
	/**
	 * @return the _help_html
	 */
	public String getHelpHtml() {
		return _help_html;
	}
	
	/**
	 * @param help_html the help_html to set
	 */
	public void setHelpHtml(String help_html) {
		this._help_html = help_html;
	}
	
	/**
	 * @return the _known_data
	 */
	public String getKnownData() {
		return _known_data;
	}
	
	/**
	 * @param known_data the known_data to set
	 */
	public void setKnownData(String known_data) {
		this._known_data = known_data;
	}
	
	/**
	 * @return the _column_number
	 */
	public int getColumnNumber() {
		return _column_number;
	}
	
	/**
	 * @param column_number the column_number to set
	 */
	public void setColumnNumber(int column_number) {
		this._column_number = column_number;
	}
}
