package by.training.entity;

import by.training.utility.TransportationState;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/*
 * 100% methods, 100% lines coverage
 */
public class PassengerTest {
	private Passenger pas;

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testWrongPassengerID() throws Exception {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Wrong passenger ID");
		pas = new Passenger(0, TransportationState.COMPLETED, 1, 2);
	}

	@Test
	public void testWrongPassengerInitialStory() throws Exception {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Wrong passenger initialStory");
		pas = new Passenger(1, TransportationState.COMPLETED, -1, 2);
	}

	@Test
	public void testWrongPassengerDestinationStory() throws Exception {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Wrong passenger destinationStory");
		pas = new Passenger(2, TransportationState.COMPLETED, 1, 0);
	}

	@Test
	public void testAllGettersAndSetters() throws Exception {
		pas = new Passenger(99, TransportationState.COMPLETED, 1, 2);
		assertThat(99, is(equalTo(pas.getPassengerID())));
		assertThat(TransportationState.COMPLETED, is(equalTo(pas.getState())));
		assertThat(1, is(equalTo(pas.getInitialStory())));
		assertThat(2, is(equalTo(pas.getDestinationStory())));
		pas.setState(TransportationState.ABORTED);
		assertThat(TransportationState.ABORTED, is(equalTo(pas.getState())));
	}

	@Test
	public void testToString() throws Exception {
		pas = new Passenger(10, TransportationState.COMPLETED, 5, 6);
		String passengerString = "ID = 10. Initial = 5. Destination = 6. State = COMPLETED;";
		assertThat(passengerString, is(equalTo(pas.toString())));
	}
}