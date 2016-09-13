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

	public void init() { //서버 시작시 제일 먼저 오직 1번만 실행되는 메서드
		System.out.println("DAOInterceptor init()");
		Reader reader;
		try {
			reader = Resources.getResourceAsReader("sqlMapConfig.xml");  
					// java Resources폴더 내의 sqlMapConfig.xml 파일의 resource 내용을 읽어옴
			sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);	
					// /sql/boardSQL.xml의 sql문을 로딩
			reader.close();
		} catch (IOException e) {		e.printStackTrace();		} 
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		System.out.println("DAOInterceptor intercept()");
		Object action=invocation.getAction();   // 연결정보(invocation)의 action(listAction)을 가져옴
		if(action instanceof DAOSqlMapper){		// action이 DAOSqlMapper를 구현(상속)하고 있는지 확인
			DAOSqlMapper daoSqlMapper=(DAOSqlMapper) action;  // daoSqlMapper는 action을 DAOSqlMapper로 형변환한 형태를 지님
			daoSqlMapper.setSqlMapper(sqlMapper);  //listAction의 setSqlMapper()를 호출 (즉, listAction에 sqlMapper를 빌려줌)
		}
		return invocation.invoke();  // 컨트롤러에 연결정보(invocation)을 돌려줌
	}	
}
