package erp_multi_swing.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import erp_multi_common.dto.Department;
import erp_multi_common.dto.Employee;
import erp_multi_common.dto.Title;
import erp_multi_swing.listener.MyDocumentListener;

@SuppressWarnings("serial")
public class EmployeePanel extends AbsItemPanel<Employee> implements ActionListener{
	private JTextField tfNo;
	private JTextField tfName;
	private JPasswordField pfPasswd1;
	private JPasswordField pfPasswd2;
	private JDateChooser tfHireDate;
	private JLabel lblPic;
	private JButton btnAdd;
	private Dimension picDimen;
	private JComboBox<Department> cmbDept;
	private JComboBox<Title> cmbTitle;
	private JComboBox<Employee> cmbMgn;
	private JLabel lblEqual;
	private String picPath;
	private JSpinner spSalary;
	
	public EmployeePanel() {
		picDimen = new Dimension(100, 150);
		initialize();
	}

	private void initialize() {
		setBorder(new TitledBorder(null, "사원 정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));

		JPanel pTop = new JPanel();
		pTop.setPreferredSize(new Dimension(100, 10));
		pTop.setBorder(new EmptyBorder(0, 0, 0, 0));
		pTop.setLayout(new BoxLayout(pTop, BoxLayout.Y_AXIS));
		add(pTop, BorderLayout.WEST);

		lblPic = new JLabel("");
		lblPic.setHorizontalAlignment(SwingConstants.CENTER);
		lblPic.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPic.setSize(picDimen);
		lblPic.setPreferredSize(picDimen);
		setPic(getClass().getClassLoader().getResource("no-image.png").getPath());
		
		JPanel pPic = new JPanel();
		pPic.add(lblPic);
		
		btnAdd = new JButton("증명사진");
		btnAdd.addActionListener(this);
		
		pPic.add(btnAdd);
		pTop.add(pPic);
		
		JPanel pEmp = new JPanel();
		add(pEmp);
		pEmp.setLayout(new GridLayout(0, 2, 10, 10));

		JLabel lblNo = new JLabel("사원 번호");
		lblNo.setHorizontalAlignment(SwingConstants.RIGHT);
		pEmp.add(lblNo);

		tfNo = new JTextField();
		pEmp.add(tfNo);
		tfNo.setColumns(10);

		JLabel lblName = new JLabel("성명");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		pEmp.add(lblName);

		tfName = new JTextField();
		tfName.setColumns(10);
		pEmp.add(tfName);

		JLabel lblDept = new JLabel("부서");
		lblDept.setHorizontalAlignment(SwingConstants.RIGHT);
		pEmp.add(lblDept);

		cmbDept = new JComboBox<>();
		pEmp.add(cmbDept);

		JLabel lblManger = new JLabel("직속상사");
		lblManger.setHorizontalAlignment(SwingConstants.RIGHT);
		pEmp.add(lblManger);

		cmbMgn = new JComboBox<>();
		pEmp.add(cmbMgn);

		JLabel lblTitle = new JLabel("직책");
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		pEmp.add(lblTitle);

		cmbTitle = new JComboBox<>();
		pEmp.add(cmbTitle);

		JLabel lblPasswd1 = new JLabel("비밀번호");
		lblPasswd1.setHorizontalAlignment(SwingConstants.RIGHT);
		pEmp.add(lblPasswd1);

		pfPasswd1 = new JPasswordField();
		pEmp.add(pfPasswd1);

		JLabel lblPasswd2 = new JLabel("비밀번호 확인");
		lblPasswd2.setHorizontalAlignment(SwingConstants.RIGHT);
		pEmp.add(lblPasswd2);

		lblEqual = new JLabel("");
		lblEqual.setFont(new Font("굴림", Font.BOLD, 20));
		lblEqual.setForeground(Color.RED);
		lblEqual.setHorizontalAlignment(SwingConstants.CENTER);
		
		pfPasswd2 = new JPasswordField();
		pfPasswd2.getDocument().addDocumentListener(new MyDocumentListener() {
			@Override
			public void msg() {
				String pw1 = new String(pfPasswd1.getPassword());
				String pw2 = new String(pfPasswd2.getPassword());
				if (pw1.length() == 0 && pw2.length() == 0) {
					lblEqual.setText("");
				} else if (pw1.equals(pw2)) {
					lblEqual.setText("비밀번호 일치.");
				} else {
					lblEqual.setText("비밀번호 불일치.");
				}				
			}
		});
		pEmp.add(pfPasswd2);
		
		JPanel panel = new JPanel();
		pEmp.add(panel);
		
		pEmp.add(lblEqual);

		JLabel lblSalary = new JLabel("급여");
		lblSalary.setHorizontalAlignment(SwingConstants.RIGHT);
		pEmp.add(lblSalary);

		spSalary = new JSpinner();
		spSalary.setModel(new SpinnerNumberModel(new Integer(1500000), null, null, new Integer(100000)));
		pEmp.add(spSalary);

		JLabel lblHireDate = new JLabel("입사일");
		lblHireDate.setHorizontalAlignment(SwingConstants.RIGHT);
		pEmp.add(lblHireDate);

		tfHireDate = new JDateChooser(new Date(), "yyyy-MM-dd HH:mm");
		tfHireDate.setAlignmentX(CENTER_ALIGNMENT);
		pEmp.add(tfHireDate);		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd) {
			btnAddActionPerformed(e);
		}
	}
	
	protected void btnAddActionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF & PNG Images", "jpg", "gif", "png");
		chooser.setFileFilter(filter);
		int ret = chooser.showOpenDialog(null);
		if (ret != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다", "경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		picPath = chooser.getSelectedFile().getPath();
		setPic(picPath);
	}
	
	private void setPic(String imgPath) {
		lblPic.setIcon(new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance((int)picDimen.getWidth(), (int)picDimen.getHeight(),  Image.SCALE_DEFAULT)));
	}
	
	private void setPic(byte[] img) {
		lblPic.setIcon(new ImageIcon(img));
	}
	
	public void setDeptList(List<Department> deptList) {
		DefaultComboBoxModel<Department> model = new DefaultComboBoxModel<>(new Vector<>(deptList));
		cmbDept.setModel(model);
		cmbDept.setSelectedIndex(-1);
	}
	
	public void setTitleList(List<Title> titleList) {
		DefaultComboBoxModel<Title> model = new DefaultComboBoxModel<>(new Vector<>(titleList));
		cmbTitle.setModel(model);
		cmbTitle.setSelectedIndex(-1);
	}
	
	public void setManagerList(List<Employee> mgnList) {
		DefaultComboBoxModel<Employee> model = new DefaultComboBoxModel<>(new Vector<>(mgnList));
		cmbMgn.setModel(model);
		cmbMgn.setSelectedIndex(-1);
	}
	
	@Override
	public Employee getItem() {
		int empNo = Integer.parseInt(tfNo.getText().trim());
		String empName = tfName.getText().trim();
		Title title = (Title) cmbTitle.getSelectedItem();
		Employee manager = (Employee) cmbMgn.getSelectedItem();
		int salary = (int) spSalary.getValue();
		Department dept = (Department) cmbDept.getSelectedItem();
		String passwd = null;
		if (lblEqual.getText().contentEquals("비밀번호 일치.")) {
			passwd = new String(pfPasswd1.getPassword());
		}
		Date hireDate = tfHireDate.getDate();
		Employee newEmp = new Employee(empNo, empName, title, manager, salary, dept, passwd, hireDate);
		newEmp.setPic(getImage());
		return newEmp;
	}

	@Override
	public void setItem(Employee item) {
		tfNo.setText(item.getEmpNo()+"");
		tfName.setText(item.getEmpName());
		pfPasswd1.setText("");
		pfPasswd2.setText("");
		tfHireDate.setDate(item.getHireDate());
		setPic(item.getPic());
		cmbDept.setSelectedItem(item.getDept());
		cmbTitle.setSelectedItem(item.getTitle());
		cmbMgn.setSelectedItem(item.getManager());
		lblEqual.setText("");
	}

	@Override
	public void clearTf() {
		tfNo.setText("");
		tfName.setText("");
		pfPasswd1.setText("");
		pfPasswd2.setText("");
		tfHireDate.setDate(new Date());
		setPic(getClass().getClassLoader().getResource("no-image.png").getPath());
		cmbDept.setSelectedIndex(-1);
		cmbTitle.setSelectedIndex(-1);
		cmbMgn.setSelectedIndex(-1);
		lblEqual.setText("");
	}

	public JComboBox<Department> getCmbDept() {
		return cmbDept;
	}

	public JComboBox<Title> getCmbTitle() {
		return cmbTitle;
	}

	public JComboBox<Employee> getCmbMgn() {
		return cmbMgn;
	}
	
	private byte[] getImage() {
		byte[] pic = null;
		File file = new File(picPath);
		try {
			InputStream is = new FileInputStream(file);
			pic = new byte[is.available()];
			is.read(pic);
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pic;
	}
	
}
