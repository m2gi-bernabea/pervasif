package manager.service.provided.impl;

import java.util.List;

import manager.service.provided.it.ManagerProviderIt;
import manager.service.utils.Context;
import activator.service.provided.ActivatorServiceIT;

public class ManagerProviderImpl implements ManagerProviderIt {

	private Context currentBasicContext;
	/** Field for activatorService dependency */
	private ActivatorServiceIT activatorService;

	@Override
	public void pushNewBasicContext(String newContext) {
		System.out.println("ManagerService : Réception d'un nouveau context.");
		currentBasicContext = Context.getByDescriptor(newContext);
		System.out.println("ManagerService : Contexte courant mis à jour - "
				+ newContext);
		computeComplexContext();
	}

	@Override
	public void pushNewBasicContext(List<String> newMultipleContext) {
		//Utilisée ou non selon l'implémentation que l'on va choisir
	}

	private void computeComplexContext() {
		System.out.println("Traitement d'un contexte complexe :");
		System.out
				.println("Prise en compte du dernier contexte transmis par le ListenerService ("
						+ currentBasicContext.descriptor
						+ ") et demande "
						+ "auprès du TimeOfTheDayService.");
		System.out
				.println("Sauvegarde du contexte complexe calculé et trasmission à l'Activator service");
		
		activatorService.applyContext(currentBasicContext+":"+Context.SALLEDEBAIN.descriptor);
	}

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("Service ManagerProvider stopped !");
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("Service ManagerProvider started !");
	}

}
