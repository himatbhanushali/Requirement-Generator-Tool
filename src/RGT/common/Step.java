package RGT.common;

import RGT.common.interfaces.IStep;
import RGT.common.VerbNounPhrase;

import java.util.ArrayList;
import java.util.List;

import RGT.common.interfaces.IAction;
import RGT.common.interfaces.IBusinessProcess;

public class Step extends VerbNounPhrase implements IStep {
	
	private List<IAction> actions;
	private String value;
	IBusinessProcess parentBusinessProcess;
	
	Step(String stepValue) {
		super(stepValue);
		value = stepValue;
		if(actions == null) {
			actions = new ArrayList<IAction>();
		}
	}

	@Override
	public IBusinessProcess getParentBusinessProcess() {
		return parentBusinessProcess;
	}

	@Override
	public void setParentBusinessProcess(IBusinessProcess aParentBusinessProcess) {
		parentBusinessProcess = aParentBusinessProcess;
	}
	
	@Override
	public boolean addAction(IAction anAction) {
		return actions.add(anAction);
	}

	@Override
	public boolean removeAction(IAction anAction) {
		/*int index = 0;
		for(IAction action : actions) {
			if(anAction.getValue().equals(action.getValue())) {
				actions.remove(index);
				return true;
			}
			index++;
		}
		return false;*/
		return actions.remove(anAction);
	}

	@Override
	public List<IAction> getActions() {
		return actions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parentBusinessProcess == null) ? 0 : parentBusinessProcess.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Step other = (Step) obj;
		if (parentBusinessProcess == null) {
			if (other.parentBusinessProcess != null)
				return false;
		} else if (!parentBusinessProcess.equals(other.parentBusinessProcess))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
