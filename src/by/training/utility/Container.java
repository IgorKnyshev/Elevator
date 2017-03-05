package by.training.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * General abstract class for Elevator,
 * Dispatch, Arrival containers.
 *
 * @author  Igor Knyshev <igorknyshev@gmail.com>
 * @see     by.training.entity.ElevatorContainer
 * @see     by.training.entity.DispatchArrivalContainer
 */
public abstract class Container {
	/**
	 * General logger for elevator/dispatch/arrival containers
	 */
	protected static final Logger log = LogManager.getLogger("TRANSPORTATION");

	protected int storyNumbers;

	public int getStoryNumbers() {
		return storyNumbers;
	}
}
