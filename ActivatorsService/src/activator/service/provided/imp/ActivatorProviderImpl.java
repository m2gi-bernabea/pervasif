package activator.service.provided.imp;

import activator.service.provided.ActivatorServiceIT;
import fr.liglab.adele.icasa.device.DeviceListener;
import fr.liglab.adele.icasa.device.GenericDevice;
import fr.liglab.adele.icasa.device.security.Siren;
import fr.liglab.adele.icasa.device.light.BinaryLight;
import fr.liglab.adele.icasa.device.sound.Speaker;
import fr.liglab.adele.icasa.device.sprinkler.Sprinkler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivatorProviderImpl implements ActivatorServiceIT, DeviceListener<GenericDevice>{

	private Siren[] sirens;
	private BinaryLight[] binaryLights;
	private Speaker[] speakers;
	private Sprinkler[] sprinklers;
	private static final String LOCATION_PROPERTY_NAME = "Location";

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("Activator provided service is stoping");
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("Activator provided service is starting !");
	}

	private synchronized List<BinaryLight> getBinaryLightFromLocation(
			String location) {
		List<BinaryLight> binaryLightsLocation = new ArrayList<BinaryLight>();

		for (BinaryLight binaryLight : binaryLights) {
			if (binaryLight.getPropertyValue(LOCATION_PROPERTY_NAME).equals(
					location))
				binaryLightsLocation.add(binaryLight);
		}

		return binaryLightsLocation;
	}

	private synchronized List<Siren> getSirensFromLocation(String location) {
		List<Siren> sirensLocation = new ArrayList<Siren>();

		for (Siren siren : sirens) {
			if (siren.getPropertyValue(LOCATION_PROPERTY_NAME).equals(location))
				sirensLocation.add(siren);
		}

		return sirensLocation;
	}

	private synchronized List<Speaker> getSpeakersFromLocation(String location) {
		List<Speaker> speakersLocation = new ArrayList<Speaker>();

		for (Speaker speaker : this.speakers) 
			if (speaker.getPropertyValue(LOCATION_PROPERTY_NAME).equals(location))
				speakersLocation.add(speaker);

		return speakersLocation;
	}
	
	private synchronized List<Sprinkler> getSprinklerFromLocation(String location) {
		List<Sprinkler> sprinklersLocation = new ArrayList<Sprinkler>();
		
		for (Sprinkler sprinkler : this.sprinklers) 
			if (sprinkler.getPropertyValue(LOCATION_PROPERTY_NAME).equals(location))
				sprinklersLocation.add(sprinkler);
		
		return sprinklersLocation;
	}

	@Override
	public void activateLight(String... rooms) {
		for (String room : rooms) {
			List<BinaryLight> sameLocationLigths = getBinaryLightFromLocation(room);

			for (BinaryLight binaryLight : sameLocationLigths)
				binaryLight.turnOn();

		}
	}

	@Override
	public void disableLight(String... rooms) {
		for (String room : rooms) {
			List<BinaryLight> sameLocationLigths = getBinaryLightFromLocation(room);

			for (BinaryLight binaryLight : sameLocationLigths)
				binaryLight.turnOff();

		}
	}

	@Override
	public void activateSiren(String... rooms) {
		System.out.println("From Activator Service : activer sirène");
		for (String room : rooms) {
			List<Siren> sameLocationSirens = getSirensFromLocation(room);

			for (Siren siren : sameLocationSirens)
				siren.turnOn();

		}
	}

	@Override
	public void disableSiren(String... rooms) {
		for (String room : rooms) {
			List<Siren> sameLocationSirens = getSirensFromLocation(room);

			for (Siren siren : sameLocationSirens)
				siren.turnOff();

		}
	}

	@Override
	public void activateSpeaker(String... rooms) {
		for (String room : rooms) {
			List<Speaker> sameLocationSpeakers = getSpeakersFromLocation(room);
			for (Speaker speaker : sameLocationSpeakers) {
				//speaker.setAudioSource(audioSource);
				//speaker.notify(); //TODO Chercher ce que fait notify()
			}
		}
	}
	
	@Override
	public void activateSprinkler(String... rooms) {
		System.out.println("From Activator Service : activation des jets d'eau");
		for (String room : rooms) {
			List<Sprinkler> sameLocationSprinklers = getSprinklerFromLocation(room);

			for (Sprinkler sprinkler : sameLocationSprinklers)
				sprinkler.turnOn();
		}
	}

	@Override
	public void disableSprinkler(String... rooms) {
		for (String room : rooms) {
			List<Sprinkler> sameLocationSprinklers = getSprinklerFromLocation(room);

			for (Sprinkler sprinkler : sameLocationSprinklers)
				sprinkler.turnOff();
		}
	}

	@Override
	public void placeCurfew() {
		System.out.println("From Activator Service : couvre feu extinction des lumières");
		for (BinaryLight binaryLight : this.binaryLights)
			binaryLight.turnOff();
	}

	/** Bind Method for sirens dependency */
	public void bindSiren(Siren siren, Map properties) {
		siren.addListener(this);
	}

	/** Unbind Method for sirens dependency */
	public void unbindSiren(Siren siren, Map properties) {
		siren.removeListener(this);
	}

	/** Bind Method for binaryLights dependency */
	public void bindBinaryLight(BinaryLight binaryLight, Map properties) {
		binaryLight.addListener(this);
	}

	/** Unbind Method for binaryLights dependency */
	public void unbindbinaryLight(BinaryLight binaryLight, Map properties) {
		binaryLight.removeListener(this);
	}

	/** Bind Method for speakers dependency */
	public void bindSpeaker(Speaker speaker, Map properties) {
		speaker.addListener(this);
	}

	/** Unbind Method for speakers dependency */
	public void unbindSpeaker(Speaker speaker, Map properties) {
		speaker.removeListener(this);
	}

	@Override
	public void deviceAdded(GenericDevice arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deviceEvent(GenericDevice arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void devicePropertyAdded(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void devicePropertyModified(GenericDevice arg0, String arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub
	}

	@Override
	public void devicePropertyRemoved(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deviceRemoved(GenericDevice arg0) {
		// TODO Auto-generated method stub
	}

	

}
