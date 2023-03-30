package manager.service.provided.it;

import java.util.List;

public interface ContextProviderIt {
	
	public List<String> getAllContext();
	
	public String getContextActifDescriptor();
	public String getContextInactifDescriptor();
	public String getContextTropChaudDescriptor();
	public String getContextTropFroidDescriptor();

}
