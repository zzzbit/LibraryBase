package picView;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;//过滤器、有关使用文件扩展名进行过滤的实现
import java.awt.image.*;//图形图像的所有类的超类。必须以特定于平台的方式获取图像。 
import java.awt.geom.AffineTransform;
import java.io.*;//数据流、序列化和文件系统提供系统输入和输出
import java.util.ArrayList;//最大限度地减少实现“随机访问”数据存储（如数组）支持的该接口所需的工作。
import java.awt.image.ConvolveOp;
import java.awt.Point;//坐标
import java.awt.geom.*;

public class ImageViewer extends JFrame {
	private Image img;
	private JButton b1, b2, b3, b4, b5, b6, b7, b8;
	private JPanel panel;
	Container c;
	DrawPanel dp;
	public boolean start = false;
	public int flag = 0;
	String name = "c:\\1.jpg";

	@SuppressWarnings("deprecation")
	public ImageViewer() {
		super("ImageViewer");
		c = getContentPane();
		panel = new JPanel();
		dp = new DrawPanel(name);
		c.add(dp, BorderLayout.CENTER);

		final MouseHandler handler = new MouseHandler(dp);
		dp.addMouseMotionListener(handler);
		dp.addMouseListener(handler);

		b1 = new JButton("打开");
		panel.add(b1);

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				final ExtensionFileFilter filter = new ExtensionFileFilter();
				filter.addExtension("jpg");
				filter.addExtension("jpeg");
				filter.addExtension("gif");
				filter.setdescription("Image Files");
				chooser.setFileFilter(filter);
				int result = chooser.showOpenDialog(dp);
				if (result == JFileChooser.APPROVE_OPTION) {
					name = chooser.getSelectedFile().getPath();
					getContentPane().remove(dp);
					removeMouseListener(handler);
					dp = new DrawPanel(name);
					getContentPane().add(dp, BorderLayout.CENTER);
					MouseHandler handler = new MouseHandler(dp);
					dp.addMouseMotionListener(handler);
					dp.addMouseListener(handler);
					dp.revalidate();

				}

			}
		});

		b2 = new JButton("缩放");
		panel.add(b2);
		b2.addActionListener(new Handler2());

		b3 = new JButton("翻转");
		panel.add(b3);
		b3.addActionListener(new Handler3());

		b4 = new JButton("移动");
		panel.add(b4);
		b4.addActionListener(new Handler4());

		b5 = new JButton("还原");
		panel.add(b5);
		b5.addActionListener(new Handler5());

		b6 = new JButton("边缘");
		panel.add(b6);
		b6.addActionListener(new Handler6());

		b7 = new JButton("画图");
		panel.add(b7);
		b7.addActionListener(new Handler7());

		b8 = new JButton("退出");
		panel.add(b8);
		b8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		c.add(panel, BorderLayout.SOUTH);

		setSize(550, 350);
		show();
	}

	private class Handler2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String str = JOptionPane.showInputDialog(null,
					"Please input zoom factor", "Message", 1);
			System.out.println(str);
			if ((str == null) || (str.length() == 0))
				JOptionPane.showMessageDialog(null, "The data cannot be null",
						"Message", 1);
			else
				dp.zoom(Double.parseDouble(str));

		}
	}

	private class Handler3 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String str = JOptionPane.showInputDialog(null,
					"Please input flip angle", "Message", 1);
			if ((str == null) || (str.length() == 0))
				JOptionPane.showMessageDialog(null, "The data cannot be null",
						"Message", 1);
			else
				dp.rotate(Integer.parseInt(str));
		}
	}

	private class Handler4 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dp.flag = 3;
		}
	}

	private class Handler5 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dp.OriginalImage();
		}
	}

	private class Handler6 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dp.Edge();
		}
	}

	private class Handler7 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dp.flag = 5;
		}
	}

	private static class ExtensionFileFilter extends FileFilter {
		private String description = "";
		private ArrayList extensions = new ArrayList();

		public void addExtension(String extension) {
			if (!extension.startsWith("."))
				extension = "." + extension;
			extensions.add(extension.toLowerCase());
		}

		public void setdescription(String aDescription) {
			description = aDescription;
		}

		public String getDescription() {
			return description;
		}

		public boolean accept(File f) {
			if (f.isDirectory())
				return true;
			String name = f.getName().toLowerCase();
			for (int i = 0; i < extensions.size(); i++)
				if (name.endsWith((String) extensions.get(i)))
					return true;
			return false;

		}
	}

	public static void main(String[] args) {
		ImageViewer app = new ImageViewer();
		app.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}

class MouseHandler extends MouseInputAdapter {

	private Point currentPos, lastPos;
	private DrawPanel drawpanel;

	public MouseHandler(DrawPanel panel) {
		this.drawpanel = panel;
	}

	public void mousePressed(MouseEvent e) {
		currentPos = e.getPoint();
		lastPos = e.getPoint();
		drawpanel.drawIt(lastPos, currentPos);

	}

	public void mouseDragged(MouseEvent e) {

		currentPos = e.getPoint();
		drawpanel.drawIt(lastPos, currentPos);
		lastPos = currentPos;

	}
}

class DrawPanel extends JPanel {

	public Image img;
	BufferedImage bi, bi1, bi2, biEdge, bimg, buffer;
	int newX = 400, newY = 400;
	int flag = 0;
	int x, y;
	private Point currentPos, lastPos;
	MouseHandler mousehandler;
	Graphics2D bufferG;

	public DrawPanel(String name) {
		img = Toolkit.getDefaultToolkit().getImage(name);
		this.addMouseMotionListener(new MouserMotionHandler());
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(img, 1);
		try {
			mt.waitForAll();
		} catch (Exception e) {
			System.out.println("Exception While Loading.");
		}
		if (img.getWidth(this) == -1) {
			System.out.println("***make sure you have the textture image"
					+ "*.jpg file in the same directory.***");
			System.exit(0);

		}
		if (img.getWidth(this) != 0 && img.getHeight(this) != 0)
			bi = new BufferedImage(img.getWidth(this), img.getHeight(this),
					BufferedImage.TYPE_INT_ARGB);
		Graphics2D big = bi.createGraphics();
		big.drawImage(img, 0, 0, this);
		bi1 = bi;
		bi2 = bi;
		biEdge = bi;
	}

	private class MouserMotionHandler extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			repaint();

		}
	}

	public void zoom(double factor) {
		int w = img.getWidth(this);
		int h = img.getHeight(this);

		newX = (int) (w * factor);
		newY = (int) (h * factor);

		bi1 = new BufferedImage(newX, newY, BufferedImage.TYPE_INT_ARGB);
		Graphics2D big = bi1.createGraphics();
		big.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		big.drawImage(img, 0, 0, newX, newY, this);
		repaint();
		flag = 1;

	}

	public void rotate(int degree) {
		int angle = degree % 360;
		int w = img.getWidth(this);
		int h = img.getHeight(this);
		if (img.getWidth(this) != 0 && img.getHeight(this) != 0)
			bi2 = new BufferedImage(bi.getWidth(this), bi.getHeight(this),
					BufferedImage.TYPE_INT_ARGB);
		Graphics2D big1 = bi2.createGraphics();
		AffineTransform at = new AffineTransform();
		at.translate(w / 10, h / 10);
		at.rotate(Math.toRadians(angle));
		big1.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		big1.drawImage(img, at, this);
		repaint();
		flag = 2;

	}

	public void OriginalImage() {
		flag = 0;
		repaint();
	}

	public void drawIt(Point lastPos, Point currentPos) {
		this.currentPos = currentPos;
		this.lastPos = lastPos;
		repaint();
	}

	public void Edge() {
		float elements[] = { 0.0f, -1.0f, 0.0f, -1.0f, 4.0f, -1.0f, 0.0f,
				-1.0f, 0.0f };
		int w = img.getWidth(this);
		int h = img.getHeight(this);
		if (img.getWidth(this) != 0 && img.getHeight(this) != 0)
			biEdge = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D bigEdge = biEdge.createGraphics();
		bigEdge.drawImage(img, 0, 0, this);
		BufferedImageOp biop = null;
		AffineTransform at = new AffineTransform();
		if (img.getWidth(this) != 0 && img.getHeight(this) != 0)
			bimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Kernel kernel = new Kernel(3, 3, elements);
		ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		cop.filter(biEdge, bimg);
		biop = new AffineTransformOp(at,
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		repaint();
		flag = 4;
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		if (flag == 0)
			g2D.drawImage(bi, 0, 0, this);
		else if (flag == 1)
			g2D.drawImage(bi1, 0, 0, this);
		else if (flag == 2)
			g2D.drawImage(bi2, 0, 0, this);
		else if (flag == 3)
			g2D.drawImage(bi, x, y, this);
		else if (flag == 4)
			g2D.drawImage(bimg, 0, 0, this);
		else if (flag == 5) {
			if (buffer == null) {
				buffer = (BufferedImage) this.createImage(this.getWidth(),
						this.getHeight());
				bufferG = buffer.createGraphics();
			}

			if (currentPos != null && lastPos != null)
				bufferG.draw(new Line2D.Double(lastPos, currentPos));
			g2D.drawImage(buffer, 0, 0, this);

		}

	}
}
