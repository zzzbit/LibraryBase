package myUISegment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Deal implements IShape{
   
    private Color color;
    
    private Stroke stroke;
    
    public ArrayList<Point> pointsList = new ArrayList<Point>(); 
    
    public Deal(Color c, Stroke s, int x, int y) {
        color = c;
        stroke = s;
        pointsList.add(new Point(x, y));
    }
    public void processCursorEvent(MouseEvent e, int t) {
        if (t != Deal.CURSOR_DRAGGED)
            return;
        pointsList.add(new Point(e.getX(), e.getY()));
    }
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(stroke);
        int size = pointsList.size();
        if (size == 0)
            return;
        int[][] points = new int[2][size];
        for (int i=0; i<size; i++) {
            Point p = (Point) pointsList.get(i);
            points[0][i] = p.x;
            points[1][i] = p.y;
        }
        
        int s = points[0].length;
        if (s == 1) {
            int x = points[0][0];
            int y = points[1][0];
            g.drawLine(x, y, x, y);
        } else {
            g.drawPolyline(points[0], points[1], s);
        }
    }
    public void drawResult(Graphics2D g,ArrayList<Point> resultList) {
        g.setColor(AppFrame.fgButton.getBackground());
        g.setStroke(stroke);
        for (int i = 0; i < resultList.size();i++){
        	g.drawLine(resultList.get(i).x, resultList.get(i).y, resultList.get(i).x, resultList.get(i).y);
        }
    }
}
