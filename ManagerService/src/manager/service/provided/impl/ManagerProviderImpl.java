package manager.service.provided.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import manager.service.provided.it.ManagerProviderIt;
import manager.service.utils.Context;
import activator.service.provided.ActivatorServiceIT;
import time.service.provided.it.MomentOfTheDayIt;

public class ManagerProviderImpl implements ManagerProviderIt {

	private ActivatorServiceIT activatorService;
	private HashMap<String, List<Context>> localizedContext;
	private MomentOfTheDayIt momentOfTheDay;
	private Map<String, Integer> tenMinutesCounter;
	private static final String kitchenLocation = "kitchen";
	private static final String loungeLocation = "lounge";
	private static final String bedroomLocation = "bedroom";
	private static final String bathroomLocation = "bathroom";
	private boolean isCurfew = false;

	private void computeContext(String location) {
		System.out.println("Calcul du contexte pour la zone : " +location);
		
		if(this.isCurfew)
			this.activatorService.placeCurfew();
		
		switch (location) {
			case kitchenLocation : computeKitchenContext(this.localizedContext.get(location), location);
				break;
			case bathroomLocation : computeBathroomContext(this.localizedContext.get(location), location);
				break;
			case loungeLocation : computeLoungeContext(this.localizedContext.get(location),location);
				break;
			case bedroomLocation : computeBedroomContext(this.localizedContext.get(location), location);
				break;
			default:
				break;
		}
	}
	
	public void computeKitchenContext(List<Context> context, String location) {
		if(context.contains(Context.OCCUPE) && context.contains(Context.ACCESINTERDIT)) {
			this.activatorService.activateSiren(location);
			//this.activatorService.activateSpeaker(location); TODO rétablir activation du speaker
		}
	}
	
	public void computeBathroomContext(List<Context> context, String location) {
		if(context.contains(Context.OCCUPE) && context.contains(Context.ACCESINTERDIT)) {
			this.activatorService.activateSiren(location);
			//this.activatorService.activateSpeaker(location); TODO rétablir activation du speaker
		}
		
		if(this.tenMinutesCounter.get(location) == 1 && context.contains(Context.OCCUPE))
			this.activatorService.activateSprinkler(location);
		
		if(!context.contains(Context.OCCUPE))
			this.activatorService.disableLight(location);
	}
	
	public void computeBedroomContext(List<Context> context, String location) {
		if(context.contains(Context.ACCESINTERDIT))
			this.activatorService.activateSiren(location);
	}
	
	public void computeLoungeContext(List<Context> context, String location) {
		if (this.tenMinutesCounter.get(location) == 3 && context.contains(Context.OCCUPE))
			this.activatorService.activateSiren(location);
		
		if (context.contains(Context.ACCESINTERDIT) && context.contains(Context.OCCUPE)) {
			this.activatorService.activateSiren(location);
			// this.activatorService.activateSpeaker(location); TODO rétablir activation du speaker
		}
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
		this.tenMinutesCounter = new HashMap<>();
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
				System.out.println("From Manager Service : Ajout contexte " + Context.OCCUPE.descriptor + " dans " + location);
			}
		} 
		else {
			cleanContext(location, Context.OCCUPE);
			if (!localizedContext.get(location).contains(Context.VIDE)) {
				localizedContext.get(location).add(Context.VIDE);
				System.out.println("From Manager Service : Ajout contexte " + Context.VIDE.descriptor + " dans " + location);
			}
		}
	}
	
	/**
	 * Retire un contexte des contextes actifs d'un localistation
	 * @param location Lieu où retirer le contexte
	 * @param contextToClean Contexte à retirer
	 */
	private synchronized void cleanContext(String location, Context contextToClean) {
		if (localizedContext.get(location).remove(contextToClean))
			System.out.println("From Manager Service : Nettayage du contexte " + contextToClean.descriptor + " dans " + location);
	}

	private synchronized void checkTime(String location) {
		//Appel TimeOfTheDay pour vérifier si l'accès est interdit ou si couvre feu (hors horaires)
		System.out.println("From Manager Service : Prise en compte du time of the day : " + momentOfTheDay.getCurrentHourOfTheDay());
		
		if (Math.random() > 0.5) {
			System.out.println("From Manager Service : Hors horaires définis par le régime.");
			localizedContext.get(location).removeAll(Arrays.asList(Context.values()));
			
			if (!location.equalsIgnoreCase("bedroom")) 
				localizedContext.get(location).add(Context.ACCESINTERDIT);
			
			else 
				System.out.println("From Manager Service : Presence obligatoire dans " + location);
			
			this.isCurfew = true;
		} 
		
		else {
			System.out.println("From Manager Service : Dans les horaires définis par le régime.");
			this.isCurfew = false;
		}
			
	}

	private synchronized void updateActivity(String location) {
		//Supprimme le contexte INACTIF s'il existe
		cleanContext(location, Context.INACTIF);

		if (!localizedContext.get(location).contains(Context.ACTIF)) {
			System.out.println("From Manager Service : Ajout contexte ACTIF dans " + location);
			localizedContext.get(location).add(Context.ACTIF);
		}

	}

	private synchronized void checkKnownLocation(String location) {
		if (!localizedContext.containsKey(location)) {
			System.out.println("From Manager Service : Nouvelle zone ("+ location + ") ajoutée.");
			localizedContext.put(location, new ArrayList<Context>());
		} 
		else 
			System.out.println("From Manager Service : Zone (" + location + ") déjà découverte.");
	}
	
	@Override
	public void pushTenMinutes() {
		System.out.println("From Manager Service : pushTenMinutes called : " + this.tenMinutesCounter);
		
		for(String location : this.localizedContext.keySet()) {
			this.computeContext(location);
			int tickForRoom = this.tenMinutesCounter.get(location);
			this.tenMinutesCounter.replace(location, tickForRoom++);
			// TODO Réinitialiser quand > 3 ?
		}
	}

}
