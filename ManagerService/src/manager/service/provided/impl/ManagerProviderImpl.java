package manager.service.provided.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.LocalAttribute;

import manager.service.provided.it.ManagerProviderIt;
import manager.service.utils.Context;
import activator.service.provided.ActivatorServiceIT;
import time.service.provided.it.MomentOfTheDayIt;

public class ManagerProviderImpl implements ManagerProviderIt {

	private Context currentBasicContext;
	/** Field for activatorService dependency */
	private ActivatorServiceIT activatorService;

	private HashMap<String, List<Context>> localizedContext;
	/** Field for momentOfTheDay dependency */
	private MomentOfTheDayIt momentOfTheDay;

	@Override
	public void pushNewBasicContext(String location, String newContext) {
		System.out.println("ManagerService : Réception d'un nouveau context.");
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
						+ "aupr�s du TimeOfTheDayService : "
						+ momentOfTheDay.getCurrentHourOfTheDay());
		System.out
				.println("Sauvegarde du contexte complexe calcul� et trasmission � l'Activator service");

		activatorService.applyContext(currentBasicContext + ":");
	}

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("Service ManagerProvider stopped !");
		localizedContext = null;
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("Service ManagerProvider started !");
		localizedContext = new HashMap<>();
	}

	@Override
	public void peopleIn(String location) {
		System.out.println("Quelqu'un dans : " + location);
		checkKnownLocation(location);
		checkTime(location);
		updatePresence(location, true);
	}

	@Override
	public void peopleOut(String location) {
		System.out.println("Plus personne dans : " + location);
		checkKnownLocation(location);
		checkTime(location);
		updatePresence(location, false);
	}

	@Override
	public void movementIn(String location) {
		System.out.println("(Ping) movement detected in : " + location);
		checkKnownLocation(location);
		updateActivity(location);
	}

	private synchronized void updatePresence(String location, boolean in) {
		if (in) {
			cleanContext(location, Context.VIDE);
			if (!localizedContext.get(location).contains(Context.OCCUPE)) {
				localizedContext.get(location).add(Context.OCCUPE);
				System.out.println("From Manager Service : Ajout contexte "
						+ Context.OCCUPE.descriptor + " dans " + location);
			}
		} else {
			cleanContext(location, Context.OCCUPE);
			if (!localizedContext.get(location).contains(Context.VIDE)) {
				localizedContext.get(location).add(Context.VIDE);
				System.out.println("From Manager Service : Ajout contexte "
						+ Context.VIDE.descriptor + " dans " + location);
			}
		}
	}

	private synchronized void cleanContext(String location,
			Context contextToClean) {
		if (localizedContext.get(location).remove(contextToClean))
			System.out.println("From Manager Service : Nettayage du contexte "
					+ contextToClean.descriptor + " dans " + location);
	}

	private synchronized void checkTime(String location) {
		//Appel TimeOfTheDay pour vérifier si l'accès est interdit ou si couvre feu (hors horaires)
		System.out.println("From Manager Service : Prise en compte du time of the day : "
				+ momentOfTheDay.getCurrentHourOfTheDay());
		if (Math.random() > 0.5) {
			System.out
					.println("From Manager Service : Hors horaires définis par le régime.");
			localizedContext.get(location).removeAll(
					Arrays.asList(Context.values()));
			if (!location.equalsIgnoreCase("bedroom")) {
				localizedContext.get(location).add(Context.ACCESINTERDIT);
			} else {
				System.out
						.println("From Manager Service : Presence obligatoire dans "
								+ location);
			}

			localizedContext.get(location).add(Context.COUVREFEU);
		} else {
			System.out
					.println("From Manager Service : Dans les horaires définis par le régime.");
		}
	}

	private synchronized void updateActivity(String location) {
		//Supprimme le contexte INACTIF s'il existe
		cleanContext(location, Context.INACTIF);

		if (!localizedContext.get(location).contains(Context.ACTIF)) {
			System.out
					.println("From Manager Service : Ajout contexte ACTIF dans "
							+ location);
			localizedContext.get(location).add(Context.ACTIF);
		}

	}

	private synchronized void checkKnownLocation(String location) {
		if (!localizedContext.containsKey(location)) {
			System.out.println("From Manager Service : Nouvelle zone ("
					+ location + ") ajoutée.");
			localizedContext.put(location, new ArrayList<Context>());
		} else {
			System.out.println("From Manager Service : Zone (" + location
					+ ") déjà découverte.");
		}

	}

}
