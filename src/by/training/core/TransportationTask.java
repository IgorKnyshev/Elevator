package by.training.core;

import by.training.entity.Building;
import by.training.entity.Passenger;
import by.training.utility.TransportationState;

/**
 * Class that describe Transportation Task for
 * every passenger.
 *
 * @author  Igor Knyshev <igorknyshev@gmail.com>
 * @see     by.training.core.Controller
 */
public class TransportationTask implements Runnable {
	private Passenger passenger;
	private Building building;
	private Controller controller;

	/**
	 * Class constructor specifying Transportation
	 * Task for every passenger.
	 * @param passenger random passenger with custom
	 *  transportation task
	 * @param building building with passengers and elevator
	 * @param controller controller for passengers
	 */
	public TransportationTask(final Passenger passenger, final Building building, final Controller controller) {
		this.passenger = passenger;
		this.building = building;
		this.controller = controller;
	}

	/**
	 * This method run transportation task
	 * for one passenger. Passenger wait until
	 * elevator come to his store and enter in
	 * elevator if he can do it. Wait in elevator
	 * until it comes to passenger destination story.
	 */
	@Override
	public void run() {
		do {
			building.getDispatchStory(passenger.getInitialStory() - 1).waitOnStory();
		} while (!controller.iCanEnter(passenger));
		passenger.setState(TransportationState.IN_PROGRESS);

		do {
			building.getElevatorContainer().waitInElevator();
		} while (!controller.iCanLeave(passenger));
		passenger.setState(TransportationState.COMPLETED);
	}

}


