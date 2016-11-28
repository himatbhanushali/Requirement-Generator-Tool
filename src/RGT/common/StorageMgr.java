package RGT.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import RGT.common.interfaces.IStorageMgr;

public class StorageMgr implements IStorageMgr {

	//private Set<IBusinessProcess> businessProcesses;
	
	public StorageMgr() {
		//businessProcesses = new HashSet<IBusinessProcess>();
	}

	@Override
	public boolean saveRequirements(List<String> aListOfRequirements, File fileToSave) throws IOException {

		fileToSave.createNewFile();
		createFile(aListOfRequirements, fileToSave);
		return true;
	}
	
	private void createFile(List<String> aListOfRequirements, File fileToSave) throws IOException {
        FileWriter writer = new FileWriter(fileToSave);
		//PrintWriter writer = new PrintWriter(new FileOutputStream(fileToSave));
		int fileSize = aListOfRequirements.size();
        for (int index = 0; index < fileSize; index++) {
            String str = aListOfRequirements.get(index).toString();
            writer.write(str);
            if(index < fileSize-1) //To stop from adding blank lines in the end of the file
                writer.write("\r\n");
        }
        writer.close();
    }

	

	/*public IBusinessProcess searchBusinessProcess(IBusinessProcess aBusinessProcess) {
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

	@Override
	public Set<IBusinessProcess> getUpdatedBusinessProcesses() {
		return businessProcesses;
	}*/
}
