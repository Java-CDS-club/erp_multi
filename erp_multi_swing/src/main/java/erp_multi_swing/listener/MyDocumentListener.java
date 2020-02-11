package erp_multi_swing.listener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public interface MyDocumentListener extends DocumentListener {

	@Override
	default void changedUpdate(DocumentEvent e) {
		msg();

	}

	@Override
	default void insertUpdate(DocumentEvent e) {
		msg();

	}

	@Override
	default void removeUpdate(DocumentEvent e) {
		msg();
	}
	
	public abstract void msg();

}
