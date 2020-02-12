package erp_multi_swing.ui;

import java.awt.EventQueue;

import javax.swing.UIManager;

public class ErpMain {
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
					
					DepartmentFrame deptFrame = new DepartmentFrame();
					deptFrame.setVisible(true);
					
					EmployeeFrame empFrame = new EmployeeFrame();
					empFrame.setVisible(true);
					
					TitleFrame titleFrame = new TitleFrame();
					titleFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
