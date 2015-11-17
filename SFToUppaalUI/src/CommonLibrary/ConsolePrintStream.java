package CommonLibrary;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.text.JTextComponent;
import javax.swing.SwingUtilities;

public class ConsolePrintStream extends PrintStream {

	private JTextComponent text;
	private StringBuffer sb = new StringBuffer();

	public ConsolePrintStream(OutputStream out, JTextComponent text) {
		super(out);
		this.text = text;
	}
	
	public void write(byte[] buf, int off, int len) {
		final String message = new String(buf, off, len);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				sb.append(message);
				text.setText(sb.toString());
			}
		});
	}
	
	public void ClearInfo()
	{
		sb.delete(0, sb.length());
	}
	
}