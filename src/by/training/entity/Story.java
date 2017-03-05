package by.training.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for dispatchArrival/Elevator
 * containers stories
 *
 * @author  Igor Knyshev <igorknyshev@gmail.com>
 */
public class Story {
	private List<Passenger> story;

	/**
	 * Class constructor specifying custom empty
	 * story.
	 */
	public Story() {
		List<Passenger> list = new ArrayList<>();
		this.story = list;
	}

	/**
	 * Class constructor specifying custom story
	 *  with passengers in it.
	 * @param passengerList list of story passengers
	 */
	public Story(final List<Passenger> passengerList) {
		this.story = passengerList;
	}

	/**
	 * Story contains list of passengers
	 * @return list of story passengers
	 */
	public List<Passenger> getStoryPassengers() {
		return story;
	}

	/**
	 * Add passenger to story
	 * @param pas passenger that will be added to story
	 */
	public void addPassenger(final Passenger pas) {
		story.add(pas);
	}

	/**
	 * Synchronized remove passenger from story
	 * @param pas passenger that will be removed from story
	 */
	public void removePassenger(final Passenger pas) {
		story.remove(pas);
	}

	/**
	 * Method return total amount of passengers
	 * at story
	 * @return size of story
	 */
	public int size() {
		return story.size();
	}

	/**
	 * Sleep passengers on specific story
	 */
	public synchronized void waitOnStory() {
		try {
			wait();
		} catch (InterruptedException e) {
			throw new RuntimeException("Thread has been interrupted..." + e);
		}
	}

	/**
	 * Notify all passengers on current story
	 */
	public synchronized void notifyStoryPassengers() {
		notifyAll();
	}

}
