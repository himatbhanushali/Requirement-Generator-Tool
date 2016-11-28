package RGT.common;

import RGT.common.interfaces.IVerbNounPhrase;

public class VerbNounPhrase implements IVerbNounPhrase {
	String value;
	
	public VerbNounPhrase(String aValue) {
		value = aValue;
	}
	
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String aValue) {
		value = aValue;
	}

	@Override
	public String toString() {
		return value;
	}
}
