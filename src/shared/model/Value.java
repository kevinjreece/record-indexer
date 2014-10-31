package shared.model;

/**
 * Java class to model record indexer values
 * @author kevinjreece
 */
public class Value {
	private int _value_id = -1;
	private String _value = null;
	private int _image_id = -1;
	private String _image_url = null;
	private int _field_id = -1;
	private int _row_number = -1;
	private int _column_number = -1;
	
	/**
	 * Creates a Value object from specific parameters
	 * @author kevinjreece
	 */
	public Value(int value_id, String value, int image_id, String image_url, int field_id, int row_num,
			int col_num) {
		assert(!value.equals("") && image_id > 0 && !image_url.equals("") && field_id > 0 && row_num >= 0 && col_num >= 0);
		setValueId(value_id);
		setValue(value);
		setImageId(image_id);
		setImageUrl(image_url);
		setFieldId(field_id);
		setRowNumber(row_num);
		setColumnNumber(col_num);
	}
	
	/**
	 * Creates a default Value object
	 * @author kevinjreece
	 * */
	public Value() {
		setValueId(-1);
		setValue("New Value");
		setImageId(-1);
		setImageUrl(null);
		setFieldId(-1);
		setRowNumber(-1);
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
		
		Value other = (Value) o;
		
		return (_value_id == other.getValueId()
				&& _field_id == other.getFieldId()
				&& _image_id == other.getImageId()
				&& _image_url.equals(other.getImageUrl())
				&& _value.equals(other.getValue())
				&& _row_number == other.getRowNumber()
				&& _column_number == other.getColumnNumber());
	}

	/**
	 * @return the _value_id
	 */
	public int getValueId() {
		return _value_id;
	}
	
	/**
	 * @param value_id the value_id to set
	 */
	public void setValueId(int value_id) {
		this._value_id = value_id;
	}
	
	/**
	 * @return the _value
	 */
	public String getValue() {
		return _value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this._value = value;
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
	
	/**
	 * @return the _row_number
	 */
	public int getRowNumber() {
		return _row_number;
	}
	
	/**
	 * @param row_number the row_number to set
	 */
	public void setRowNumber(int row_number) {
		this._row_number = row_number;
	}

	/**
	 * @return the image_url
	 */
	public String getImageUrl() {
		return _image_url;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String image_url) {
		this._image_url = image_url;
	}
}
