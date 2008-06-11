package org.shapelogic.imageprocessing;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.filter.ExtendedPlugInFilter;
import ij.plugin.filter.PlugInFilterRunner;
import java.awt.AWTEvent;

import org.shapelogic.imageutil.PixelHandler;
import org.shapelogic.imageutil.PixelHandlerOperation;
import org.shapelogic.imageutil.PlugInFilterAdapter;

/** ColorReplacer replaces one color with another.<br />
 *
 * XXX: This class could be one generic class that could handle all PixelHanlers.
 * It need an input parameter definition that is using reflection.<br />
 *
 * For comparison with ColorReplacer.java, this is a little longer but it is
 * more general and pretty straightforward.<br />
 * 
 * @author Sami Badawi
 */
public class ColorReplacerSL extends PlugInFilterAdapter
        implements ExtendedPlugInFilter, DialogListener 
{
    private int _flags = DOES_8G|DOES_RGB|SUPPORTS_MASKING|PARALLELIZE_STACKS;

    protected GenericDialog _gd;
    protected PlugInFilterRunner _pfr;
    protected PixelHandler _pixelHandler;
    protected ColorReplacePixelHandler _handler;
    
    public ColorReplacerSL() {
        super(new PixelHandlerOperation(new ColorReplacePixelHandler()));
        _pixelHandler = ((PixelHandlerOperation)getImageOperation()).getPixelHandler();
        _handler = (ColorReplacePixelHandler)_pixelHandler;
    }

    @Override
    public int showDialog(ImagePlus imp, String command, PlugInFilterRunner pfr) {
        _pfr = pfr;
        _gd = new GenericDialog(getClass().getSimpleName(), IJ.getInstance());
        _gd.addNumericField("Max distance: ", _handler.maxDistance, 1);
        _gd.addNumericField("Reference color: ", _handler.referenceColor, 0);
        _gd.addPreviewCheckbox(pfr);
        _gd.addDialogListener(this);
        _gd.showDialog();
        if (_gd.wasCanceled()) {
            return DONE;
        }
        _flags |= KEEP_PREVIEW;      // standard filter without enlarge
        if (imp.getStackSize()==1)
            _flags |= NO_CHANGES;            // undoable as a "compound filter"
        return IJ.setupDialog(imp, _flags);
    }

    @Override
    public void setNPasses(int arg0) {
    }

    @Override
    public boolean dialogItemChanged(GenericDialog gd, AWTEvent e) {
        _handler.maxDistance = gd.getNextNumber();
        if (gd.invalidNumber()) {
            if (gd.wasOKed()) IJ.error("Angle is invalid.");
            return false;
        }
        _handler.referenceColor = (int)gd.getNextNumber();
        return true;
    }
}
