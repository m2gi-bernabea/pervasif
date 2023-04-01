package listener.service.impl;

import fr.liglab.adele.icasa.device.DeviceListener;
import fr.liglab.adele.icasa.device.GenericDevice;
import fr.liglab.adele.icasa.device.light.Photometer;
import fr.liglab.adele.icasa.device.motion.MotionSensor;
import fr.liglab.adele.icasa.device.presence.PresenceSensor;
import fr.liglab.adele.icasa.device.temperature.Thermometer;
import java.util.Map;
import manager.service.provided.it.ContextProviderIt;
import manager.service.provided.it.ManagerProviderIt;

public class ListenerSensorImpl implements DeviceListener {

	/** Field for photometerSensors dependency */
	private Photometer[] photometerSensors;
	/** Field for motionSensors dependency */
	private MotionSensor[] motionSensors;
	/** Field for presenceSensors dependency */
	private PresenceSensor[] presenceSensors;
	/** Field for thermometerSensors dependency */
	private Thermometer[] thermometerSensors;
	/** Field for contextProvider dependency */
	private ContextProviderIt contextProvider;
	/** Field for managerProvider dependency */
	private ManagerProviderIt managerProvider;

	/** Bind Method for thermometerSensors dependency */
	public void bindThermometerSensor(Thermometer thermometer, Map properties) {
		thermometer.addListener(this);
		System.out.println("bind thermometer sensor "
				+ thermometer.getSerialNumber());
	}

	/** Unbind Method for thermometerSensors dependency */
	public void unbindThermometerSensor(Thermometer thermometer, Map properties) {
		thermometer.removeListener(this);
		System.out.println("unbind thermometer sensor "
				+ thermometer.getSerialNumber());
	}

	/** Bind Method for presenceSensors dependency */
	public void bindPresenceSensor(PresenceSensor presenceSensor, Map properties) {
		presenceSensor.addListener(this);
		System.out.println("bind presence sensor "
				+ presenceSensor.getSerialNumber());
	}

	/** Unbind Method for presenceSensors dependency */
	public void unbindPresenceSensor(PresenceSensor presenceSensor,
			Map properties) {
		presenceSensor.removeListener(this);
		System.out.println("Unbind presence sensor "
				+ presenceSensor.getSerialNumber());
	}

	/** Bind Method for photometerSensors dependency */
	public void bindPhotometerSensors(Photometer photometer, Map properties) {
		photometer.addListener(this);
		System.out.println("bind photometer " + photometer.getSerialNumber());
	}

	/** Unbind Method for photometerSensors dependency */
	public void unbindPhotometerSensors(Photometer photometer, Map properties) {
		photometer.removeListener(this);
		System.out.println("unbind photometer " + photometer.getSerialNumber());
	}

	/** Bind Method for motionSensors dependency */
	public void bindMotionSensor(MotionSensor motionSensor, Map properties) {
		motionSensor.addListener(this);
		System.out.println("unbind motionSensor "
				+ motionSensor.getSerialNumber());
	}

	/** Unbind Method for motionSensors dependency */
	public void unbindMotionSensor(MotionSensor motionSensor, Map properties) {
		motionSensor.removeListener(this);
		System.out.println("unbind motionSensor "
				+ motionSensor.getSerialNumber());
	}

	/** Component Lifecycle Method */
	public void stop() {
		for (PresenceSensor sensor : presenceSensors) {
			sensor.removeListener(this);
		}
		for (MotionSensor sensor : motionSensors) {
			sensor.removeListener(this);
		}
		for (Photometer sensor : photometerSensors) {
			sensor.removeListener(this);
		}
		for (Thermometer sensor : thermometerSensors) {
			sensor.removeListener(this);
		}
		System.out.println("Component is stopping...");
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("Component is starting...");
	}

	public void deviceAdded(GenericDevice arg0) {
		// TODO Auto-generated method stub

	}

	public void deviceEvent(GenericDevice device, Object value) {
		String detectorLocation = (String) device.getPropertyValue("Location");
		System.out.println("movement detected in " + detectorLocation);
		System.out.println("Ping du manager");
		managerProvider.movementIn(detectorLocation);

	}

	public void devicePropertyAdded(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void devicePropertyModified(GenericDevice device,
			String propertyName, Object oldValue, Object newValue) {

		System.out.println("devicePropertyModified called on device: " + device
				+ " with property: " + propertyName + " old value: " + oldValue
				+ " and new value: " + newValue);

		if (device instanceof PresenceSensor) {
			if (propertyName
					.equals(PresenceSensor.PRESENCE_SENSOR_SENSED_PRESENCE)) {
				// get the location where the sensor is:
				String detectorLocation = (String) device
						.getPropertyValue("Location");
				if((Boolean) newValue){
					System.out.println("presence detected in " + detectorLocation);
					managerProvider.peopleIn(detectorLocation);
				} else {
					System.out.println("no more presence in " +detectorLocation);
					managerProvider.peopleOut(detectorLocation);
				}
			}
		}

		if (device instanceof Thermometer) {
			System.out.println("nouvelle température");
			System.out.println("Temp : "+ ((Thermometer) device).getTemperature());
		}

	}

	public void devicePropertyRemoved(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public void deviceRemoved(GenericDevice arg0) {
		// TODO Auto-generated method stub

	}

}
