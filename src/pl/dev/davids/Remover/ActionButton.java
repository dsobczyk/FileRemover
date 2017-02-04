package pl.dev.davids.Remover;

import javax.swing.JButton;

public class ActionButton extends JButton{
	ActionButton(String name, boolean enabled) {
		super(name);
		this.setEnabled(enabled);
	}
}
