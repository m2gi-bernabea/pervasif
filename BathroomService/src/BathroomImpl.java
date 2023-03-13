
import fr.liglab.adele.icasa.device.DeviceListener;
import fr.liglab.adele.icasa.device.GenericDevice;
import fr.liglab.adele.icasa.device.presence.PresenceSensor;
import fr.liglab.adele.icasa.device.security.Siren;
import java.util.Map;

public class BathroomImpl implements DeviceListener {

	/** Field for sirens dependency */
	private Siren[] sirens;
	/** Field for presenceSensors dependency */
	private PresenceSensor[] presenceSensors;

	/** Bind Method for sirens dependency */
	public void bindSiren(Siren siren, Map properties) {
		siren.addListener(this);
		System.out.println("bind siren " + siren.getSerialNumber());
	}

	/** Unbind Method for sirens dependency */
	public void unbindSiren(Siren siren, Map properties) {
		siren.removeListener(this);
		System.out.println("unbind siren " + siren.getSerialNumber());
	}

	/** Bind Method for presenceSensors dependency */
	public synchronized void bindPresenceSensor(PresenceSensor presenceSensor, Map properties) {
		presenceSensor.addListener(this);
		System.out.println("bind presence sensor " + presenceSensor.getSerialNumber());
	}

	/** Unbind Method for presenceSensors dependency */
	public synchronized void unbindPresenceSensor(PresenceSensor presenceSensor, Map properties) {
		presenceSensor.removeListener(this);
		System.out.println("Unbind presence sensor " + presenceSensor.getSerialNumber());
	}

	/** Component Lifecycle Method */
	public void stop() {
		for (Siren siren : sirens) {
			siren.removeListener(this);
		}
		for (PresenceSensor sensor : presenceSensors) {
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

	public void deviceEvent(GenericDevice arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	public void devicePropertyAdded(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * The name of the LOCATION property
	 */
	public static final String LOCATION_PROPERTY_NAME = "Location";

	/**
	 * The name of the location for unknown value
	 */
	public static final String LOCATION_UNKNOWN = "unknown";

	public void devicePropertyModified(GenericDevice device, String propertyName,
			Object oldValue, Object newValue) {
		System.out.println("device property modified called ");
		if (device instanceof PresenceSensor) {
			PresenceSensor changingSensor = (PresenceSensor) device;
			// check the change is related to presence sensing
			if (propertyName.equals(PresenceSensor.PRESENCE_SENSOR_SENSED_PRESENCE)) {
				// get the location where the sensor is:
				String detectorLocation = (String) changingSensor.getPropertyValue(LOCATION_PROPERTY_NAME);
				System.out.println("presence sensor detected presence in " + detectorLocation);
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
