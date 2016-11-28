package RGT.common.interfaces;

import java.util.List;

public interface IStep extends IVerbNounPhrase {
	IBusinessProcess getParentBusinessProcess();
	void setParentBusinessProcess(IBusinessProcess aParentBusinessProcess);
	
	boolean addAction(IAction anAction);
	boolean removeAction(IAction anAction);
	public List<IAction> getActions(); 
}
