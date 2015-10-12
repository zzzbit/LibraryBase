

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;

public class FileDrop extends JFrame implements DropTargetListener {
	private static final long serialVersionUID = 1L;
	JLabel cmdLabel = new JLabel("文件拖到此处");

	public FileDrop() {
		Container CP = getContentPane();
		getContentPane().setLayout(null);
		cmdLabel.setBounds(0, 0, 485, 24);
		CP.add(cmdLabel);
		setSize(511, 279);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(200, 50);
		setTitle("文件拖放");
		new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
		setVisible(true);
	}

	public void dragEnter(DropTargetDragEvent dtde) {
	}

	public void dragExit(DropTargetEvent dte) {
	}

	public void dragOver(DropTargetDragEvent dtde) {
	}

	public void drop(DropTargetDropEvent dtde) {
		try {
			if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				List list = (List) (dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					File f = (File) iterator.next();
					this.cmdLabel.setText("文件路径：" + f.getAbsolutePath());
				}
				dtde.dropComplete(true);
				// this.updateUI();
			} else {
				dtde.rejectDrop();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (UnsupportedFlavorException ufe) {
			ufe.printStackTrace();
		}
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
	}

	public static void main(String[] args) {
		FileDrop c = new FileDrop();
		if (args.length != 0)
			c.cmdLabel.setText(args[0]); // ����Լ��Ŀؼ�������Ӧ�ķ���
	}
}