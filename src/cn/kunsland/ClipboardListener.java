package cn.kunsland;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ClipboardListener extends Thread {
	private Clipboard clipboard = null;
	private String last_str = "";
	private WordProcessor wordProcessor;

	public ClipboardListener() {
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(300);
				listen();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void listen() {
		String str = null;
		try {
			str = (String) clipboard.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException
				| IllegalStateException e) {
//			System.out.println("[EXCEPTION]" + e.getMessage());
			str = last_str;
		}
		if (!Word.validateWord(str))
			return;
		str = Word.regularWord(str);
		if (last_str.equals(str))
			return;
		last_str = str;
		System.out.println(str);
		if (wordProcessor != null)
			wordProcessor.doClipboardChanged(str);
	}

	public void setWordProcessor(WordProcessor wordProcessor) {
		this.wordProcessor = wordProcessor;
	}

	public static void main(String[] args) {
		new ClipboardListener().run();
	}
}
