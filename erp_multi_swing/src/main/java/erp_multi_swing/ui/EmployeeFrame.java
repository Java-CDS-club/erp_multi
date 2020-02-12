package erp_multi_swing.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import erp_multi_common.dto.Department;
import erp_multi_common.dto.Employee;
import erp_multi_swing.content.EmployeePanel;
import erp_multi_swing.service.EmployeeService;
import erp_multi_swing.table.EmployeeTablePanel;

@SuppressWarnings("serial")
public class EmployeeFrame extends JFrame implements ActionListener, ItemListener {

	private JPanel contentPane;
	private JPanel pContent;
	private EmployeeTablePanel pList;
	private EmployeePanel pEmployee;
	private JPanel pBtns;
	private JButton btnAdd;
	private JButton btnCancel;
	private EmployeeService service;
	private DlgEmployee dialog;

	public EmployeeFrame() {
		service = new EmployeeService();
		dialog = new DlgEmployee();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("사원 관리");
		setBounds(100, 100, 550, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		pContent = new JPanel();
		contentPane.add(pContent);
		pContent.setLayout(new BorderLayout(0, 0));

		pEmployee = new EmployeePanel();
		
		pEmployee.setDeptList(service.showDepartments());
		
		pEmployee.setTitleList(service.showTitles());
		
		pContent.add(pEmployee, BorderLayout.CENTER);

		pBtns = new JPanel();
		contentPane.add(pBtns);

		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtns.add(btnAdd);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBtns.add(btnCancel);

		pList = new EmployeeTablePanel();
		contentPane.add(pList);

		List<Employee> empList = service.showEmployees();
		pList.loadData(empList);

		pList.setPopupMenu(createPopupMenu());
		
		pEmployee.getCmbDept().addItemListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			btnCancelActionPerformed(e);
		}
		if (e.getSource() == btnAdd) {
			if (e.getActionCommand().contentEquals("추가")) {
				btnAddActionPerformed(e);
			} else {
				btnUpdateActionPerformed(e);
			}
		}
	}

	private void btnUpdateActionPerformed(ActionEvent e) {
		Employee updateEmp = pEmployee.getItem();
		service.modifyEmployee(updateEmp);
		pList.updateRow(updateEmp, pList.getSelectedRowIndex());
		btnAdd.setText("추가");
		pEmployee.clearTf();
		JOptionPane.showMessageDialog(null, String.format("%s 사원이 수정되었습니다.", updateEmp.getEmpName()));
	}

	protected void btnAddActionPerformed(ActionEvent e) {
		try {
			Employee newEmp = pEmployee.getItem();
			service.addEmployee(newEmp);
			pList.addRow(newEmp);
			pEmployee.clearTf();
			JOptionPane.showMessageDialog(null, String.format("%s(%d)이 추가되었습니다.", newEmp.getEmpName(), newEmp.getEmpNo()));
		} catch (RuntimeException e1) {
			SQLException e2 = (SQLException) e1.getCause();
			if (e2.getErrorCode() == 1062) {
				JOptionPane.showMessageDialog(null, "사원번호 중복");
				System.err.println(e2.getMessage());
				return;
			}
			e1.printStackTrace();
		}
	}

	protected void btnCancelActionPerformed(ActionEvent e) {
		pEmployee.clearTf();
		btnAdd.setText("추가");
	}

	private JPopupMenu createPopupMenu() {
		JPopupMenu popMenu = new JPopupMenu();

		JMenuItem updateItem = new JMenuItem("수정");
		updateItem.addActionListener(myPopMenuListener);
		popMenu.add(updateItem);

		JMenuItem deleteItem = new JMenuItem("삭제");
		deleteItem.addActionListener(myPopMenuListener);
		popMenu.add(deleteItem);

		return popMenu;
	}

	ActionListener myPopMenuListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("수정")) {
				Employee selectedEmp = pList.getSelectedItem();
				pEmployee.setItem(selectedEmp);
				btnAdd.setText("수정");
			}
			if (e.getActionCommand().equals("삭제")) {
				Employee delEmp = pList.getSelectedItem();
				service.removeEmployee(delEmp);
				pList.removeRow();
				JOptionPane.showMessageDialog(null, "삭제되었습니다.");
				if (btnAdd.getText().contentEquals("수정")) {
					pEmployee.clearTf();
					btnAdd.setText("추가");
				}
			}
			
		}
	};
	
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == pEmployee.getCmbDept()) {
			pEmployeeCmbDeptItemStateChanged(e);
		}
	}
	
	protected void pEmployeeCmbDeptItemStateChanged(ItemEvent e) {
		Department selectedDept = (Department) pEmployee.getCmbDept().getSelectedItem();
		if (selectedDept == null) {
			
		}
		System.out.println("selectedDept " + selectedDept);
		pEmployee.setManagerList(service.showManagers(selectedDept));
	}
}