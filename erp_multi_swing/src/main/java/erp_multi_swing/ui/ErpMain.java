package erp_multi_swing.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import erp_multi_swing.content.EmployeePanel;

public class ErpMain {
	public static void main(String[] args) {

		String str = "사원(1)";
		str = str.substring(str.indexOf("(")+1, str.length()-1);
		System.out.println(str);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
					
					DepartmentFrame deptFrame = new DepartmentFrame();
					deptFrame.setVisible(true);
					
					EmployeeFrame empFrame = new EmployeeFrame();
					empFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
