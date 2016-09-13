package board;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class checkAction extends ActionSupport implements Preparable,ModelDriven,DAOSqlMapper{
	private boardVO paramClass; //�Ķ���͸� ������ ��ü
	private SqlMapClient sqlMapper;
	private int currentPage;	//���� ������
	
	public String execute() throws Exception {
		System.out.println("viewAction checkAction()");
		// ���� ���� ��й�ȣ ��������.
		paramClass = (boardVO) sqlMapper.queryForObject("selectPassword",paramClass);

		// �Է��� ��й�ȣ�� Ʋ���� ERROR ����.
		if (paramClass == null)
			return ERROR;

		return SUCCESS;
	}
	
	public Object getModel() {	System.out.println("getModel"); return paramClass; 	}
	public void prepare() throws Exception {	paramClass = new boardVO();	}
	public void setSqlMapper(SqlMapClient sqlMapper) {		this.sqlMapper=sqlMapper;	}
	public int getCurrentPage() {	return currentPage;	}
	public void setCurrentPage(int currentPage) {	this.currentPage = currentPage;	}
}
