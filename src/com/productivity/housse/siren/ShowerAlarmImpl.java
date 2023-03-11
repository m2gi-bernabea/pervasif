package com.productivity.housse.siren;

import fr.liglab.adele.icasa.device.DeviceListener;
import fr.liglab.adele.icasa.device.GenericDevice;
import fr.liglab.adele.icasa.device.security.Siren;
import fr.liglab.adele.icasa.device.presence.PresenceSensor;
import java.util.Map;

public class ShowerAlarmImpl implements DeviceListener {

	/** Field for siren dependency */
	private Siren siren;
	/** Field for presenceSensor dependency */
	private PresenceSensor presenceSensor;

	/** Bind Method for siren dependency */
	public void bindSiren(Siren siren, Map properties) {
		this.siren = siren;
	}

	/** Unbind Method for siren dependency */
	public void unbindSiren(Siren siren, Map properties) {
		this.siren = null;
	}

	/** Bind Method for presenceSensors dependency */
	public void bindPresenceSensor(PresenceSensor presenceSensor, Map properties) {
		presenceSensor.addListener(this);
		this.presenceSensor = presenceSensor;
	}

	/** Unbind Method for presenceSensors dependency */
	public void unbindPresenceSensor(PresenceSensor presenceSensor,
			Map properties) {
		presenceSensor.removeListener(this);
		this.presenceSensor = null;
	}

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("Component is stopping...");
		presenceSensor = null;
		presenceSensor = null;
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("Component is starting...");
	}

	public void deviceAdded(GenericDevice arg0) {
		// TODO Auto-generated method stub

	}

	public void deviceEvent(GenericDevice arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public void devicePropertyAdded(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void devicePropertyModified(GenericDevice device, String propertyName,
			Object oldValue, Object newValue) {
		if(device instanceof PresenceSensor) {
			 PresenceSensor activSensor = (PresenceSensor) device;
			 if(activSensor != null && propertyName.equals(PresenceSensor.PRESENCE_SENSOR_SENSED_PRESENCE)){
				 System.out.println("One person is taking a bath...");
				 //TODO: Delay and if the person take too much time -> Activate Siren
			 }
		}

	}

	public void devicePropertyRemoved(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void deviceRemoved(GenericDevice arg0) {
		// TODO Auto-generated method stub

	}

}
