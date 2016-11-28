package RGT.common;

import java.util.ArrayList;
import java.util.List;

import RGT.common.interfaces.IBusinessProcess;
import RGT.common.interfaces.IStep;

public class BusinessProcess extends VerbNounPhrase implements IBusinessProcess {

	private List<IStep> steps;
	private String value;
	
	BusinessProcess(String aBusinessProcessValue) {
		super(aBusinessProcessValue);
		value = aBusinessProcessValue;
		if(steps == null) {
			steps = new ArrayList<IStep>();
		}
	}

	public boolean addStep(IStep step) {
		return steps.add(step);
	}

	@Override
	public boolean removeStep(IStep aStep) {
		/*int index = 0;
		for(IStep step : steps) {
			if(aStep.getValue().equals(step.getValue())) {
				steps.remove(index);
				return true;
			}
			index++;
		}
		return false;*/
		return steps.remove(aStep);
	}

	@Override
	public List<IStep> getSteps() {
		return steps;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		BusinessProcess other = (BusinessProcess) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
}
