package erp_multi_swing.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import erp_multi_swing.content.DepartmentPanel;
import erp_multi_swing.exception.InvalidCheckException;
import erp_multi_swing.service.DepartmentService;
import erp_multi_swing.table.DepartmentTablePanel;

@SuppressWarnings("serial")
public class DepartmentFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JPanel pContent;
	private DepartmentTablePanel pList;
	private DepartmentPanel pDepartment;
	private JPanel pBtns;
	private JButton btnAdd;
	private JButton btnCancel;
	private DepartmentService service;
	private DlgEmployee dialog;

	public DepartmentFrame() {
		service = new DepartmentService();
		dialog = new DlgEmployee();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("부서 관리");
		setBounds(100, 100, 450, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		pContent = new JPanel();
		contentPane.add(pContent);
		pContent.setLayout(new BorderLayout(0, 0));

		pDepartment = new DepartmentPanel();
		pContent.add(pDepartment, BorderLayout.CENTER);

		pBtns = new JPanel();
		contentPane.add(pBtns);

		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtns.add(btnAdd);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBtns.add(btnCancel);

		pList = new DepartmentTablePanel();
		contentPane.add(pList);

		List<Department> depts = service.showDepartments();
		pList.loadData(depts);

		pList.setPopupMenu(createPopupMenu());
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
		Department newDept = pDepartment.getItem();
		service.modifyDepartment(newDept);
		pList.updateRow(newDept, pList.getSelectedRowIndex());
		btnAdd.setText("추가");
		pDepartment.clearTf();
		JOptionPane.showMessageDialog(null, "부서가 수정되었습니다.");
	}

	protected void btnAddActionPerformed(ActionEvent e) {
		try {
			Department newDept = pDepartment.getItem();
			service.addDepartment(newDept);
			pList.addRow(newDept);
			pDepartment.clearTf();
			JOptionPane.showMessageDialog(null, "부서가 추가되었습니다.");
		} catch(InvalidCheckException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		} catch (RuntimeException e1) {
			SQLException e2 = (SQLException) e1.getCause();
			if (e2.getErrorCode() == 1062) {
				JOptionPane.showMessageDialog(null, "부서번호가 중복");
				System.err.println(e2.getMessage());
				return;
			}
			
			e1.printStackTrace();
		} 
	}

	protected void btnCancelActionPerformed(ActionEvent e) {
		pDepartment.clearTf();
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

		JMenuItem showEmployee = new JMenuItem("소속 사원");
		showEmployee.addActionListener(myPopMenuListener);
		popMenu.add(showEmployee);

		return popMenu;
	}

	ActionListener myPopMenuListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("수정")) {
				Department upDept = pList.getSelectedItem();
				pDepartment.setItem(upDept);
				btnAdd.setText("수정");
			}
			if (e.getActionCommand().equals("삭제")) {
				Department delDept = pList.getSelectedItem();
				service.removeDepartment(delDept);
				pList.removeRow();
				JOptionPane.showMessageDialog(null, "삭제되었습니다.");
			}
			if (e.getActionCommand().contentEquals("소속 사원")) {
				Department selectedDept = pList.getSelectedItem(); // 선택한 부서정보
				List<Employee> list = service.showEmployeeGroupByDno(selectedDept);
				dialog.setEmpList(list);
				dialog.setTitle(selectedDept.getDeptName() + " 부서");
				dialog.setVisible(true);
			}
		}
	};
}