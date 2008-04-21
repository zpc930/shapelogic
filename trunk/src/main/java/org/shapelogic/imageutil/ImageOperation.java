package org.shapelogic.imageutil;

/** Modifies a image. An abstraction of ImageJ's PlugInFilter.<br />
 * 
 * Used for a thin wrapper around PlugInFilter or a class that will do the same 
 * for BufferedImages.<br />
 * 
 * @author Sami Badawi
 *
 */
public interface ImageOperation extends Runnable {
	
	/** How to get the SLImage in run(). */ 
	SLImage getSLImage();

	/** Equivalent of setup in PlugInFilter. */ 
	int setup(String arg, SLImage imp);

	/** This wrapper only implement displaying a simple text string. */ 
	void showMessage(String title, String text);
	
	String showAbout();

	/** If you want the object to  write to a GUI, set a GUIWrapper. */
	void setGuiWrapper(GuiWrapper guiWrapper);

	GuiWrapper getGuiWrapper();
	
	boolean isImageValid();

	int getSetupReturnValue();

	void setSetupReturnValue(int setupReturnValue);
}
