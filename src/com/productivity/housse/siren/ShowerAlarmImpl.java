package com.productivity.housse.siren;

import fr.liglab.adele.icasa.device.DeviceListener;
import fr.liglab.adele.icasa.device.GenericDevice;
import fr.liglab.adele.icasa.device.security.Siren;
import fr.liglab.adele.icasa.device.presence.PresenceSensor;
import java.util.Map;

public class ShowerAlarmImpl implements DeviceListener {

	/** Field for siren dependency */
	private Siren[] sirens;
	/** Field for presenceSensor dependency */
	private PresenceSensor[] presenceSensors;

	/** Bind Method for siren dependency */
	public void bindSirens(Siren siren, Map properties) {
		  System.out.println("bind siren " + siren.getSerialNumber());
	}

	/** Unbind Method for siren dependency */
	public void unbindSirens(Siren siren, Map properties) {
		  System.out.println("unbind siren " + siren.getSerialNumber());	
	}

	/** Bind Method for presenceSensors dependency */
	public void bindPresenceSensors(PresenceSensor presenceSensor, Map properties) {
		System.out.println("bind presence sensor for shower"+ presenceSensor.getSerialNumber());
		presenceSensor.addListener(this);
	}

	/** Unbind Method for presenceSensors dependency */
	public void unbindPresenceSensors(PresenceSensor presenceSensor,
			Map properties) {
		System.out.println("Unbind presence sensor for shower"+ presenceSensor.getSerialNumber());	
		presenceSensor.removeListener(this);
	}

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("Component is stopping...");
		for(PresenceSensor sensor : presenceSensors){
		      sensor.removeListener(this);
		   }
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
		 System.out.println("Device property modified called on :"+device+"\n property :"+propertyName);
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
