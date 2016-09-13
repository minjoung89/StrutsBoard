package board;

import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class DAOInterceptor implements Interceptor{
	public static SqlMapClient sqlMapper;	//SqlMapClient API�� ����ϱ� ���� sqlMapper ��ü.
	public void destroy() {}

	public void init() { //���� ���۽� ���� ���� ���� 1���� ����Ǵ� �޼���
		System.out.println("DAOInterceptor init()");
		Reader reader;
		try {
			reader = Resources.getResourceAsReader("sqlMapConfig.xml");  
					// java Resources���� ���� sqlMapConfig.xml ������ resource ������ �о��
			sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);	
					// /sql/boardSQL.xml�� sql���� �ε�
			reader.close();
		} catch (IOException e) {		e.printStackTrace();		} 
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		System.out.println("DAOInterceptor intercept()");
		Object action=invocation.getAction();   // ��������(invocation)�� action(listAction)�� ������
		if(action instanceof DAOSqlMapper){		// action�� DAOSqlMapper�� ����(���)�ϰ� �ִ��� Ȯ��
			DAOSqlMapper daoSqlMapper=(DAOSqlMapper) action;  // daoSqlMapper�� action�� DAOSqlMapper�� ����ȯ�� ���¸� ����
			daoSqlMapper.setSqlMapper(sqlMapper);  //listAction�� setSqlMapper()�� ȣ�� (��, listAction�� sqlMapper�� ������)
		}
		return invocation.invoke();  // ��Ʈ�ѷ��� ��������(invocation)�� ������
	}	
}
