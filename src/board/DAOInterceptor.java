package board;

import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class DAOInterceptor implements Interceptor{
	public static SqlMapClient sqlMapper;	//SqlMapClient API를 사용하기 위한 sqlMapper 객체.
	public void destroy() {}

	public void init() {
		Reader reader;
		try {
			reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);	
			reader.close();
		} catch (IOException e) {		e.printStackTrace();		} 
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		System.out.println("DAOInterceptor");
		Object action=invocation.getAction();
		if(action instanceof DAOSqlMapper){
			System.out.println("오버라이딩");
			System.out.println(sqlMapper);
			DAOSqlMapper daoSqlMapper=(DAOSqlMapper) action;
			daoSqlMapper.setSqlMapper(sqlMapper);
			
		}
		return invocation.invoke();
	}
	
}
