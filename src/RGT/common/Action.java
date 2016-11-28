package RGT.common;

import RGT.common.interfaces.IAction;
import RGT.common.interfaces.IBusinessProcess;
import RGT.common.interfaces.IStep;

public class Action extends VerbNounPhrase implements IAction {

	private String value;
	IBusinessProcess parentBusinessProcess;
	IStep parentStep;
	
	public Action(String aValue) {
		super(aValue);
		value = aValue;
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
	public IStep getParentStep() {
		return parentStep;
	}

	@Override
	public void setParentStep(IStep aParentStep) {
		parentStep = aParentStep;	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parentBusinessProcess == null) ? 0 : parentBusinessProcess.hashCode());
		result = prime * result + ((parentStep == null) ? 0 : parentStep.hashCode());
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
		Action other = (Action) obj;
		if (parentBusinessProcess == null) {
			if (other.parentBusinessProcess != null)
				return false;
		} else if (!parentBusinessProcess.equals(other.parentBusinessProcess))
			return false;
		if (parentStep == null) {
			if (other.parentStep != null)
				return false;
		} else if (!parentStep.equals(other.parentStep))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
