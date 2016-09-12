package board;

import com.opensymphony.xwork2.ActionSupport;
import com.ibatis.sqlmap.client.SqlMapClient; 
import java.util.*;
import board.pagingAction;

public class listAction extends ActionSupport implements DAOSqlMapper{
	
	private SqlMapClient sqlMapper;	//SqlMapClient API를 사용하기 위한 sqlMapper 객체.

	private List<boardVO> list;
	
	private int currentPage = 1;	//현재 페이지
	private int totalCount; 		// 총 게시물의 수
	private int blockCount = 10;	// 한 페이지의  게시물의 수
	private int blockPage = 5; 	// 한 화면에 보여줄 페이지 수
	private String pagingHtml; 	//페이징을 구현한 HTML
	private pagingAction page; 	// 페이징 클래스
	

	// 게시판 LIST 액션
	public String execute() throws Exception {

		// 모든 글을 가져와 list에 넣는다.
		list = sqlMapper.queryForList("selectAll");

		totalCount = list.size(); // 전체 글 갯수를 구한다.
		page = new pagingAction(currentPage, totalCount, blockCount, blockPage); // pagingAction 객체 생성.
		pagingHtml = page.getPagingHtml().toString(); // 페이지 HTML 생성.

		// 현재 페이지에서 보여줄 마지막 글의 번호 설정.
		int lastCount = totalCount;

		// 현재 페이지의 마지막 글의 번호가 전체의 마지막 글 번호보다 작으면 lastCount를 +1 번호로 설정.
		if (page.getEndCount() < totalCount)
			lastCount = page.getEndCount() + 1;

		// 전체 리스트에서 현재 페이지만큼의 리스트만 가져온다.
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

