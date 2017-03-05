package by.training.utility;

/**
 * Enum with all possible passenger's
 * transportation states.
 *
 * @author  Igor Knyshev <igorknyshev@gmail.com>
 * @see     by.training.core.TransportationTask
 */
public enum TransportationState {
	/**
	 * NOT_STARTED - passenger didn't start
	 * transportation(in Dispatch Container)
	 */
	NOT_STARTED,
	/**
	 * IN_PROGRESS - passenger has already
	 * started transportation (in Elevator Container)
	 */
	IN_PROGRESS,
	/**
	 * COMPLETED - passenger has already
	 * finished transportation (in Arrival Container)
	 */
	COMPLETED,
	/**
	 * ABORTED - transportation has been aborted
	 */
	ABORTED
}
