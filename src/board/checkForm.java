package board;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class checkForm  extends ActionSupport implements Preparable,ModelDriven{
	boardVO paramClass;
	int currentPage;
	public String execute() throws Exception {
		System.out.println("checkFormAction execute()");
		return SUCCESS;
	}

	public Object getModel() {	return paramClass;	}
	public void prepare() throws Exception {	paramClass=new boardVO();	}
	public void setCurrentPage(int currentPage){ this.currentPage=currentPage; }
	public int getCurrentPage(){ return currentPage; }
	public boardVO getParamClass(){ return paramClass; }
}
