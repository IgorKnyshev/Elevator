package by.training.core;

import by.training.entity.*;
import by.training.utility.Direction;
import by.training.utility.TransportationState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that describes main controller
 * logic for correct passengers transportation.
 *
 * @author  Igor Knyshev <igorknyshev@gmail.com>
 * @see     by.training.core.TransportationTask
 */
public class Controller implements Runnable {
	private static final Logger log = LogManager.getLogger("Controller");
	private Building building;

	/**
	 * Class constructor specifying custom controller.
	 * Dispatch container contains passengers from passengerList.
	 * Arrival container is empty.
	 * Elevator container is empty. Constrained capacity and
	 * story numbers.
	 * @param building generated building
	 * @throws IOException if generator couldn't read data from file
	 */
	public Controller(final Building building) throws IOException {
		this.building = building;
		log.info("TRANSPORTATION WILL START SOON");
	}

	/**
	 * Move elevator non-stop from 1 to max story and back.
	 */
	public void moveElevator() throws InterruptedException {
		if (Direction.UP.equals(building.getElevatorContainer().getDirection())) {
			if (building.getElevatorContainer().getCurrentStory() < building.getElevatorContainer().getStoryNumbers()) {
				building.getElevatorContainer().up();
			} else {
				building.getElevatorContainer().setDirection(Direction.DOWN);
			}
		} else {
			if (building.getElevatorContainer().getCurrentStory() > 1) {
				building.getElevatorContainer().down();
			} else {
				building.getElevatorContainer().setDirection(Direction.UP);
			}
		}
	}

	/**
	 * Check elevator and passenger direction of move
	 * @return true if passenger and elevator move in same direction
	 */
	private boolean correctDirection(final Passenger pas) {
		return (Direction.UP.equals(building.getElevatorContainer().getDirection()) && pas.getInitialStory() < pas.getDestinationStory())
				|| (Direction.DOWN.equals(building.getElevatorContainer().getDirection()) && pas.getInitialStory() > pas.getDestinationStory());
	}

	/**
	 * Verify passenger can enter elevator(same move direction,
	 * elevator isn't full). Leave dispatch container ->
	 * enter elevator.
	 * @return true if passenger can enter in elevator.
	 */
	public synchronized boolean iCanEnter(final Passenger passenger) {
		boolean canEnter = false;
		if (building.getElevatorContainer().letMeEnter() && correctDirection(passenger) && building.getElevatorContainer().getCurrentStory() == passenger.getInitialStory()) {
			canEnter = true;
			building.getDispatchStory(building.getElevatorContainer().getCurrentStory() - 1).removePassenger(passenger);
			building.getElevatorContainer().enterElevator(passenger);
			building.getEnterLatch().countDown();
		}
		return canEnter;
	}

	/**
	 * Verify passenger can leave elevator (elevator
	 * at destination story of passenger) and leave elevator->
	 * enter arrival container.
	 * @return true if passenger can leave elevator.
	 */
	public synchronized boolean iCanLeave(final Passenger passenger) {
		boolean canLeave = false;
		if (passenger.getDestinationStory() == building.getElevatorContainer().getCurrentStory()) {
			canLeave = true;
			building.getElevatorContainer().leaveElevator(passenger);
			building.getArrivalStory(building.getElevatorContainer().getCurrentStory() - 1).addPassenger(passenger);
			building.getLeaveLatch().countDown();
		}
		return canLeave;
	}

	/**
	 * Count passengers that can leave elevator
	 * at current story
	 * @return number of enter passengers
	 */
	private int countCanLeave() {
		int canLeave = 0;
		List<Passenger> passengerList = new ArrayList<>();
		passengerList.addAll(building.getElevatorContainer().getElevatorPassengers());
		for (Passenger pas : passengerList) {
			if (pas.getDestinationStory() == building.getElevatorContainer().getCurrentStory()) {
				canLeave++;
			}
		}
		return canLeave;
	}

	/**
	 * Count passengers that can enter elevator
	 * at current story.
	 * @return number of leave passengers
	 */
	private int countCanEnter() {
		int canEnterInElevator = 0;
		int elevatorFreePlaces =  building.getElevatorContainer().getStorage() - building.getElevatorContainer().getElevatorPassengers().size();
		List<Passenger> passengerList = new ArrayList<>();
		passengerList.addAll(building.getDispatchStory(building.getElevatorContainer().getCurrentStory() - 1).getStoryPassengers());
		for (Passenger pas : passengerList) {
			if (pas.getInitialStory() == building.getElevatorContainer().getCurrentStory() && correctDirection(pas)) {
				canEnterInElevator++;
			}
		}
		if (canEnterInElevator < elevatorFreePlaces) {
			return canEnterInElevator;
		} else {
			return elevatorFreePlaces;
		}
	}

	/**
	 * Transportation tasks has been finished if:
	 *  - arrived passengers number are equal to total number
	 * of passengers
	 *  - number of passengers in dispatch container are 0
	 *  - number of passengers is elevator container are 0
	 *  - all passengers has COMPLETED state
	 * @return true if all transportation tasks has been finished.
	 */
	private boolean isFinished() {
		boolean isFinished = false;
		int arrivedNumber  = 0;
		int dispatchNumber = 0;
		int elevatorNumber = building.getElevatorContainer().getElevatorPassengers().size();
		int totalNumber = building.getGenerator().getPassengersNumber();
		for (int i = 0; i < building.getGenerator().getStoriesNumber(); i++) {
			dispatchNumber += building.getDispatchStory(i).size();
		}
		for (int i = 0; i < building.getGenerator().getStoriesNumber(); i++) {
			for (int j = 0; j < building.getArrivalStory(i).size(); j++) {
				Passenger pas = building.getArrivalStory(i).getStoryPassengers().get(j);
				if (pas.getDestinationStory() == i + 1 && TransportationState.COMPLETED.equals(pas.getState())) {
					arrivedNumber++;
				}
			}
		}

		if (arrivedNumber == totalNumber && dispatchNumber == 0 && elevatorNumber == 0) {
			isFinished = true;
			log.info("TRANSPORTATION COMPLETE");
		}
		return isFinished;
	}

	/**
	 * Signal all passengers at elevator to wake up and
	 * wait until all passengers that can leave elevator
	 * will leave it.
	 * Signal all passengers on current story to
	 * wake up and wait until all passengers that can enter
	 * elevator will enter it.
	 * Move elevator to next story.
	 */
	@Override
	public void run() {
		try {
			while (!isFinished()) {
				building.setLeaveLatch(countCanLeave());
				building.getElevatorContainer().notifyElevatorPassengers();
				building.getLeaveLatch().await();

				building.setEnterLatch(countCanEnter());
				building.getDispatchStory(building.getElevatorContainer().getCurrentStory() - 1).notifyStoryPassengers();
				building.getEnterLatch().await();

				moveElevator();
			}
		} catch (InterruptedException e) {
			throw new RuntimeException("Thread has been interrupted..." + e);
		}
	}

}

