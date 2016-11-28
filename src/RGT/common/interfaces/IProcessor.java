package RGT.common.interfaces;

import java.util.List;
import java.util.Set;

import RGT.common.BusinessProcess;
import RGT.common.Step;
import RGT.common.Action;

public interface IProcessor {

	public boolean updateChanges(String anOldValue, String aNewValue);
	public boolean deleteSelected(String aSelectedNodeValue);
	public BusinessProcess createBusinessProcess(IVerbNounPhrase aVerbNounPhrase);
	public Step createStep(IVerbNounPhrase aVerbNounPhrase, IBusinessProcess aParentBusinessProcess);
	public Action createAction(IVerbNounPhrase aVerbNounPhrase, IStep aParentStep);
	public Set<IBusinessProcess> getUpdatedBusinessProcesses();
	public List<String> addRequirement(String businessProcess, String requirement, int priority);
	
	public IBusinessProcess searchBusinessProcess(IBusinessProcess aBusinessProcess);
	public IStep searchStep(IStep aStep);
	public IAction searchAction(IAction anAction);
	public List<String> getRequirements(String businessProcess);
}
