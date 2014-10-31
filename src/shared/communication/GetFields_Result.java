package shared.communication;

import java.util.List;
import shared.model.*;

/**
 * Contains the return value of getting the fields of a project
 * @author kevinjreece
 */
public class GetFields_Result {
	List<Field> _fields;

	/**
	 * @return the _fields
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
