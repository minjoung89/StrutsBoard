package board;

import com.opensymphony.xwork2.ActionSupport;
import com.ibatis.sqlmap.client.SqlMapClient; 
import java.util.*;
import board.pagingAction;

public class listAction extends ActionSupport implements DAOSqlMapper{
	
	private SqlMapClient sqlMapper;	//SqlMapClient API�� ����ϱ� ���� sqlMapper ��ü.

	private List<boardVO> list;
	
	private int currentPage = 1;	//���� ������
	private int totalCount; 		// �� �Խù��� ��
	private int blockCount = 10;	// �� ��������  �Խù��� ��
	private int blockPage = 5; 	// �� ȭ�鿡 ������ ������ ��
	private String pagingHtml; 	//����¡�� ������ HTML
	private pagingAction page; 	// ����¡ Ŭ����
	

	// �Խ��� LIST �׼�
	public String execute() throws Exception {

		// ��� ���� ������ list�� �ִ´�.
		list = sqlMapper.queryForList("selectAll");

		totalCount = list.size(); // ��ü �� ������ ���Ѵ�.
		page = new pagingAction(currentPage, totalCount, blockCount, blockPage); // pagingAction ��ü ����.
		pagingHtml = page.getPagingHtml().toString(); // ������ HTML ����.

		// ���� ���������� ������ ������ ���� ��ȣ ����.
		int lastCount = totalCount;

		// ���� �������� ������ ���� ��ȣ�� ��ü�� ������ �� ��ȣ���� ������ lastCount�� +1 ��ȣ�� ����.
		if (page.getEndCount() < totalCount)
			lastCount = page.getEndCount() + 1;

		// ��ü ����Ʈ���� ���� ��������ŭ�� ����Ʈ�� �����´�.
		list = list.subList(page.getStartCount(), lastCount);

		return SUCCESS;
	}
	
	public List<boardVO> getList() {	return list;	}

	public int getCurrentPage() {	return currentPage;	}

	public void setCurrentPage(int currentPage) {	this.currentPage = currentPage;	}

	public int getTotalCount() {	return totalCount;	}

	public int getBlockCount() {	return blockCount;	}

	public int getBlockPage() {	return blockPage;	}

	public String getPagingHtml() {	return pagingHtml;	}

	public pagingAction getPage() {	return page;	}

	public void setSqlMapper(SqlMapClient sqlMapper) {	System.out.println(sqlMapper); 	this.sqlMapper=sqlMapper;	}

}

