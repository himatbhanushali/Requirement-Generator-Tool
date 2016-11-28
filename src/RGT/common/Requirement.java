package RGT.common;

import RGT.common.interfaces.IRequirement;

public class Requirement implements IRequirement {

	private String priority;
	private String value;
	
	public Requirement(String aValue) {
		value = aValue;
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
		Requirement other = (Requirement) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String aPriority) {
		priority = aPriority;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String aValue) {
		value = aValue;
	}
	
}
