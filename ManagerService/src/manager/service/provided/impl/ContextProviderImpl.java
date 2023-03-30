package manager.service.provided.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import manager.service.provided.it.ContextProviderIt;
import manager.service.utils.Context;

public class ContextProviderImpl implements ContextProviderIt {

	@Override
	public List<String> getAllContext() {
		List<String> contexts = new ArrayList<>();
		for(int i = 0; i < Context.values().length; i++) {
			contexts.add(Context.values()[i].descriptor);
		}
		return contexts;
	}

	@Override
	public String getContextActifDescriptor() {
		return Context.ACTIF.descriptor;
	}

	@Override
	public String getContextInactifDescriptor() {
		return Context.INACTIF.descriptor;
	}

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("Service ContextProvider is stopped !");
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("Service ContextProvider is started !");
	}

	@Override
	public String getContextTropChaudDescriptor() {
		return Context.TROPCHAUD.descriptor;
	}

	@Override
	public String getContextTropFroidDescriptor() {
		return Context.TROPFROID.descriptor;
	}

}
