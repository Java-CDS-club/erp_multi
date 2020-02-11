package erp_multi_common.dao;

import java.util.List;

import erp_multi_common.dto.Department;

public interface DepartmentDao {
	Department selectDepartmentByNo(Department dept);
	List<Department> selectDepartmentByAll();
	
	int insertDepartment(Department dept);
	int updateDepartment(Department dept);
	int deleteDepartment(Department dept);
}
