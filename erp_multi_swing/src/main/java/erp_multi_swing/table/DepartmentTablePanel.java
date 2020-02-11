
package erp_multi_swing.table;

import javax.swing.SwingConstants;

import erp_multi_common.dto.Department;

@SuppressWarnings("serial")
public class DepartmentTablePanel extends AbstractTablePanel<Department> {
	
	public DepartmentTablePanel() {
		super("부서 리스트");
	}

	public Department getSelectedItem(){
		int row = getSelectedRowIndex();
		int deptNo=	(int) model.getValueAt(row, 0);
		String deptName = (String) model.getValueAt(row, 1);
		int floor = (int) model.getValueAt(row, 2);
		return new Department(deptNo, deptName, floor);
	}
	
	public void updateRow(Department item, int row) {
		model.setValueAt(item.getDeptNo(), row, 0);
		model.setValueAt(item.getDeptName(), row, 1);
		model.setValueAt(item.getFloor(), row, 2);
	}

	protected Object[] toArray(Department item) {
		return new Object[] {item.getDeptNo(), item.getDeptName(), item.getFloor()};
	}
	
	@Override
	protected void setWidthAlign() {
		tableCellAlign(SwingConstants.CENTER, 0, 1, 2);
        tableSetWidth(100, 200, 100);    		
	}

	@Override
	protected String[] getColNames() {
		return new String[] { "부서 코드", "부서 명", "위치"};
	}
	
}
