package musicSearch;

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
	private JLabel result;

	public FileDrop() {
		getContentPane().setBackground(Color.WHITE);
		Container CP = getContentPane();
		getContentPane().setLayout(null);
		
		result = new JLabel("");
		result.setBounds(194, 86, 95, 31);
		getContentPane().add(result);
		setSize(511, 279);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(200, 50);
		setTitle("\u6B4C\u8BCD\u4E0B\u8F7D");
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
				result.setText("下载进行中...");
				dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				List list = (List) (dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
				Iterator iterator = list.iterator();
				int num = 0;
				while (iterator.hasNext()) {
					File f = (File) iterator.next();
					if (new Search().searchIrc(f.getAbsolutePath())){
						num++;
					}
				}
				dtde.dropComplete(true);
				result.setText("下载完成"+num+"！");
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