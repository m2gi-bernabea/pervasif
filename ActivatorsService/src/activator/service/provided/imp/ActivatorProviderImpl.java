package activator.service.provided.imp;

import activator.service.provided.ActivatorServiceIT;

public class ActivatorProviderImpl implements ActivatorServiceIT {

	@Override
	public void applyContext(String context) {
		System.out.println("Nouveau contexte : " +context);
	}

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("Activator provided service is stoping");
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("Activator provided service is starting !!!");
	}

}
