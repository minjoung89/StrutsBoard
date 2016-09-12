package board;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.ibatis.sqlmap.client.SqlMapClient;

import java.io.File;

public class deleteAction extends ActionSupport implements Preparable,ModelDriven,DAOSqlMapper{
	private SqlMapClient sqlMapper;
	private boardVO paramClass; //파라미터를 저장할 객체
	private int currentPage;	//현재 페이지
	private String fileUploadPath = "E:\\save\\";


	// 게시글 글 삭제
	public String execute() throws Exception {
		
		// 해당 번호의 글을 가져온다.
		paramClass = (boardVO) sqlMapper.queryForObject("selectOne", paramClass.getNo());

		//서버 파일 삭제
		File deleteFile = new File(fileUploadPath + paramClass.getFile_savname());
		deleteFile.delete();
				
		// 삭제 쿼리 수행.
		sqlMapper.update("deleteBoard", paramClass);

		return SUCCESS;
	}

	public int getCurrentPage() {	return currentPage;	}
	public void setCurrentPage(int currentPage) {	this.currentPage = currentPage;	}
	public void setSqlMapper(SqlMapClient sqlMapper) {		this.sqlMapper=sqlMapper;	}
	public Object getModel() {		return paramClass;	}
	public void prepare() throws Exception { paramClass=new boardVO();	}
}

