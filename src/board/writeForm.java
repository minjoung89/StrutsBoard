package board;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class writeForm extends ActionSupport implements Preparable,ModelDriven{
	private boardVO paramClass; //�Ķ���͸� ������ ��ü
	private int currentPage;
	public String execute() throws Exception {
		System.out.println(paramClass.getNo()+">>>>�۹�ȣ");
		//��� ��.
		return SUCCESS;
	}

	public Object getModel() {		return paramClass;	}
	public void prepare() throws Exception {		paramClass=new boardVO();	}
	public void setCurrentPage(int currentPage) {	this.currentPage = currentPage;	}
	public int getCurrentPage() {	return currentPage;	}
}
