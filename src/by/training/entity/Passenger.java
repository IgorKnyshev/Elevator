package by.training.entity;

import by.training.utility.TransportationState;

/**
 * Class that describes passenger.
 *
 * @author  Igor Knyshev <igorknyshev@gmail.com>
 * @see     by.training.core.Controller
 */
public class Passenger {
	private final int passengerID;
	private TransportationState state;
	private final int initialStory;
	private final int destinationStory;

	/**
	 * Class constructor specifying passenger's unique ID,
	 * transportation state, initial and destination story.
	 * @param passengerID  passenger's unique ID
	 * @param state  passenger's current Transportation State
	 * @param initialStory  passenger's initial story in Dispatch Container
	 * @param destinationStory  passenger's destination story in Arrival Container
	 */
	public Passenger(final Integer passengerID, final TransportationState state, final Integer initialStory, final Integer destinationStory) {
		if (passengerID < 1) {
			throw  new IllegalArgumentException("Wrong passenger ID");
		}
		if (initialStory < 1) {
			throw  new IllegalArgumentException("Wrong passenger initialStory");
		}
		if (destinationStory < 1) {
			throw  new IllegalArgumentException("Wrong passenger destinationStory");
		}
		this.passengerID = passengerID;
		this.state = state;
		this.initialStory = initialStory;
		this.destinationStory = destinationStory;
	}

	public int getInitialStory() {
		return initialStory;
	}

	public int getPassengerID() {
		return passengerID;
	}

	public TransportationState getState() {
		return state;
	}

	public void setState(final TransportationState state) {
		this.state = state;
	}

	public int getDestinationStory() {
		return destinationStory;
	}

	/**
	 * Class method with information about passenger.
	 * @return String value with information about passenger
	 */
	@Override
	public String toString() {
		return "ID = " + passengerID + ". Initial = " + initialStory + ". Destination = "
				+ destinationStory + ". State = " + state + ";";
	}

}
