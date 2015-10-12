package UI;

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

	public FileDrop() {
		getContentPane().setBackground(Color.WHITE);
		Container CP = getContentPane();
		getContentPane().setLayout(null);
		setSize(511, 279);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(200, 50);
		setTitle("\u62D6\u66F3\u6587\u4EF6\u5230\u7A97\u4F53\u4E0A");
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
				String result = "";
				while (iterator.hasNext()) {
					File f = (File) iterator.next();
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
	}
}