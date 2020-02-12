package erp_multi_swing.table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingConstants;

import erp_multi_common.dto.Department;
import erp_multi_common.dto.Employee;
import erp_multi_common.dto.Title;

@SuppressWarnings("serial")
public class EmployeeTablePanel extends AbstractTablePanel<Employee> {
	private Map<Integer, byte[]> picMaps = new HashMap<>();
	
	public EmployeeTablePanel() {
		super("사원 정보");
	}

	@Override
	protected void setWidthAlign() {
		//  empno, empname, title, manager, salary, dno, hire_date
		tableSetWidth(65, 100, 70, 120, 120, 100, 110);
		tableCellAlign(SwingConstants.CENTER, 0, 1, 2, 3, 5, 6);	
		tableCellAlign(SwingConstants.RIGHT, 4);	
	}

	@Override
	protected String[] getColNames() {
		return new String[] {"사원번호", "사원명", "직책", "직속상사", "급여", "부서", "입사일"};
	}

	@Override
	protected Object[] toArray(Employee item) {
		String manager;
		if (item.getManager().getEmpName()==null) {
			manager = "";
		}else {
			manager = String.format("%s(%d)", item.getManager().getEmpName(), item.getManager().getEmpNo());
		}
		picMaps.put(item.getEmpNo(), item.getPic());
		return new Object[] {
			item.getEmpNo(),
			item.getEmpName(),
			String.format("%s(%d)", item.getTitle().getTitleName(), item.getTitle().getTitleNo()),
			manager, //직속상사명(사원번호)
			String.format("%,d", item.getSalary()),			//천단위구분기호
			String.format("%s(%d)", item.getDept().getDeptName(), item.getDept().getDeptNo()),	//부서명(부서번호)
			String.format("%tF", item.getHireDate())
		};
	}

	@Override
	public void updateRow(Employee item, int updateIdx) {
		model.setValueAt(item.getEmpNo(), updateIdx, 0);//사원번호
		model.setValueAt(item.getEmpName(), updateIdx, 1);//사원명
		model.setValueAt(String.format("%s(%d)", item.getTitle().getTitleName(), item.getTitle().getTitleNo()), updateIdx, 2);//직책(직책번호)
		String manager = null;
		if (item.getManager().getEmpName()==null) {
			manager = "";
		}else {
			manager = String.format("%s(%d)", item.getManager().getEmpName(), item.getManager().getEmpNo());
		}
		model.setValueAt(manager, updateIdx, 3);//직속상사번호
		model.setValueAt(String.format("%,d",item.getSalary()), updateIdx, 4);//급여
		model.setValueAt(String.format("%s(%d)", item.getDept().getDeptName(), item.getDept().getDeptNo()), updateIdx, 5);//소속부서번호	
		model.setValueAt(String.format("%tF", item.getHireDate()), updateIdx, 6);
		picMaps.replace(item.getEmpNo(), item.getPic());
	}

	@Override
	public Employee getSelectedItem() {
		int selectedIdx = getSelectedRowIndex();
		int empNo = (int) model.getValueAt(selectedIdx, 0);
		String empName = (String) model.getValueAt(selectedIdx, 1);
		String titleInfo = (String) model.getValueAt(selectedIdx, 2);
		int titleNO = Integer.parseInt(titleInfo.substring(titleInfo.indexOf("(")+1, titleInfo.length()-1));
		Title title = new Title(titleNO);
		
		String managerStr = (String) model.getValueAt(selectedIdx, 3);
		int managerNO = Integer.parseInt(managerStr.substring(managerStr.indexOf("(")+1, managerStr.length()-1));
		Employee manager = new Employee(managerNO);
		
		String salaryStr = (String) model.getValueAt(selectedIdx, 4);
		int salary = Integer.parseInt(salaryStr.replace(",", ""));
		
		String deptInfo = (String) model.getValueAt(selectedIdx, 5);
		int deptNo = Integer.parseInt(deptInfo.substring(deptInfo.indexOf("(")+1, deptInfo.length()-1));
		Department dept = new Department(deptNo);
		String hire= (String)model.getValueAt(selectedIdx, 6);
		Date hireDate = null;
		try {
			hireDate = new SimpleDateFormat("yyyy-MM-dd").parse(hire);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Employee emp = new Employee(empNo, empName, title, manager, salary, dept, hireDate);
		emp.setPic(picMaps.get(emp.getEmpNo()));
		return emp;
	}

	@Override
	public void removeRow() {
		super.removeRow();
		int empNo = (int) model.getValueAt(getSelectedRowIndex(), 0);
		picMaps.remove(empNo);
	}
	
	

}