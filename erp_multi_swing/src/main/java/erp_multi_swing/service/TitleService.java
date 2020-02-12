package erp_multi_swing.service;

import java.util.List;

import erp_multi_api.daoimpl.EmployeeDaoImpl;
import erp_multi_api.daoimpl.TitleDaoImpl;
import erp_multi_common.dao.EmployeeDao;
import erp_multi_common.dao.TitleDao;
import erp_multi_common.dto.Employee;
import erp_multi_common.dto.Title;

public class TitleService {
	private TitleDao titleDao;
	private EmployeeDao empDao;
	
	public TitleService() {
		titleDao = TitleDaoImpl.getInstance();
		empDao = EmployeeDaoImpl.getInstance();
	}


	public List<Title> showTitles() {
		return titleDao.selectTitleByAll();
	}


	public void modifyTitle(Title title) {
		titleDao.updateTitle(title);
	}


	public void addTitle(Title title) {
		titleDao.insertTitle(title);
	}


	public void removeTitle(Title title) {
		titleDao.deleteTitle(title);
	}


	public List<Employee> showEmployeeGroupByTitle(Title title) {
		return empDao.selectEmployeeGroupByTitle(title);
	}

}
