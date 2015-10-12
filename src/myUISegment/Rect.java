/*
 * Rect.java
 *
 * Created on April 29, 2005, 5:29 PM
 */

package myUISegment;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author hysun
 */
public class Rect implements IShape{

	protected Color color;
    
    protected Stroke stroke;
    
    protected int startX, startY, endX, endY;
    public Rect(Color c, Stroke s, int x, int y) {
    	color = c;
        stroke = s;
        startX = endX = x;
        startY = endY = y;
    }
    public void processCursorEvent(MouseEvent e, int t) {
        if (t != Rect.CURSOR_DRAGGED)
            return;
        int x = e.getX();
        int y = e.getY();
        if (e.isShiftDown()) {
            regulateShape(x, y);
        } else {
            endX = x;
            endY = y;
        }
    }
    protected void regulateShape(int x, int y) {
        int w = x - startX;
        int h = y - startY;
        int s = Math.min(Math.abs(w), Math.abs(h));
        if (s == 0) {
            endX = startX;
            endY = startY;
        } else {
            endX = startX + s * (w / Math.abs(w));
            endY = startY + s * (h / Math.abs(h));
        }
    }
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(stroke);
        int x, y, w, h;
        if (startX > endX) {
            x = endX;
            w = startX - endX;
        } else {
            x = startX;
            w = endX - startX;
        }
        if (startY > endY) {
            y = endY;
            h = startY - endY;
        } else {
            y = startY;
            h = endY - startY;
        }
        g.drawRect(x, y, w, h);
    }
    public void drawResult(Graphics2D g,ArrayList<Point> resultList) {
        g.setColor(AppFrame.fgButton.getBackground());
        g.setStroke(stroke);
        for (int i = 0; i < resultList.size();i++){
        	g.drawLine(resultList.get(i).x, resultList.get(i).y, resultList.get(i).x, resultList.get(i).y);
        }
    }
}
