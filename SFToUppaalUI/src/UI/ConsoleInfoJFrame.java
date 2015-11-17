package UI;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import CommonLibrary.ConsolePrintStream;

public class ConsoleInfoJFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5977143315156481007L;

	private JPanel mDescPanel = null;
	private JPanel mConsolePanel = null;
	private JPanel mButtonPanel = null;

	private JLabel mDescription = null;
	private JTextComponent mConsoleTextComponent = null;
	private JScrollPane mConsole = null;
	private JButton mCancelButton = null;
	private ConsolePrintStream consolestream = null;

	private JFrame mSelfReference = this;

	public ConsoleInfoJFrame(String title) {
		super(title);
		InitComponents();
		consolestream = new ConsolePrintStream(System.out,
				mConsoleTextComponent);
		System.setOut(consolestream);
		System.setErr(consolestream);
	}

	private void InitComponents() {
		// flow layout
		mDescPanel = new JPanel();
		mDescPanel.setLayout(null);
		mDescPanel.setBounds(0, 0, 500, 25);
		mConsolePanel = new JPanel();
		mConsolePanel.setLayout(null);
		mConsolePanel.setBounds(0, 25, 500, 185);
		mButtonPanel = new JPanel();
		mButtonPanel.setLayout(null);
		mButtonPanel.setBounds(0, 210, 500, 30);
		// create elements
		mDescription = new JLabel("Console Output:");
		mDescription.setHorizontalAlignment(SwingConstants.CENTER);
		mDescription.setBounds(172, 0, 150, 25);
		mDescPanel.add(mDescription);
		mConsoleTextComponent = new JTextArea("");
		mConsoleTextComponent.setSize(378, 178);
		mConsoleTextComponent.setEditable(false);
		mConsole = new JScrollPane(mConsoleTextComponent);
		mConsole.setBounds(50, 0, 380, 180);
		mConsolePanel.add(mConsole);
		mCancelButton = new JButton("Close");
		mCancelButton.setBounds(210, 0, 80, 25);
		mButtonPanel.add(mCancelButton);
		// frame(container) layout.
		Container panel = getContentPane();
		panel.setLayout(null);
		panel.add(mDescPanel);
		panel.add(mConsolePanel);
		panel.add(mButtonPanel);
		// set button action
		mCancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClearInfo();
				mSelfReference.setVisible(false);
			}
		});
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) {
				ClearInfo();
				mSelfReference.setVisible(false);
			}
		});

		setSize(500, 270);
		setLocation(320,200);
		mSelfReference.setVisible(false);
	}

	public void ClearInfo() {
		consolestream.ClearInfo();
		mConsoleTextComponent.setText("");
	}

}