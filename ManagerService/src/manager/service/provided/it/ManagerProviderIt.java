package manager.service.provided.it;

import java.util.List;

public interface ManagerProviderIt {
	
	public void pushNewBasicContext(String newContext);
	
	public void pushNewBasicContext(List<String> newMultipleContext);

}
