package pl.dev.davids.Remover;

import javax.swing.JTextField;

public class PathText extends JTextField {
	public PathText(String name, boolean enabled) {
		super(name);
		this.setEnabled(enabled);
	}
}
