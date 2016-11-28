package RGT.common.interfaces;

import java.util.List;

public interface IBusinessProcess extends IVerbNounPhrase {
	public boolean addStep(IStep aStep);
	public boolean removeStep(IStep aStep);
	public List<IStep> getSteps(); 
}
