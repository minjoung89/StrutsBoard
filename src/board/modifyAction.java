package board;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.Reader;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class modifyAction extends ActionSupport implements Preparable,ModelDriven,DAOSqlMapper{
	private SqlMapClient sqlMapper;

	private boardVO paramClass; //파라미터를 저장할 객체

	private int currentPage;	//현재 페이지
	private String old_file;

	private File upload; //파일 객체
	private String uploadContentType; //컨텐츠 타입
	private String uploadFileName; //파일 이름
	private String fileUploadPath = "E:\\save\\"; //업로드 경로.

	// 게시글 수정
	public String execute() throws Exception {
		System.out.println("modifyAction execute()");
		// 일단 항목만 수정한다.
		sqlMapper.update("updateBoard", paramClass);

		// 수정할 파일이 업로드 되었다면 파일을 업로드하고 DB의 file 항목을 수정함.
		if (upload != null) {
			
			//실제 서버에 저장될 파일 이름과 확장자 설정.
			String file_name = "file_" + paramClass.getNo();
		    String file_ext = uploadFileName.substring(uploadFileName.lastIndexOf('.')+1,uploadFileName.length());
			
			//이전 파일 삭제
			File deleteFile = new File(fileUploadPath + old_file);
			deleteFile.delete();
			
			//새 파일 업로드
			File destFile = new File(fileUploadPath + file_name + "." + file_ext);
			FileUtils.copyFile(upload, destFile);
			
			//파일 정보 파라미터 설정.
			paramClass.setFile_orgname(uploadFileName);
			paramClass.setFile_savname(file_name + "." + file_ext);
			
			//파일 정보 업데이트.
			sqlMapper.update("updateFile", paramClass);
		}

		// 수정이 끝나면 view 페이지로 이동.
		paramClass = (boardVO) sqlMapper.queryForObject("selectOne", paramClass.getNo());

		return SUCCESS;
	}

	public void setUpload(File upload) {		this.upload = upload;	}
	public void setUploadContentType(String uploadContentType) {	this.uploadContentType = uploadContentType;	}
	public void setUploadFileName(String uploadFileName) {		this.uploadFileName = uploadFileName;	}
	public void setFileUploadPath(String fileUploadPath) {		this.fileUploadPath = fileUploadPath;	}
	public void setOld_file(String old_file) {		this.old_file = old_file;	}
	public int getCurrentPage() {		return currentPage;	}
	public void setCurrentPage(int currentPage) {		this.currentPage = currentPage;	}	
	public void setSqlMapper(SqlMapClient sqlMapper) {		this.sqlMapper=sqlMapper;	}
	public Object getModel() {	System.out.println("getModel"); 	return paramClass;	}
	public void prepare() throws Exception { paramClass=new boardVO();	}
}

