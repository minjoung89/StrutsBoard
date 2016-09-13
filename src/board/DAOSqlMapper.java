package board;

import com.ibatis.sqlmap.client.SqlMapClient;

public interface DAOSqlMapper {
	public void setSqlMapper(SqlMapClient sqlMapper); // sqlMapper를 빌려오는 메서드
}
