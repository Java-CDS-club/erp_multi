package erp_multi_swing.service;

import java.sql.SQLException;
import java.util.List;

import erp_multi_api.daoimpl.DepartmentDaoImpl;
import erp_multi_api.daoimpl.EmployeeDaoImpl;
import erp_multi_common.dao.DepartmentDao;
import erp_multi_common.dao.EmployeeDao;
import erp_multi_common.dto.Department;
import erp_multi_common.dto.Employee;

public class DepartmentService {
	private DepartmentDao deptDao;
	private EmployeeDao empDao;

	public DepartmentService() {
		deptDao = DepartmentDaoImpl.getInstance();
		empDao = EmployeeDaoImpl.getInstance();
	}

	public List<Employee> showEmployeeGroupByDno(Department dept) {
		return empDao.selectEmployeeGroupByDno(dept);
	}

	public List<Department> showDepartments() {
		return deptDao.selectDepartmentByAll();
	}

	public void addDepartment(Department newDept) throws SQLException {
		deptDao.insertDepartment(newDept);
	}

	public void modifyDepartment(Department dept) throws SQLException {
		deptDao.updateDepartment(dept);
	}

	public void removeDepartment(Department dept) throws SQLException {
		deptDao.deleteDepartment(dept);
	}

}
