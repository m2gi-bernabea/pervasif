

import java.util.Timer;
import java.util.TimerTask;

import manager.service.provided.it.ManagerProviderIt;
import fr.liglab.adele.icasa.clockservice.Clock;

public class TimerNotifierImpl {

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
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				//Environ toutes les 10 minutes on push un message à voir avec quel facteur de temps on gère
				System.out.println("TimeNotifer provided service : check time 10 minutes are elapsed?");
				if (clock.getElapsedTime() / 1000 / 60 % 10 == 0) {
					System.out.println("push ten minutes to manager");
					managerProvider.pushTenMinutes();
				}
			}
		}, 1000);
	}

}
