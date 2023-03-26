package activator.service.provided;

import fr.liglab.adele.icasa.device.sound.AudioSource;

public interface ActivatorServiceIT {
	
	public void applyContext (String context);
	public void activateLight (String...rooms);
	public void disableLight (String...rooms);
	public void activateSiren (String...rooms);
	public void disableSiren (String...rooms);
	public void activateSpeaker (AudioSource audioSource, String...rooms);
	
	/*
	 * Met en place le couvre-feu dans la maison
	 */
	public void placeCurfew();
}
