package manager.service.provided.it;

import java.util.List;

public interface ManagerProviderIt {
	
	public void pushNewBasicContext(String location, String activeContext);
	
	public void pushNewBasicContext(List<String> newMultipleContext);
	
	public void movementIn(String location);
	
	public void peopleIn(String location);
	
	public void peopleOut(String location);

	public void pushTenMinutes();

}
