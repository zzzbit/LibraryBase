/*
 * IShape.java
 *
 * Created on April 29, 2005, 4:47 PM
 */

package myUISegment;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * All drawing shapes should implement this interface.
 *
 * @author hysun
 */
public interface IShape {
    
    /** Corresponding to mouse pressed event type */
    public static final int RIGHT_PRESSED = 0;
    
    /** Corresponding to mouse released event type */
    public static final int LEFT_RELEASED = 1;
    
    /** Corresponding to mouse dragged event type */
    public static final int CURSOR_DRAGGED = 2;
    
    /** 
     * Code for processing draw cursor events in the drawing process.
     *
     * @param evt the MouseEvent being detected.
     * @param type the event type. can take values PRESSED, RELEASED, and 
     * DRAGGED.
     */
    public void processCursorEvent(MouseEvent evt, int type);
        
    /** 
     * To be called by the UI to draw out the shape. 
     *
     * @param g the Graphics2D context being passed for drawing.
     */
    public void draw(Graphics2D g);
    public void drawResult(Graphics2D g,ArrayList<Point> resultList);
    
}
