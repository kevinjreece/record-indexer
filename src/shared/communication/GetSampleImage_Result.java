package shared.communication;

import shared.model.Image;

/**
 * Contains the return value of getting a sample image
 * @author kevinjreece
 */
public class GetSampleImage_Result {
	private Image _sample_image;
	
	/**
	 * @return the _sample_image
	 */
	public Image getSampleImage() {
		return _sample_image;
	}
	
	/**
	 * @param sample_image the sample_image to set
	 */
	public void setSampleImage(Image sample_image) {
		this._sample_image = sample_image;
	}
}
