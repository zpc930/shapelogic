package org.shapelogic.imageutil;

import ij.IJ;

/** Very thin wrapper around ImageJ showMessage().<br />
 * 
 * @author Sami Badawi
 *
 */
public class IJGui implements GuiWrapper {
	
    public static final int MAX_LENGTH_BEFORE_USING_LOG_WINDOW = 1000;

	public static final IJGui INSTANCE = new IJGui();
	
	protected IJGui() {
	}
	
	@Override
	public void showMessage(String title, String text) {
        if (text != null && MAX_LENGTH_BEFORE_USING_LOG_WINDOW < text.length())
            IJ.log(text);
        else
    		IJ.showMessage(title,text);
	}

}
