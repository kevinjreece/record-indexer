package shared.communication;

import java.util.List;

import shared.model.Value;

/**
 * Contains the return value of a database search
 * @author kevinjreece
 */
public class Search_Result {
	private List<Value> _found_values;

	/**
	 * @return the _found_images
	 */
	public List<Value> getFoundImages() {
		return _found_values;
	}

	/**
	 * @param found_images the found_images to set
	 */
	public void setFoundImages(List<Value> found_values) {
		this._found_values = found_values;
	}
}
