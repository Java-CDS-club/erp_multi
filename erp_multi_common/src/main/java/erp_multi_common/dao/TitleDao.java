package erp_multi_common.dao;

import java.util.List;

import erp_multi_common.dto.Title;

public interface TitleDao {
	Title selectTitleByNo(Title title);
	List<Title> selectTitleByAll();
	
	int insertTitle(Title title);
	int updateTitle(Title title);
	int deleteTitle(Title title);
}
