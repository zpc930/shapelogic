package org.shapelogic.imageprocessing;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.filter.ExtendedPlugInFilter;
import ij.plugin.filter.PlugInFilterRunner;
import ij.process.ImageProcessor;
import java.awt.AWTEvent;

/** ColorReplacer replaces one color with another.<br />
 *
 * @author Sami Badawi
 */
public class ColorReplacer implements ExtendedPlugInFilter, DialogListener {
    private int flags = DOES_ALL|SUPPORTS_MASKING|PARALLELIZE_STACKS;
    private static double angle = 15.0;
    private static boolean enlarge;
    private static int gridLines = 1;
    private static boolean fillWithBackground;
    private static boolean interpolate = true;
    boolean canEnlarge;
    boolean isEnlarged;
    private int bitDepth;

    private ImagePlus imp;
    GenericDialog gd;
    PlugInFilterRunner pfr;

    @Override
    public int showDialog(ImagePlus imp, String command, PlugInFilterRunner pfr) {
        this.pfr = pfr;
        gd = new GenericDialog(getClass().getSimpleName(), IJ.getInstance());
        gd.addNumericField("Angle (degrees): ", angle, (int)angle==angle?1:2);
        gd.addNumericField("Grid Lines: ", gridLines, 0);
        gd.addCheckbox("Interpolate", interpolate);
        if (bitDepth==8 || bitDepth==24)
            gd.addCheckbox("Fill with Background Color", fillWithBackground);
        if (canEnlarge)
            gd.addCheckbox("Enlarge Image to Fit Result", enlarge);
        else
            enlarge = false;
        gd.addPreviewCheckbox(pfr);
        gd.addDialogListener(this);
        gd.showDialog();
        if (gd.wasCanceled()) {
            return DONE;
        }
        flags |= KEEP_PREVIEW;      // standard filter without enlarge
        if (imp.getStackSize()==1)
            flags |= NO_CHANGES;            // undoable as a "compound filter"
        return IJ.setupDialog(imp, flags);
    }

    @Override
    public void setNPasses(int arg0) {
    }

    @Override
    public int setup(String arg, ImagePlus imp) {
        this.imp = imp;
        if (imp!=null) {
            bitDepth = imp.getBitDepth();
        }
        return flags;
    }

    @Override
    public boolean dialogItemChanged(GenericDialog gd, AWTEvent e) {
        angle = gd.getNextNumber();
        //only check for invalid input to "angle", don't care about gridLines
        if (gd.invalidNumber()) {
            if (gd.wasOKed()) IJ.error("Angle is invalid.");
            return false;
        }
        gridLines = (int)gd.getNextNumber();
        interpolate = gd.getNextBoolean();
        if (bitDepth==8 || bitDepth==24)
            fillWithBackground = gd.getNextBoolean();
        if (canEnlarge)
            enlarge = gd.getNextBoolean();
        return true;
    }

    @Override
    public void run(ImageProcessor arg0) {

    }

}
