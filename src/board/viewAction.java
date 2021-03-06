package board;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.ibatis.sqlmap.client.SqlMapClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.net.URLEncoder;

public class viewAction extends ActionSupport implements Preparable,ModelDriven,DAOSqlMapper{
	private SqlMapClient sqlMapper;
	private boardVO paramClass; //파라미터를 저장할 객체
	private int currentPage;

	// 상세보기
	public String execute() throws Exception {
		System.out.println("viewAction execute()");
		// 해당 글의 조회수 +1.
		sqlMapper.update("updateReadHit", paramClass);
		
		// 해당 번호의 글을 가져온다.
		paramClass = (boardVO) sqlMapper.queryForObject("selectOne", paramClass.getNo()); 
		return SUCCESS;
	}
	
	public Object getModel() {	System.out.println("getModel"); return paramClass; 	}
	public void prepare() throws Exception {	paramClass = new boardVO();	}
	public void setSqlMapper(SqlMapClient sqlMapper) {		this.sqlMapper=sqlMapper;	}
	public void setCurrentPage(int currentPage) {	this.currentPage = currentPage;	}
	public boardVO getParamClass(){	System.out.println("getParamClass");  return paramClass;	}
	public int getCurrentPage() {	return currentPage;	}
}

