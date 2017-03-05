package by.training.entity;

import by.training.utility.Container;

import java.util.List;

/**
 * General class for DispatchArrival container.
 * DispatchArrival container contains list of
 * stories
 *
 * @author  Igor Knyshev <igorknyshev@gmail.com>
 * @see     by.training.utility.Container
 */

public class DispatchArrivalContainer extends Container {
	private List<Story> dispatchContainer;
	private List<Story> arrivalContainer;

	/**
	 * Class constructor specifying custom container.
	 * Dispatch container contains passengers at their
	 * initial story.
	 * Arrival container will contain passengers at their
	 * destination story.
	 * @param dispatchContainer custom dispatchContainer
	 * @param arrivalContainer  custom arrivalContainer
	 * @param storyNumber       total amount of stories
	 */
	public DispatchArrivalContainer(final List<Story> dispatchContainer, final List<Story> arrivalContainer, final int storyNumber) {
		this.dispatchContainer = dispatchContainer;
		this.arrivalContainer = arrivalContainer;
		this.storyNumbers = storyNumber;
	}

	/**
	 * Particular dispatch container
	 * @return dispatch container object
	 */
	public List<Story> getDispatchContainer() {
		return dispatchContainer;
	}

	/**
	 * Particular arrival container
	 * @return arrival container object
	 */
	public List<Story> getArrivalContainer() {
		return arrivalContainer;
	}


}
