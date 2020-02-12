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

import erp_multi_common.dto.Employee;
import erp_multi_common.dto.Title;
import erp_multi_swing.content.TitlePanel;
import erp_multi_swing.service.TitleService;
import erp_multi_swing.table.TitleTablePanel;

@SuppressWarnings("serial")
public class TitleFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JPanel pContent;
	private TitleTablePanel pList;
	private TitlePanel pDepartment;
	private JPanel pBtns;
	private JButton btnAdd;
	private JButton btnCancel;
	private TitleService service;
	private DlgEmployee dialog;

	public TitleFrame() {
		service = new TitleService();
		dialog = new DlgEmployee();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("직책 관리");
		setBounds(100, 100, 450, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		pContent = new JPanel();
		contentPane.add(pContent);
		pContent.setLayout(new BorderLayout(0, 0));

		pDepartment = new TitlePanel();
		pContent.add(pDepartment, BorderLayout.CENTER);

		pBtns = new JPanel();
		contentPane.add(pBtns);

		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtns.add(btnAdd);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBtns.add(btnCancel);

		pList = new TitleTablePanel();
		contentPane.add(pList);

		List<Title> depts = service.showTitles();
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
		Title updateTitle = pDepartment.getItem();
		service.modifyTitle(updateTitle);
		pList.updateRow(updateTitle, pList.getSelectedRowIndex());
		btnAdd.setText("추가");
		pDepartment.clearTf();
		JOptionPane.showMessageDialog(null, String.format("%s(%d) 수정되었습니다", updateTitle.getTitleName(), updateTitle.getTitleNo()));
	}

	protected void btnAddActionPerformed(ActionEvent e) {
		try {
			Title newTitle = pDepartment.getItem();
			service.addTitle(newTitle);
			pList.addRow(newTitle);
			pDepartment.clearTf();
			JOptionPane.showMessageDialog(null, String.format("%s(%d) 추가되었습니다", newTitle.getTitleName(), newTitle.getTitleNo()));
		} catch (RuntimeException e1) {
			SQLException e2 = (SQLException) e1.getCause();
			if (e2.getErrorCode() == 1062) {
				JOptionPane.showMessageDialog(null, "직책번호가 중복");
				System.err.println(e2.getMessage());
				pDepartment.clearTf();
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

		JMenuItem showEmployee = new JMenuItem("동일 직책 사원 보기");
		showEmployee.addActionListener(myPopMenuListener);
		popMenu.add(showEmployee);

		return popMenu;
	}

	ActionListener myPopMenuListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("수정")) {
				Title title = pList.getSelectedItem();
				pDepartment.setItem(title);
				btnAdd.setText("수정");
			}
			if (e.getActionCommand().equals("삭제")) {
				Title delTitle = pList.getSelectedItem();
				service.removeTitle(delTitle);
				pList.removeRow();
				JOptionPane.showMessageDialog(null, "삭제되었습니다.");
			}
			if (e.getActionCommand().contentEquals("동일 직책 사원 보기")) {
				Title selectedTitle = pList.getSelectedItem(); // 선택한 부서정보
				List<Employee> list = service.showEmployeeGroupByTitle(selectedTitle);
				dialog.setEmpList(list);
				dialog.setTitle(selectedTitle.getTitleName() + " 직책");

				dialog.setVisible(true);
			}
		}
	};
}