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

	private boardVO paramClass; //�Ķ���͸� ������ ��ü

	private int currentPage;

	private String fileUploadPath = "E:\\save\\";

	private InputStream inputStream;
	private String contentDisposition;
	private long contentLength;


	// �󼼺���
	public String execute() throws Exception {
		System.out.println("viewAction execute()");
		System.out.println(paramClass.getNo()+"�� �� ��������");
		// �ش� ���� ��ȸ�� +1.
		sqlMapper.update("updateReadHit", paramClass);
		
		// �ش� ��ȣ�� ���� �����´�.
		paramClass = (boardVO) sqlMapper.queryForObject("selectOne", paramClass.getNo());
		return SUCCESS;
	}

	// ÷�� ���� �ٿ�ε�
	public String download() throws Exception {
		// �ش� ��ȣ�� ���� ������ �����´�.
		paramClass = (boardVO) sqlMapper.queryForObject("selectOne",  paramClass.getNo());

		// ���� ��ο� ���ϸ��� file ��ü�� �ִ´�.
		File fileInfo = new File(fileUploadPath + paramClass.getFile_savname());

		// �ٿ�ε� ���� ���� ����.
		setContentLength(fileInfo.length());
		setContentDisposition("attachment;filename="
				+ URLEncoder.encode(paramClass.getFile_orgname(), "UTF-8"));
		setInputStream(new FileInputStream(fileUploadPath
				+ paramClass.getFile_savname()));

		return SUCCESS;
	}

	// ��й�ȣ üũ ��
	public String checkForm() throws Exception {
		
		return SUCCESS;
	}

	// ��й�ȣ üũ �׼�
	public String checkAction() throws Exception {
		System.out.println(paramClass.getNo());
		// ���� ���� ��й�ȣ ��������.
		paramClass = (boardVO) sqlMapper.queryForObject("selectPassword",paramClass);

		// �Է��� ��й�ȣ�� Ʋ���� ERROR ����.
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

