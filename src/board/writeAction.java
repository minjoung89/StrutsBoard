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

	private SqlMapClient sqlMapper; //SqlMapClient API�� ����ϱ� ���� sqlMapper ��ü.

	private boardVO paramClass; //�Ķ���͸� ������ ��ü
	private int currentPage; //���� ������

	Calendar today = Calendar.getInstance(); //���� ��¥ ���ϱ�.

	private File upload; //���� ��ü
	private String uploadContentType; //������ Ÿ��
	private String uploadFileName; //���� �̸�
	private String fileUploadPath = "C:\\save\\"; //���ε� ���.

	public String form() throws Exception {
		System.out.println("writeAction form()");
		//��� ��.
		return SUCCESS;
	}

	// �Խ��� WRITE �׼�
	public String execute() throws Exception {
		System.out.println("writeAction execute()");
		// ��� ���� ����.
		sqlMapper.insert("insertBoard", paramClass);

		// ÷�������� �����ߴٸ� ������ ���ε��Ѵ�.
		if (upload != null) {
			System.out.println("���ε����� ����");
			//����� �� ��ȣ ��������.
			paramClass = (boardVO) sqlMapper.queryForObject("selectLastNo");

			//���� ������ ����� ���� �̸��� Ȯ���� ����.
			String file_name = "file_" + paramClass.getNo();
			String file_ext = uploadFileName.substring(
					uploadFileName.lastIndexOf('.') + 1,
					uploadFileName.length());

			//������ ���� ����.
			File destFile = new File(fileUploadPath + file_name + "."
					+ file_ext);
			FileUtils.copyFile(upload, destFile);

			//���� ���� �Ķ���� ����.
			paramClass.setFile_orgname(uploadFileName);		//���� ���� �̸�
			paramClass.setFile_savname(file_name + "." + file_ext);	//������ ������ ���� �̸�

			//���� ���� ������Ʈ.
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
