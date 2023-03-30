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
		System.out.println("ManagerService : R�ception d'un nouveau context.");
		currentBasicContext = Context.getByDescriptor(newContext);
		System.out.println("ManagerService : Contexte courant mis � jour - "
				+ newContext);
		computeComplexContext();
	}

	@Override
	public void pushNewBasicContext(List<String> newMultipleContext) {
		//Utilis�e ou non selon l'impl�mentation que l'on va choisir
	}

	private void computeComplexContext() {
		System.out.println("Traitement d'un contexte complexe :");
		System.out
				.println("Prise en compte du dernier contexte transmis par le ListenerService ("
						+ currentBasicContext.descriptor
						+ ") et demande "
						+ "aupr�s du TimeOfTheDayService.");
		System.out
				.println("Sauvegarde du contexte complexe calcul� et trasmission � l'Activator service");
		
		activatorService.applyContext(currentBasicContext+":");
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
