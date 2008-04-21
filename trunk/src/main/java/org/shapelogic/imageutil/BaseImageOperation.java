package org.shapelogic.imageutil;

import static org.shapelogic.imageutil.ImageJConstants.*;

/** Class to subclass when writing filters.<br />
 * 
 * @author Sami Badawi
 *
 */
public abstract class BaseImageOperation implements ImageOperation {
	
	protected SLImage _slImage;
	protected GuiWrapper _guiWrapper;
	
	/** This is an argument with information about what should be run. */
	protected String _arg;
	protected int _setupReturnValue;
	
	public BaseImageOperation() {
		this(null);
	}
	
	public BaseImageOperation(SLImage imp) {
		this(null,imp);
	}
	
	public BaseImageOperation(String arg, SLImage imp) {
		_arg = arg;
		_slImage = imp;
	}
	
	public BaseImageOperation(int setupReturnValue) {
		_setupReturnValue = setupReturnValue;
	}
	
	@Override
	public SLImage getSLImage() {
		return _slImage;
	}

	/** If you are using the ImageOperation to fit into a ImageJ PlugInFilter
	 * use this to instantiate arg and image.<br />
	 */
	@Override
	public int setup(String arg, SLImage imp) {
		_arg = arg;
		_slImage = imp;
		if (arg.equals("about")) {
			showAbout(); 
			return DONE;
		}
		else if (imp == null) 
			showAbout(); 
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

	/** Filter capability is encoded in _setupReturnValue compare it to the type input image.<br /> 
	 * 
	 * XXX This is not fully implemented yet.
	 * */
	@Override
	public boolean isImageValid() {
		if ((_setupReturnValue & DOES_8G) != 0 && _slImage.isGray()) return true;
		if ((_setupReturnValue & DOES_RGB) != 0 && _slImage.isRgb()) return true;
		return false;
	}

	@Override
	public int getSetupReturnValue() {
		return _setupReturnValue;
	}

	@Override
	public void setSetupReturnValue(int setupReturnValue) {
		_setupReturnValue = setupReturnValue;
	}
	
	@Override
	public String showAbout() {
		String message = "";
		if ((_setupReturnValue & DOES_8G) != 0) message += "Works for 8 bit Gray\n";
		if ((_setupReturnValue & DOES_RGB) != 0) message += "Works for 32 bit Color\n";

		showMessage("About " + getClass().getSimpleName(),message);
		return message;
	}
}
