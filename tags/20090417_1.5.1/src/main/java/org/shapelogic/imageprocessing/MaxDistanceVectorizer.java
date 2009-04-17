package org.shapelogic.imageprocessing;


/** Vectorizer that is splitting lines based on max distance to line between end points.
 * 
 * The main idea is that this will read a whole multi line at a time.
 * Then later it will split it according to max distance of pixels to the line 
 * between start and end point of the multi line.
 * 
 * Maybe this could be completely abstracted out, maybe but at that point I 
 * will just take most of this class and turn it into a base class.
 * 
 * Always stop on junctions, if there is one junction point use that, but stop after.
 * N points are chosen last.
 * Never go from one N point to another.
 * If you are at a start point then just chose one direction.
 * Can I delegate this to a different object. I always need to find all the 
 * neighbors first. 
 * I might have to know how many N points there are if there are more just
 * add all to _unfinishedPoints. 
 * 
 * Treatment of different points:
 * Junction: add to new point, move to first junction. 
 * N points: count, keep track of first.
 * Other: count, keep track of first.
 * Unused: count, keep track of first. I think that is already done.
 * Used: count, keep track of first.
 * 
 * For each junction add to unfinished. Go to first junction.
 * If other points are available take first and go to it.
 * If only N point is available, if current point an N stop else go to that.
 * 
 * When coming to a new point check if it is a junction if stop if not on 
 * first point. It does not matter if the start point is used or not.
 * I think that at the end check to see if you can go to either a junction 
 * point or to the start point.
 * 
 * @author Sami Badawi
 *
 */
public class MaxDistanceVectorizer extends BaseMaxDistanceVectorizer {
}
