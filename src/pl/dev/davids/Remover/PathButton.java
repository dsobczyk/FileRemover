package pl.dev.davids.Remover;

import javax.swing.JButton;
import javax.swing.JCheckBox;

public class PathButton extends JButton{
	PathButton(String name, boolean enabled) {
		super(name);
		this.setEnabled(enabled);
	}
	
}
