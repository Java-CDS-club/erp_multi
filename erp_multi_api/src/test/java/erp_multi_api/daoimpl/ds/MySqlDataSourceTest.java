package erp_multi_api.daoimpl.ds;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import erp_multi_api.util.LogUtil;

public class MySqlDataSourceTest {

	@Test
	public void testGetConnection() {
		try (Connection con = MySqlDataSource.getConnection();){
			LogUtil.prnLog(con);
	        Assert.assertNotNull(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
