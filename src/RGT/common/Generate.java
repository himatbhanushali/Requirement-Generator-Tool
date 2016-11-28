package RGT.common;

import RGT.common.interfaces.IBusinessProcess;
import RGT.common.interfaces.IGenerate;
import RGT.common.interfaces.IRequirement;

public class Generate implements IGenerate {

	IBusinessProcess businessProcess;
	IRequirement requirements;
	
	public Generate(IBusinessProcess aBusinessProcess, IRequirement aRequirements) {
		businessProcess = aBusinessProcess;
		requirements = aRequirements;
	}

	public IBusinessProcess getBusinessProcess() {
		return businessProcess;
	}

	public void setBusinessProcess(IBusinessProcess aBusinessProcess) {
		businessProcess = aBusinessProcess;
	}

	public IRequirement getRequirements() {
		return requirements;
	}

	public void setRequirements(IRequirement aRequirements) {
		requirements = aRequirements;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((businessProcess == null) ? 0 : businessProcess.hashCode());
		result = prime * result + ((requirements == null) ? 0 : requirements.hashCode());
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
		Generate other = (Generate) obj;
		if (businessProcess == null) {
			if (other.businessProcess != null)
				return false;
		} else if (!businessProcess.equals(other.businessProcess))
			return false;
		if (requirements == null) {
			if (other.requirements != null)
				return false;
		} else if (!requirements.equals(other.requirements))
			return false;
		return true;
	}
	
	
}
