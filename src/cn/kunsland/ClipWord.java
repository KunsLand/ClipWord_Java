package cn.kunsland;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class ClipWord extends JFrame implements WordProcessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -693174504384257845L;
	private JEditorPane contentPane;
	private ClipboardListener clipboardListener;
	private Point initialClick;
	private JFrame parent = this;
	private JLabel label_quit;
	private int width = 450, height = 300;
	private int width_quit = 25, height_quit = 25;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClipWord frame = new ClipWord();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClipWord() {
		setTitle("ClipWord");
		setIconImage(new ImageIcon("icons/dictionary-icon.png").getImage());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, width, height);
		contentPane = new JEditorPane();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.gray);
		contentPane.setContentType("text/html");
		contentPane.setEditable(false);
		contentPane.setText(ContentController.getWelcome());
		JScrollPane scrollPane = new JScrollPane(contentPane);
		setUndecorated(true);
		setContentPane(scrollPane);

		try {
			BufferedImage img = ImageIO.read(new File("icons/quit.png"));
			Image img_quit = img.getScaledInstance(width_quit, height_quit,
					Image.SCALE_SMOOTH);
			label_quit = new JLabel(new ImageIcon(img_quit));
			label_quit.setBounds(width - width_quit - 6, 5, width_quit,
					height_quit);
			label_quit.setVisible(false);
			label_quit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseExited(MouseEvent e) {
					label_quit.setVisible(false);
				}

				@Override
				public void mousePressed(MouseEvent e) {
					parent.dispatchEvent(new WindowEvent(parent,
							WindowEvent.WINDOW_CLOSING));
				}
			});
			contentPane.add(label_quit);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		clipboardListener = new ClipboardListener();
		clipboardListener.setWordProcessor(this);
		clipboardListener.start();

		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (e.getX() > width - width_quit - 6
						&& e.getY() < 5 + height_quit)
					label_quit.setVisible(true);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// get location of Window
				int thisX = parent.getLocation().x;
				int thisY = parent.getLocation().y;

				// Determine how much the mouse moved since the initial click
				int xMoved = e.getX() - initialClick.x;
				int yMoved = e.getY() - initialClick.y;

				// Move window to this position
				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				parent.setLocation(X, Y);
			}
		});
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
				getComponentAt(initialClick);
			}
		});
	}

	@Override
	public void doClipboardChanged(String word) {
		if (word == null || word.isEmpty())
			return;
		super.setAlwaysOnTop(true);
		super.toFront();
		super.requestFocus();
		super.setAlwaysOnTop(false);
		contentPane.setText(ContentController.getWaiting());
		String info = OnlineDictionary.getFromIciba(word);
		contentPane.setText(info);
	}
}
