package RGT.common.interfaces;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IStorageMgr {
	public boolean saveRequirements(List<String> aListOfRequirements, File fileToSave) throws IOException;
}
