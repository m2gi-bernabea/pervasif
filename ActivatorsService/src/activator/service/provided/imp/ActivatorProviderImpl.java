package activator.service.provided.imp;

import activator.service.provided.ActivatorServiceIT;
import fr.liglab.adele.icasa.device.GenericDevice;
import fr.liglab.adele.icasa.device.security.Siren;
import fr.liglab.adele.icasa.device.light.BinaryLight;
import fr.liglab.adele.icasa.device.sound.AudioSource;
import fr.liglab.adele.icasa.device.sound.Speaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ActivatorProviderImpl implements ActivatorServiceIT {

	private Siren[] sirens;
	private BinaryLight[] binaryLights;
	private Speaker[] speakers;
	private static final String LOCATION_PROPERTY_NAME = "Location";

	@Override
	public void applyContext(String context) {
		System.out.println("New context : " + context);
	}

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

		for (Speaker speaker : speakers) {
			if (speaker.getPropertyValue(LOCATION_PROPERTY_NAME).equals(
					location))
				speakersLocation.add(speaker);
		}

		return speakersLocation;
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
	public void activateSpeaker(AudioSource audioSource, String... rooms) {
		for (String room : rooms) {
			List<Speaker> sameLocationSpeakers = getSpeakersFromLocation(room);

			for (Speaker speaker : sameLocationSpeakers) {
				speaker.setAudioSource(audioSource);
			}
		}
	}

	@Override
	public void placeCurfew() {
		for (BinaryLight binaryLight : this.binaryLights)
			binaryLight.turnOff();
		//TODO surveiller s'il y a du mouvement et activer le speaker si c'est le cas
		//TODO éteindre automatiquement les lumières si allumées durant le couvre feu (ou faire ça avec juste un appel au service depuis l'admin)
	}

	/** Bind Method for sirens dependency */
	public void bindSiren(Siren siren, Map properties) {
		// TODO: Add your implementation code here
	}

	/** Unbind Method for sirens dependency */
	public void unbindSiren(Siren siren, Map properties) {
		// TODO: Add your implementation code here
	}

	/** Bind Method for binaryLights dependency */
	public void bindbinaryLight(BinaryLight binaryLight, Map properties) {
		// TODO: Add your implementation code here
	}

	/** Unbind Method for binaryLights dependency */
	public void unbindbinaryLight(BinaryLight binaryLight, Map properties) {
		// TODO: Add your implementation code here
	}

	/** Bind Method for speakers dependency */
	public void bindSpeaker(Speaker speaker, Map properties) {
		// TODO: Add your implementation code here
	}

	/** Unbind Method for speakers dependency */
	public void unbindSpeaker(Speaker speaker, Map properties) {
		// TODO: Add your implementation code here
	}

}
