package RGT.common.interfaces;

public interface IAction extends IVerbNounPhrase {
	IBusinessProcess getParentBusinessProcess();
	void setParentBusinessProcess(IBusinessProcess aParentBusinessProcess);
	IStep getParentStep();
	void setParentStep(IStep aParentStep);
}
