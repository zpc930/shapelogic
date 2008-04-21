package org.shapelogic.imageutil;

import ij.IJ;

/** Very thin wrapper around ImageJ showMessage().<br />
 * 
 * @author Sami Badawi
 *
 */
public class IJGui implements GuiWrapper {
	
	public static final IJGui INSTANCE = new IJGui();
	
	protected IJGui() {
	}
	
	@Override
	public void showMessage(String title, String text) {
		IJ.showMessage(title,text);
	}

}
