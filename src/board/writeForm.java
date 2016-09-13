package board;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class writeForm extends ActionSupport implements Preparable,ModelDriven{
	private boardVO paramClass; //파라미터를 저장할 객체
	private int currentPage;
	public String execute() throws Exception {
		System.out.println(paramClass.getNo()+">>>>글번호");
		//등록 폼.
		return SUCCESS;
	}

	public Object getModel() {		return paramClass;	}
	public void prepare() throws Exception {		paramClass=new boardVO();	}
	public void setCurrentPage(int currentPage) {	this.currentPage = currentPage;	}
	public int getCurrentPage() {	return currentPage;	}
}
