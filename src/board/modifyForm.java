package board;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class modifyForm extends ActionSupport implements Preparable,ModelDriven,DAOSqlMapper {
	private SqlMapClient sqlMapper;
	private boardVO paramClass; //파라미터를 저장할 객체
	private int currentPage;
	public String execute() throws Exception {
		System.out.println(paramClass.getNo()+">>>>글번호");
		// 해당 번호의 글을 가져온다.
		paramClass = (boardVO) sqlMapper.queryForObject("selectOne", paramClass.getNo()); 
		return SUCCESS;
	}

	public Object getModel() {	System.out.println("getModel"); return paramClass; 	}
	public void prepare() throws Exception {	paramClass = new boardVO();	}
	public void setSqlMapper(SqlMapClient sqlMapper) {		this.sqlMapper=sqlMapper;	}
	public boardVO getParamClass(){	System.out.println("getParamClass");  return paramClass;	}
	public void setCurrentPage(int currentPage) {	this.currentPage = currentPage;	}
	public int getCurrentPage() {	return currentPage;	}
}
