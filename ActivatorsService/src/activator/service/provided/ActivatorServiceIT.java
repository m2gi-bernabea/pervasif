package activator.service.provided;

public interface ActivatorServiceIT {
	
	public void activateLight (String...rooms);
	public void disableLight (String...rooms);
	public void activateSiren (String...rooms);
	public void disableSiren (String...rooms);
	public void activateSpeaker (String...rooms);
	public void activateSprinkler (String...rooms);
	public void disableSprinkler (String...rooms);
	
	/**
	 * Met en place le couvre feu
	 */
	public void placeCurfew();
}
