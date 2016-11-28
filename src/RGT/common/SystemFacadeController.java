package RGT.common;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import RGT.common.interfaces.IAction;
import RGT.common.interfaces.IBusinessProcess;
import RGT.common.interfaces.IProcessor;
import RGT.common.interfaces.IStep;
import RGT.common.interfaces.IStorageMgr;
import RGT.common.interfaces.ISystemFacadeController;
import RGT.common.interfaces.IVerbNounPhrase;

public class SystemFacadeController implements ISystemFacadeController {
	
	private IStorageMgr storageMgr;
	private IProcessor processor;
	//private Set<IBusinessProcess> businessProcesses; 
	//private Set<IGenerate> generatedRequirement;
	
	
	public SystemFacadeController() {
		
		//businessProcesses = new HashSet<IBusinessProcess>();
		//generatedRequirement = new HashSet<IGenerate>();
		storageMgr  = new StorageMgr();
		processor = new Processor();
	}
	
	@Override
	public BusinessProcess userOptionInput(String aVerbNounPhrase) {
		return createBusinessProcess(new VerbNounPhrase(aVerbNounPhrase));
	}
	
	@Override
	public Step userOptionInput(String aVerbNounPhrase, BusinessProcess aParent) {
		return createStep(new VerbNounPhrase(aVerbNounPhrase), aParent);
	}

	@Override
	public Action userOptionInput(String aVerbNounPhrase, Step aParentStep) {
			return createAction(new VerbNounPhrase(aVerbNounPhrase), aParentStep);
	}

	@Override
	public boolean updateChanges(String anOldValue, String aNewValue) {
		return processor.updateChanges(anOldValue, aNewValue);
	}
	
	@Override
	public boolean deleteSelected(String aSelectedNodeValue) {
		return processor.deleteSelected(aSelectedNodeValue);
	}
	
	public BusinessProcess createBusinessProcess(IVerbNounPhrase aVerbNounPhrase) {
		
		return processor.createBusinessProcess(aVerbNounPhrase);
	}

	public Step createStep(IVerbNounPhrase aVerbNounPhrase, IBusinessProcess aParentBusinessProcess) {
		
		return processor.createStep(aVerbNounPhrase, aParentBusinessProcess);
	}

	public Action createAction(IVerbNounPhrase aVerbNounPhrase, IStep aParentStep) {
		
		return processor.createAction(aVerbNounPhrase, aParentStep);
	}

	public IBusinessProcess searchBusinessProcess(IBusinessProcess aBusinessProcess) {
		return processor.searchBusinessProcess(aBusinessProcess);
	}
	
	public IStep searchStep(IStep aStep) {
		return processor.searchStep(aStep);
	}
	
	public IAction searchAction(IAction anAction) {
		return processor.searchAction(anAction);
	}
	
	@Override
	public Set<IBusinessProcess> getUpdatedBusinessProcesses() {
		return processor.getUpdatedBusinessProcesses();
	}
	
	public List<String> addRequirement(String businessProcess, String requirement, int aPriority) {
		return processor.addRequirement(businessProcess, requirement, aPriority);
	}
	
	public List<String> getRequirements(String businessProcess) {
		return processor.getRequirements(businessProcess);
	}
	
	public boolean saveRequirements(List<String> aListOfRequirements, File fileToSave) throws IOException {
		return storageMgr.saveRequirements(aListOfRequirements, fileToSave);
	}
}
