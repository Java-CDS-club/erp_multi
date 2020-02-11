package erp_multi_swing.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import erp_multi_common.dto.Employee;
import erp_multi_swing.table.EmployeeTablePanel;

@SuppressWarnings("serial")
public class DlgEmployee extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private EmployeeTablePanel pEmployee;

	public DlgEmployee() {
		setBounds(100, 100, 550, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			pEmployee = new EmployeeTablePanel();
			contentPanel.add(pEmployee, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(this);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			okButtonActionPerformed(e);
		}
	}
	protected void okButtonActionPerformed(ActionEvent e) {
		dispose();
	}
	
	public void setEmpList(List<Employee> items) {
		pEmployee.loadData(items);
	}
}