package board;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class fileDownloadAction extends ActionSupport implements Preparable,ModelDriven,DAOSqlMapper {
	private SqlMapClient sqlMapper;
	private boardVO paramClass; //파라미터를 저장할 객체
	private int currentPage;
	private String fileUploadPath = "C:\\save\\";
	private InputStream inputStream;
	private String contentDisposition;
	private long contentLength;
	
	public String execute() throws Exception {
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
	public Object getModel() {	System.out.println("getModel"); return paramClass; 	}
	public void prepare() throws Exception {	paramClass = new boardVO();	}
	public void setSqlMapper(SqlMapClient sqlMapper) {		this.sqlMapper=sqlMapper;	}
	public void setInputStream(InputStream inputStream) {	this.inputStream = inputStream;	}
	public void setFileUploadPath(String fileUploadPath) {	this.fileUploadPath = fileUploadPath;	}	
	public void setContentDisposition(String contentDisposition) {	this.contentDisposition = contentDisposition;	}
	public void setContentLength(long contentLength) {	this.contentLength = contentLength;	}
	public void setCurrentPage(int currentPage) {	this.currentPage = currentPage;	}
	public InputStream getInputStream() {	return inputStream;	}
	public String getFileUploadPath() {	return fileUploadPath;	}
	public boardVO getParamClass(){	System.out.println("getParamClass");  return paramClass;	}
	public String getContentDisposition() {	return contentDisposition;	}
	public long getContentLength() {	return contentLength;	}
	public int getCurrentPage() {	return currentPage;	}
}
