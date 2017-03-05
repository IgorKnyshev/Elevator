package by.training.entity;

import by.training.utility.Container;
import by.training.utility.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * General class for elevator container
 * with some move logic and enter/leave logic.
 * Elevator need to move passengers from initial
 * story to destination story.
 *
 * @author  Igor Knyshev <igorknyshev@gmail.com>
 * @see     by.training.utility.Container
 * @see     by.training.core.Controller
 *
 */
public class ElevatorContainer extends Container {
	private List<Passenger> elevatorPassengers;
	private int currentStory;
	private final int storage;
	private Direction direction;

	/**
	 * Class constructor specifying storage of elevator,
	 * total story numbers, initial move direction(UP) and
	 * initial story(1).
	 * @param storage  total storage of elevator
	 * @param storyNumbers  total amount of stories
	 * @throws IllegalArgumentException if
	 * storage or story number are wrong.
	 */
	public ElevatorContainer(final int storage, final int storyNumbers) {
		if (storage < 1 || storyNumbers < 2) {
			throw new IllegalArgumentException("Elevator storage must be greater than 0 and storyNumbers must be great than 1");
		}
		this.currentStory = 1;
		this.storyNumbers = storyNumbers;
		this.storage = storage;
		this.elevatorPassengers = new ArrayList<>(storage);
		this.direction = Direction.UP;
	}

	public List<Passenger> getElevatorPassengers() {
		return elevatorPassengers;
	}

	public int getCurrentStory() {
		return currentStory;
	}

	public int getStorage() {
		return storage;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(final Direction direction) {
		this.direction = direction;
	}

	/**
	 * Method verify that i can enter in elevator.
	 * @return true if there are enough storage for one more passenger
	 */
	public boolean letMeEnter() {
		return elevatorPassengers.size() < storage;
	}

	/**
	 * Method allow passenger to enter elevator.
	 * Logging boarding of passenger.
	 * @param pas passenger that will enter elevator
	 */
	public void enterElevator(final Passenger pas) {
		log.info("BOARDING OF PASSENGER ( ID = " + pas.getPassengerID() + " on story-" + currentStory + ")");
		if (elevatorPassengers.size() < storage) {
			elevatorPassengers.add(pas);
		}
	}

	/**
	 * Method allow passenger to leave elevator.
	 * Logging deboarding of passenger.
	 * @param pas passenger that will leave elevator
	 */
	public void leaveElevator(final Passenger pas) {
		log.info("DEBOARDING OF PASSENGER ( ID = " + pas.getPassengerID() + " on story-" + currentStory + ")");
		elevatorPassengers.remove(pas);
	}

	/**
	 * Move elevator to next up story
	 * and log move of elevator.
	 * @throws IllegalStateException if
	 *  elevator can't move up(above max story)
	 */
	public void up() {
		if (storyNumbers > currentStory) {
			log.info("MOVING ELEVATOR (from story-" + currentStory + " to story-" + (currentStory + 1) + ")");
			currentStory++;
		} else {
			throw new IllegalStateException("Elevator can't move");
		}
	}

	/**
	 * Move elevator to next down story
	 * and log move of elevator.
	 * @throws IllegalStateException if
	 *  elevator can't move down(below 1 story)
	 */
	public void down() {
		if (currentStory > 1) {
			log.info("MOVING ELEVATOR (from story-" + currentStory + " to story-" + (currentStory - 1) + ")");
			currentStory--;
		} else {
			throw new IllegalStateException("Elevator can't move");
		}
	}

	/**
	 * Sleep passengers in elevator
	 */
	public synchronized void waitInElevator() {
		try {
			wait();
		} catch (InterruptedException e) {
			throw new RuntimeException("Thread has been interrupted..." + e);
		}
	}

	/**
	 * Notify all passengers in elevator
	 */
	public synchronized void notifyElevatorPassengers() {
		notifyAll();
	}

}
