/*
 * DrawingBoard.java
 *
 * Created on April 29, 2005, 7:13 PM
 */

package myUISegment;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * 
 * @author hysun
 */
public class DrawingBoard extends ZPanel implements MouseListener,
		MouseMotionListener {

	public static final int TOOL_PENCIL = 0;
	public static final int TOOL_RECT = 1;

	public static final Stroke[] STROKES = new Stroke[] {
			new BasicStroke(1.0f), new BasicStroke(2.0f),
			new BasicStroke(5.0f), new BasicStroke(7.5f),
			new BasicStroke(10.0f) };

	private IShape currentShape;
	private ArrayList<Point> resultList;
	private int tool;
	private int drawFlag;
	private int strokeIndex;

	public DrawingBoard() {
		tool = TOOL_PENCIL;
		strokeIndex = 0;

		setBackground(Color.WHITE);
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		setOpaque(false);
		setForeground(Color.black);
		setBackground(Color.white);

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void setTool(int t) {
		tool = t;
	}

	public void setStrokeIndex(int i) {
		if (i < 0 || i > 4)
			throw new IllegalArgumentException("Invaild Weight Specified!");
		strokeIndex = i;
	}

	public void clearBoard() {
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		try {
			
			if (drawFlag == 1){
				currentShape.draw(g2d);
			}
			else {
				currentShape.drawResult(g2d,resultList);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1){
			if (tool == TOOL_PENCIL)
				currentShape = new Deal(Color.red, STROKES[strokeIndex],
				e.getX(), e.getY());
			else if (tool == TOOL_RECT) {
                currentShape = new Rect(this.getForeground(), 
                            STROKES[strokeIndex], e.getX(), e.getY());
			}
		}
		else if (e.getButton() == 3) {
			currentShape = new Deal(Color.green, STROKES[strokeIndex],
					e.getX(), e.getY());
		}
		drawFlag = 1;
		repaint();
	}

	public void mouseDragged(MouseEvent e) {
		currentShape.processCursorEvent(e, Deal.CURSOR_DRAGGED);
		drawFlag = 1;
		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		ArrayList<Point> points = ((Deal)currentShape).pointsList;
		if (e.getButton() == 1) {
//			currentShape.processCursorEvent(e, Deal.LEFT_RELEASED);
//			System.out.println("hi");
//			repaint();
			PicSegment picSegment = new PicSegment();
			resultList = picSegment.startSegment("C:\\1.jpg", "C:\\1_b.jpg", points);
			drawFlag = 2;
			repaint();
		}
		else if (e.getButton() == 3){
			
		}
//		clearBoard();
//		currentShape = null;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

}
