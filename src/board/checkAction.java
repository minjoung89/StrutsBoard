package board;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class checkAction extends ActionSupport implements Preparable,ModelDriven,DAOSqlMapper{
	private boardVO paramClass; //파라미터를 저장할 객체
	private SqlMapClient sqlMapper;
	private int currentPage;	//현재 페이지
	
	public String execute() throws Exception {
		System.out.println("viewAction checkAction()");
		// 현재 글의 비밀번호 가져오기.
		paramClass = (boardVO) sqlMapper.queryForObject("selectPassword",paramClass);

		// 입력한 비밀번호가 틀리면 ERROR 리턴.
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
