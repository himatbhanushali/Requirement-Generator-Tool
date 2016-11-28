package RGT.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import RGT.common.interfaces.IAction;
import RGT.common.interfaces.IBusinessProcess;
import RGT.common.interfaces.IGenerate;
import RGT.common.interfaces.IProcessor;
import RGT.common.interfaces.IStep;
import RGT.common.interfaces.IVerbNounPhrase;
import RGT.views.MainGUI;

public class Processor implements IProcessor {

	private Set<IBusinessProcess> businessProcesses; 
	private Set<IGenerate> generatedRequirement;
	
	public Processor () {
		businessProcesses = new HashSet<IBusinessProcess>();
		generatedRequirement = new HashSet<IGenerate>();
	}
	
	@Override
	public boolean updateChanges(String anOldValue, String aNewValue) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) MainGUI.selectedBusinessProcessTree.getLastSelectedPathComponent();
		if (selectedNode != null) {
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();
			if(parent.isRoot()) {
				
				IBusinessProcess parentBusinessProcess = new BusinessProcess(parent.getUserObject().toString());
				parentBusinessProcess = searchBusinessProcess(parentBusinessProcess);
				IStep oldStep = new Step(anOldValue);
				oldStep.setParentBusinessProcess(parentBusinessProcess);
				oldStep = searchStep(oldStep);
				oldStep.setValue(aNewValue);
				return true;
			}
			else {
				DefaultMutableTreeNode superParent = (DefaultMutableTreeNode) parent.getParent();
				IBusinessProcess parentBusinessProcess = new BusinessProcess(superParent.getUserObject().toString());
				parentBusinessProcess = searchBusinessProcess(parentBusinessProcess);
				IStep parentStep = new Step(parent.getUserObject().toString());
				parentStep.setParentBusinessProcess(parentBusinessProcess);
				parentStep = searchStep(parentStep);
				for(IAction action : parentStep.getActions()){
					if(action.getValue().equals(anOldValue)) {
						action.setValue(aNewValue);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean deleteSelected(String aSelectedNodeValue) {
		
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) MainGUI.selectedBusinessProcessTree.getLastSelectedPathComponent();
		if (selectedNode != null) {
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();
			if(parent == null) {
				IBusinessProcess rootBusinessProcess = new BusinessProcess(selectedNode.getUserObject().toString());
				rootBusinessProcess = searchBusinessProcess(rootBusinessProcess);
				return businessProcesses.remove(rootBusinessProcess);
			}
			else if(parent.isRoot()) {
				
				IBusinessProcess parentBusinessProcess = new BusinessProcess(parent.getUserObject().toString());
				parentBusinessProcess = searchBusinessProcess(parentBusinessProcess);
				IStep aStepToDelete = new Step(aSelectedNodeValue);
				aStepToDelete.setParentBusinessProcess(parentBusinessProcess);
				aStepToDelete = searchStep(aStepToDelete);
				parentBusinessProcess.getSteps().remove(aStepToDelete);
				return true;
			}
			else {
				DefaultMutableTreeNode superParent = (DefaultMutableTreeNode) parent.getParent();
				IBusinessProcess parentBusinessProcess = new BusinessProcess(superParent.getUserObject().toString());
				parentBusinessProcess = searchBusinessProcess(parentBusinessProcess);
				IStep parentStep = new Step(parent.getUserObject().toString());
				parentStep.setParentBusinessProcess(parentBusinessProcess);
				parentStep = searchStep(parentStep);
				for(IAction action : parentStep.getActions()){
					if(action.getValue().equals(aSelectedNodeValue)) {
						parentStep.getActions().remove(action);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public BusinessProcess createBusinessProcess(IVerbNounPhrase aVerbNounPhrase) {
		//Create a new Business Process.
		BusinessProcess businessProcess = new BusinessProcess(aVerbNounPhrase.getValue());
		if (businessProcesses.add(businessProcess))
			return businessProcess;
		else
			return null;
	}

	@Override
	public Step createStep(IVerbNounPhrase aVerbNounPhrase, IBusinessProcess aParentBusinessProcess) {
		Step step = new Step(aVerbNounPhrase.getValue());
		step.setParentBusinessProcess(aParentBusinessProcess);
		if (aParentBusinessProcess.addStep(step))
			return step;
		else
			return null;
	}

	@Override
	public Action createAction(IVerbNounPhrase aVerbNounPhrase, IStep aParentStep) {
		//Create new Action for given Step of Business Process.
		Action action = new Action(aVerbNounPhrase.getValue());
		action.setParentBusinessProcess(aParentStep.getParentBusinessProcess());
		action.setParentStep(aParentStep);
		if (aParentStep.addAction(action))
			return action;
		else
			return null;
	}

	public IBusinessProcess searchBusinessProcess(IBusinessProcess aBusinessProcess) {
		for(IBusinessProcess businessProcess : businessProcesses){
			if(businessProcess.equals(aBusinessProcess)) {
				return businessProcess;
			}
		}
		return null;
	}
	
	public IStep searchStep(IStep aStep) {
		
		for(IBusinessProcess businessProcess : businessProcesses){
			for(IStep step : businessProcess.getSteps()){
				if(step.equals(aStep)) {
					return step;
				}
			}
		}
		return null;
	}
	
	public IAction searchAction(IAction anAction) {

		for(IBusinessProcess businessProcess : businessProcesses){
			for(IStep step : businessProcess.getSteps()){
				for(IAction action : step.getActions()) {
					if(action.equals(anAction)) {
						return action;
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public Set<IBusinessProcess> getUpdatedBusinessProcesses() {
		return businessProcesses;
	}

	@Override
	public List<String> addRequirement(String businessProcess, String requirement, int aPriority) {
		
		String priority = null;
		switch(aPriority) {
			case 1 : priority = "Low"; break;
			case 2 : priority = "Medium"; break;
			case 3 : priority = "High"; break;
		}
		List<String> listOfRequirements = new ArrayList<String>();
		IBusinessProcess selectedBusinessProcess = new BusinessProcess(businessProcess);
		selectedBusinessProcess = searchBusinessProcess(selectedBusinessProcess);
		if(selectedBusinessProcess == null) {
			throw new IllegalArgumentException("Please select a Business Process under which you want to add the requirement.");
		}
		Requirement aNewRequirement = new Requirement(requirement);
		aNewRequirement.setPriority(String.valueOf(priority));
		generatedRequirement.add(new Generate(selectedBusinessProcess, aNewRequirement));
		for(IGenerate generate : generatedRequirement) {
			if(generate.getBusinessProcess().equals(selectedBusinessProcess)) {
				listOfRequirements.add(generate.getRequirements().getValue() +" - "+generate.getRequirements().getPriority());
			}
		}
		return listOfRequirements;
	}

	@Override
	public List<String> getRequirements(String businessProcess) {
		
		List<String> listOfRequirements = new ArrayList<String>();
		IBusinessProcess selectedBusinessProcess = new BusinessProcess(businessProcess);
		selectedBusinessProcess = searchBusinessProcess(selectedBusinessProcess);
		
		if(businessProcess.equals("Get All Requirements")) {
			for(IGenerate generate : generatedRequirement) {
				listOfRequirements.add(generate.getBusinessProcess().getValue()+ " - " + generate.getRequirements().getPriority() + " - " + generate.getRequirements().getValue());
			}
		}
		else if(businessProcess != null) {
			for(IGenerate generate : generatedRequirement) {
				if(selectedBusinessProcess.equals(generate.getBusinessProcess())) {
					listOfRequirements.add(generate.getRequirements().getValue()+ " - " + generate.getRequirements().getPriority());
				}
			}
		}
		return listOfRequirements;
	}

	

}
