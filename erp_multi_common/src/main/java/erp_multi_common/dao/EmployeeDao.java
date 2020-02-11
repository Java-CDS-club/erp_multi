package erp_multi_common.dao;

import java.util.List;

import erp_multi_common.dto.Department;
import erp_multi_common.dto.Employee;

public interface EmployeeDao {
	Employee selectEmployeeByNo(Employee emp);
	List<Employee> selectEmployeeByAll();
	List<Employee> selectEmployeeGroupByDno(Department dept);
	int insertEmployee(Employee emp);
	int updateEmployee(Employee emp);
	int deleteEmployee(Employee emp);
	
	Employee loginProcess(Employee emp);
	List<Employee> selectEmployeeByDept(Department dept);
}
