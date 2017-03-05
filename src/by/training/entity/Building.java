package by.training.entity;

import by.training.core.Generator;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * General class for Building.
 * It contains elevator and dispatchArrival
 * containers. Generator is used to generate
 * containers and passengers.
 *
 * @author  Igor Knyshev <igorknyshev@gmail.com>
 * @see     by.training.entity.ElevatorContainer
 * @see     by.training.entity.DispatchArrivalContainer
 *
 */
public class Building {
	private List<Passenger> passengerList;
	private ElevatorContainer elevatorContainer;
	private DispatchArrivalContainer dispatchArrivalContainer;
	private Generator generator;
	private CountDownLatch leaveLatch;
	private CountDownLatch enterLatch;

	/**
	 * Specific constructor for building
	 * @param gnr custom generator
	 * @throws IOException can't read properties
	 */
	public Building(final Generator gnr) throws IOException {
		generator = gnr;
		passengerList = generator.generatePassengers();
		elevatorContainer = new ElevatorContainer(generator.getElevatorCapacity(), generator.getStoriesNumber());
		dispatchArrivalContainer = new DispatchArrivalContainer(generator.generateDispatchContainers(passengerList),
				                                                generator.generateArrivalContainers(),
				                                                generator.getStoriesNumber());
	}


	public Generator getGenerator() {
		return generator;
	}

	public List<Passenger> getPassengerList() {
		return passengerList;
	}

	public ElevatorContainer getElevatorContainer() {
		return elevatorContainer;
	}

	public void setLeaveLatch(final int count) {
		leaveLatch = new CountDownLatch(count);
	}

	public CountDownLatch getLeaveLatch() {
		return leaveLatch;
	}

	public void setEnterLatch(final int count) {
		this.enterLatch = new CountDownLatch(count);
	}

	public CountDownLatch getEnterLatch() {
		return enterLatch;
	}

	/**
	 * Particular story of dispatch container
	 * @param story number of dispatch story
	 * @return story object at concrete story number
	 * from dispatch container
	 */
	public Story getDispatchStory(final int story) {
		return dispatchArrivalContainer.getDispatchContainer().get(story);
	}

	/**
	 * Particular story of arrival container
	 * @param story number of dispatch story
	 * @return story object at concrete story number
	 * from arrival container
	 */
	public Story getArrivalStory(final int story) {
		return dispatchArrivalContainer.getArrivalContainer().get(story);
	}
}
