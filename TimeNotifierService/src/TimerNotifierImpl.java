

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import manager.service.provided.it.ManagerProviderIt;
import fr.liglab.adele.icasa.clockservice.Clock;
import fr.liglab.adele.icasa.service.scheduler.PeriodicRunnable;

public class TimerNotifierImpl implements PeriodicRunnable {

	/** Field for managerProvider dependency */
	private ManagerProviderIt managerProvider;
	/** Field for clock dependency */
	private Clock clock;

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("TimeNotifer provided service is stopping !!!");
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("TimeNotifer provided service is starting !!!");
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
	}

	@Override	
	public void run() {
		//Environ toutes les 10 minutes on push un message à voir avec quel facteur de temps on gère
		int minutes = (int) (clock.getElapsedTime()/1000/60);
		if (minutes % 10 < 2 && clock.getElapsedTime() != 0) {
			System.out.println("TimerNotifer service : push ten minutes to manager");
			managerProvider.pushTenMinutes();
		}
	}

	@Override
	public long getPeriod() {
		return 1000;
	}

	@Override
	public TimeUnit getUnit() {
		return TimeUnit.MILLISECONDS;
	}


}
