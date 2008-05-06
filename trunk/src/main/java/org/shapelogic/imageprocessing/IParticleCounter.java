/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.shapelogic.imageprocessing;

import org.shapelogic.color.IColorHypothesisFinder;
import org.shapelogic.imageutil.ImageOperation;

/**
 *
 * @author sbadawi
 */
public interface IParticleCounter extends ImageOperation {

    int getParticleCount();

    SBSegmentation getSegmentation();

    String getStatus();

    boolean isParticleImage();
    
    IColorHypothesisFinder getColorHypothesisFinder();
    
    void setColorHypothesisFinder(IColorHypothesisFinder colorHypothesisFinder);
    
    double getMaxDistance();

    void setMaxDistance(double maxDistance);
    
    int getMinPixelsInArea();
    
    void setMinPixelsInArea(int minPixelsInArea);

    int getMaxIterations();

    void setMaxIterations(int maxIterations);
}
