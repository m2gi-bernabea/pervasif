package time.service.provided.impl;
import time.service.provided.it.MomentOfTheDayIt;
import fr.liglab.adele.icasa.clockservice.Clock;

public class HourGetterImpl implements MomentOfTheDayIt {

	/** Field for clock dependency */
	private Clock clock;

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("HourGetter provided service is stopping !!!");
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("HourGetter provided service is starting !!!");
	}

	@Override
	public Integer getCurrentHourOfTheDay() {
		return (int) (clock.getElapsedTime() / 1000 / 60 / 60 % 24);
	}

}
