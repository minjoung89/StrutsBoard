package board;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.util.*;
import java.io.Reader;
import java.io.IOException;

import java.io.File;
import org.apache.commons.io.FileUtils;

public class writeAction extends ActionSupport implements Preparable,ModelDriven,DAOSqlMapper{

	private SqlMapClient sqlMapper; //SqlMapClient API를 사용하기 위한 sqlMapper 객체.

	private boardVO paramClass; //파라미터를 저장할 객체
	private int currentPage; //현재 페이지

	Calendar today = Calendar.getInstance(); //오늘 날짜 구하기.

	private File upload; //파일 객체
	private String uploadContentType; //컨텐츠 타입
	private String uploadFileName; //파일 이름
	private String fileUploadPath = "C:\\save\\"; //업로드 경로.

	public String form() throws Exception {
		System.out.println("writeAction form()");
		//등록 폼.
		return SUCCESS;
	}

	// 게시판 WRITE 액션
	public String execute() throws Exception {
		System.out.println("writeAction execute()");
		// 등록 쿼리 수행.
		sqlMapper.insert("insertBoard", paramClass);

		// 첨부파일을 선택했다면 파일을 업로드한다.
		if (upload != null) {
			System.out.println("업로드파일 있음");
			//등록한 글 번호 가져오기.
			paramClass = (boardVO) sqlMapper.queryForObject("selectLastNo");

			//실제 서버에 저장될 파일 이름과 확장자 설정.
			String file_name = "file_" + paramClass.getNo();
			String file_ext = uploadFileName.substring(
					uploadFileName.lastIndexOf('.') + 1,
					uploadFileName.length());

			//서버에 파일 저장.
			File destFile = new File(fileUploadPath + file_name + "."
					+ file_ext);
			FileUtils.copyFile(upload, destFile);

			//파일 정보 파라미터 설정.
			paramClass.setFile_orgname(uploadFileName);		//원래 파일 이름
			paramClass.setFile_savname(file_name + "." + file_ext);	//서버에 저장한 파일 이름

			//파일 정보 업데이트.
			sqlMapper.update("updateFile", paramClass);
		}

		return SUCCESS;
	}

	public void prepare() throws Exception { paramClass = new boardVO();	}
	public Object getModel() {	System.out.println("getModel"); return paramClass; 	}
	public void setUpload(File upload) {	this.upload = upload;	}
	public void setUploadContentType(String uploadContentType) {	this.uploadContentType = uploadContentType;	}
	public void setUploadFileName(String uploadFileName) {	this.uploadFileName = uploadFileName;	}
	public void setFileUploadPath(String fileUploadPath) {	this.fileUploadPath = fileUploadPath;	}
	public void setCurrentPage(int currentPage) {	this.currentPage = currentPage;	}
	public int getCurrentPage() {	return currentPage;	}
	public void setSqlMapper(SqlMapClient sqlMapper) { this.sqlMapper=sqlMapper;	}
}
