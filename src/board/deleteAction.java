package board;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.ibatis.sqlmap.client.SqlMapClient;

import java.io.File;

public class deleteAction extends ActionSupport implements Preparable,ModelDriven,DAOSqlMapper{
	private SqlMapClient sqlMapper;
	private boardVO paramClass; //�Ķ���͸� ������ ��ü
	private int currentPage;	//���� ������
	private String fileUploadPath = "E:\\save\\";


	// �Խñ� �� ����
	public String execute() throws Exception {
		
		// �ش� ��ȣ�� ���� �����´�.
		paramClass = (boardVO) sqlMapper.queryForObject("selectOne", paramClass.getNo());

		//���� ���� ����
		File deleteFile = new File(fileUploadPath + paramClass.getFile_savname());
		deleteFile.delete();
				
		// ���� ���� ����.
		sqlMapper.update("deleteBoard", paramClass);

		return SUCCESS;
	}

	public int getCurrentPage() {	return currentPage;	}
	public void setCurrentPage(int currentPage) {	this.currentPage = currentPage;	}
	public void setSqlMapper(SqlMapClient sqlMapper) {		this.sqlMapper=sqlMapper;	}
	public Object getModel() {		return paramClass;	}
	public void prepare() throws Exception { paramClass=new boardVO();	}
}

