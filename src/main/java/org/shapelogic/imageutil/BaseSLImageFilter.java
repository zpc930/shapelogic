package org.shapelogic.imageutil;

/** Class to subclass when writing filters.<br />
 * 
 * @author Sami Badawi
 *
 */
public abstract class BaseSLImageFilter implements ImageOperation {
	
	protected SLImage _slImage;
	protected GuiWrapper _guiWrapper;
	protected String _arg;
	protected int _setupReturnValue;
	
	public BaseSLImageFilter() {
	}
	
	public BaseSLImageFilter(int setupReturnValue) {
		_setupReturnValue = setupReturnValue;
	}
	
	@Override
	public SLImage getSLImage() {
		return _slImage;
	}

	@Override
	public int setup(String arg, SLImage imp) {
		_arg = arg;
		_slImage = imp;
		return _setupReturnValue;
	}

	@Override
	public void showMessage(String title, String text) {
		if (_guiWrapper!=null)
			_guiWrapper.showMessage(title, text);
		else 
			System.out.println(title + "\n" + text + "\n");
	}
	
	@Override
	public void setGuiWrapper(GuiWrapper guiWrapper) {
		_guiWrapper = guiWrapper;
	}

	@Override
	public GuiWrapper getGuiWrapper() {
		return _guiWrapper;
	}

	/** Should be based on _setupReturnValue and type of input image.<br /> */
	@Override
	public boolean isImageValid() {
		return true; //XXX Implement logic later.
	}

	@Override
	public int getSetupReturnValue() {
		return _setupReturnValue;
	}

	@Override
	public void setSetupReturnValue(int setupReturnValue) {
		_setupReturnValue = setupReturnValue;
	}
}
