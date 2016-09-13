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

	private boardVO paramClass; //�Ķ���͸� ������ ��ü

	private int currentPage;	//���� ������
	private String old_file;

	private File upload; //���� ��ü
	private String uploadContentType; //������ Ÿ��
	private String uploadFileName; //���� �̸�
	private String fileUploadPath = "E:\\save\\"; //���ε� ���.

	// �Խñ� ����
	public String execute() throws Exception {
		System.out.println("modifyAction execute()");
		// �ϴ� �׸� �����Ѵ�.
		sqlMapper.update("updateBoard", paramClass);

		// ������ ������ ���ε� �Ǿ��ٸ� ������ ���ε��ϰ� DB�� file �׸��� ������.
		if (upload != null) {
			
			//���� ������ ����� ���� �̸��� Ȯ���� ����.
			String file_name = "file_" + paramClass.getNo();
		    String file_ext = uploadFileName.substring(uploadFileName.lastIndexOf('.')+1,uploadFileName.length());
			
			//���� ���� ����
			File deleteFile = new File(fileUploadPath + old_file);
			deleteFile.delete();
			
			//�� ���� ���ε�
			File destFile = new File(fileUploadPath + file_name + "." + file_ext);
			FileUtils.copyFile(upload, destFile);
			
			//���� ���� �Ķ���� ����.
			paramClass.setFile_orgname(uploadFileName);
			paramClass.setFile_savname(file_name + "." + file_ext);
			
			//���� ���� ������Ʈ.
			sqlMapper.update("updateFile", paramClass);
		}

		// ������ ������ view �������� �̵�.
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

