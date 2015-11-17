package UI;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import InvokeLibrary.StateflowToUppaalInvoker;

public class MainUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8402105215879212082L;
	private JPanel mSourcePanel = null;
	private JPanel mDestPanel = null;
	private JPanel mButtonPanel = null;

	private JLabel mSourceLabel = new JLabel("Source:");
	private JFileChooser mSourceChooser = new JFileChooser();
	private JTextField mSourcePath = new JTextField();
	private JButton mSourceChooseButton = new JButton("Choose");
	private JLabel mDestLabel = new JLabel("Dest(Optional):");
	private JFileChooser mDestChooser = new JFileChooser();
	private JTextField mDestPath = new JTextField();
	private JButton mDestChooseButton = new JButton("Choose");
	private JButton mTransferButton = null;
	private JButton mCancelButton = null;
	private ConsoleInfoJFrame mConsoleFrame = null;

	private JFrame mSelfReference = this;

	public MainUI(String title) {
		super(title);
		// create child frame
		mConsoleFrame = new ConsoleInfoJFrame("Console Panel");
		// set file chooser
		mSourceChooser.addChoosableFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "Files end with(*.slx)";
			}
			@Override
			public boolean accept(File f) {
				if (f.isDirectory() || f.getName().endsWith(".slx")) {
					return true;
				}
				return false;
			}
		});
		mDestChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		mDestChooser.addChoosableFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "Directories";
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				return false;
			}
		});
		// flow layout
		mSourcePanel = new JPanel();
		mSourcePanel.setSize(425, 50);
		mSourcePanel.setLayout(null);
		mDestPanel = new JPanel();
		mDestPanel.setSize(425, 50);
		mDestPanel.setLayout(null);
		mButtonPanel = new JPanel();
		mButtonPanel.setLayout(null);
		mButtonPanel.setSize(425, 50);
		mSourcePanel.add(mSourceLabel);
		mSourcePath.setBounds(100, 10, 220, 30);
		mSourcePath.setFont(new Font("simsun", Font.PLAIN, 14));
		mSourcePath.setEditable(false);
		mSourcePanel.add(mSourcePath);
		mSourceChooseButton.setBounds(330, 10, 80, 30);
		mSourcePanel.add(mSourceChooseButton);
		mDestPanel.add(mDestLabel);
		mDestPath.setBounds(100, 10, 220, 30);
		mDestPath.setFont(new Font("simsun", Font.PLAIN, 14));
		mDestPath.setEditable(false);
		mDestPanel.add(mDestPath);
		mDestChooseButton.setBounds(330, 10, 80, 30);
		mDestPanel.add(mDestChooseButton);
		// create elements
		mSourceLabel.setBounds(0, 0, 90, 50);
		mSourceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mDestLabel.setBounds(0, 0, 90, 50);
		mDestLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mTransferButton = new JButton("Transfer");
		mCancelButton = new JButton("Exit");
		mTransferButton.setBounds(227, 10, 90, 30);
		mCancelButton.setBounds(112, 10, 90, 30);
		mButtonPanel.add(mCancelButton);
		mButtonPanel.add(mTransferButton);
		// frame(container) layout.
		Container panel = getContentPane();
		panel.setLayout(new GridLayout(3, 1));
		panel.add(mSourcePanel);
		panel.add(mDestPanel);
		panel.add(mButtonPanel);
		mSourceChooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = mSourceChooser.showOpenDialog(mSelfReference);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: "
							+ mSourceChooser.getSelectedFile().getName());
					mSourcePath.setText(mSourceChooser.getSelectedFile()
							.getAbsolutePath());
				}
			}
		});
		mDestChooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = mDestChooser.showOpenDialog(mSelfReference);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to save here: "
							+ mDestChooser.getSelectedFile().getName());
					mDestPath.setText(mDestChooser.getSelectedFile()
							.getAbsolutePath());
				}
			}
		});

		// set button action
		mCancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		mTransferButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String source = mSourcePath.getText().trim();
				String target = mDestPath.getText().trim();
				int arglen = 2;
				if (source == null || source.equals(""))
				{
					JOptionPane.showMessageDialog(mSelfReference, "Source file is null", "Source file is null", JOptionPane.YES_OPTION);
					return;
				}
				if (!source.endsWith(".slx"))
				{
					JOptionPane.showMessageDialog(mSelfReference, "Source file is not end with .slx", "Source file is not end with .slx", JOptionPane.YES_OPTION);
					return;
				}
				if (target != null && !target.equals("") && !(new File(target)).isDirectory())
				{
					JOptionPane.showMessageDialog(mSelfReference, "Dest position to put the generated file must be a directory, not a file.", "Dest position to put the generated file must be a directory, not a file.", JOptionPane.YES_OPTION);
					return;
				}
				if (target==null || target.equals(""))
				{
					arglen = 1;
				}
				String[] args = new String[arglen];
				args[0] = source;
				if (target!=null && !target.equals(""))
				{
					args[1] = target;
				}
				StateflowToUppaalInvoker.InvokeStateflowToUppaalLogic(args);
				mConsoleFrame.ClearInfo();
				mConsoleFrame.setVisible(true);
				mSourcePath.setText("");
				mDestPath.setText("");
			}
		});
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});

		setSize(425, 170);
		setLocation(400, 200);
		setVisible(true);
	}

	public static void main(String[] args) {
		new MainUI("StateflowToUppaal");
	}

}