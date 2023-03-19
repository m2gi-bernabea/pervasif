package manager.service.provided.it;

import java.util.List;

public interface ContextProviderIt {
	
	public List<String> getAllContext();
	
	public String getContextActif();
	public String getContextInactif();
	public String getContextSalleDeBain();

}
