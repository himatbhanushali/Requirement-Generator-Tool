package RGT.common.interfaces;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import RGT.common.Option;
import RGT.common.BusinessProcess;
import RGT.common.Step;
import RGT.common.Action;

public interface ISystemFacadeController {
	
	BusinessProcess userOptionInput(String aVerbNounPhrase);
	Step userOptionInput(String verbNounPhrase, BusinessProcess aParent);
	Action userOptionInput(String verbNounPhrase, Step aParentStep);
	public boolean updateChanges(String anOldValue, String aNewValue);
	public boolean deleteSelected(String aSelectedNodeValue);
	public BusinessProcess createBusinessProcess(IVerbNounPhrase aVerbNounPhrase);
	public Step createStep(IVerbNounPhrase aVerbNounPhrase, IBusinessProcess aParentBusinessProcess);
	public Action createAction(IVerbNounPhrase aVerbNounPhrase, IStep aParentStep);
	public Set<IBusinessProcess> getUpdatedBusinessProcesses();
	public List<String> addRequirement(String businessProcess, String requirement, int priority);
	public List<String> getRequirements(String businessProcess);
	public boolean saveRequirements(List<String> aListOfRequirements, File fileToSave) throws IOException;
}
