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

	private String fileUploadPath = "E:\\save\\";

	private InputStream inputStream;
	private String contentDisposition;
	private long contentLength;


	// 상세보기
	public String execute() throws Exception {
		System.out.println("viewAction execute()");
		System.out.println(paramClass.getNo()+"번 글 가져오기");
		// 해당 글의 조회수 +1.
		sqlMapper.update("updateReadHit", paramClass);
		
		// 해당 번호의 글을 가져온다.
		paramClass = (boardVO) sqlMapper.queryForObject("selectOne", paramClass.getNo());
		return SUCCESS;
	}

	// 첨부 파일 다운로드
	public String download() throws Exception {
		// 해당 번호의 파일 정보를 가져온다.
		paramClass = (boardVO) sqlMapper.queryForObject("selectOne",  paramClass.getNo());

		// 파일 경로와 파일명을 file 객체에 넣는다.
		File fileInfo = new File(fileUploadPath + paramClass.getFile_savname());

		// 다운로드 파일 정보 설정.
		setContentLength(fileInfo.length());
		setContentDisposition("attachment;filename="
				+ URLEncoder.encode(paramClass.getFile_orgname(), "UTF-8"));
		setInputStream(new FileInputStream(fileUploadPath
				+ paramClass.getFile_savname()));

		return SUCCESS;
	}

	// 비밀번호 체크 폼
	public String checkForm() throws Exception {
		
		return SUCCESS;
	}

	// 비밀번호 체크 액션
	public String checkAction() throws Exception {
		System.out.println(paramClass.getNo());
		// 현재 글의 비밀번호 가져오기.
		paramClass = (boardVO) sqlMapper.queryForObject("selectPassword",paramClass);

		// 입력한 비밀번호가 틀리면 ERROR 리턴.
		if (paramClass == null)
			return ERROR;

		return SUCCESS;
	}
	public Object getModel() {	return paramClass; 	}
	public void prepare() throws Exception {	paramClass = new boardVO();	}
	public void setSqlMapper(SqlMapClient sqlMapper) {		this.sqlMapper=sqlMapper;	}
	
	public void setInputStream(InputStream inputStream) {	this.inputStream = inputStream;	}
	public void setFileUploadPath(String fileUploadPath) {	this.fileUploadPath = fileUploadPath;	}	
	public void setContentDisposition(String contentDisposition) {	this.contentDisposition = contentDisposition;	}
	public void setContentLength(long contentLength) {	this.contentLength = contentLength;	}
	public void setCurrentPage(int currentPage) {	this.currentPage = currentPage;	}
	
	public InputStream getInputStream() {	return inputStream;	}
	public String getFileUploadPath() {	return fileUploadPath;	}
	public boardVO getParamClass(){	return paramClass;	}
	public String getContentDisposition() {	return contentDisposition;	}
	public long getContentLength() {	return contentLength;	}
	public int getCurrentPage() {	return currentPage;	}
}

