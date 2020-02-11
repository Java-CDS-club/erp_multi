package erp_multi_swing.service;

import java.util.List;

import erp_multi_api.daoimpl.DepartmentDaoImpl;
import erp_multi_api.daoimpl.EmployeeDaoImpl;
import erp_multi_api.daoimpl.TitleDaoImpl;
import erp_multi_common.dao.DepartmentDao;
import erp_multi_common.dao.EmployeeDao;
import erp_multi_common.dao.TitleDao;
import erp_multi_common.dto.Department;
import erp_multi_common.dto.Employee;
import erp_multi_common.dto.Title;

public class EmployeeService {
	private TitleDao titleDao;
	private EmployeeDao empDao;
	private DepartmentDao deptDao;
	
	public EmployeeService() {
		titleDao = TitleDaoImpl.getInstance();
		empDao = EmployeeDaoImpl.getInstance();
		deptDao = DepartmentDaoImpl.getInstance();
	}

	public List<Department> showDepartments() {
		return deptDao.selectDepartmentByAll();
	}

	public List<Employee> showEmployees() {
		return empDao.selectEmployeeByAll();
	}

	public void modifyEmployee(Employee emp) {
		empDao.updateEmployee(emp);
	}

	public void addEmployee(Employee emp) {
		empDao.insertEmployee(emp);
	}

	public void removeEmployee(Employee emp) {
		empDao.deleteEmployee(emp);
	}

	public List<Title> showTitles() {
		return titleDao.selectTitleByAll();
	}

	public List<Employee> showManagers(Department dept) {
		return empDao.selectEmployeeByDept(dept);
	}
	
	
}
