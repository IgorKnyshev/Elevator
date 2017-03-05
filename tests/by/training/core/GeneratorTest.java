package by.training.core;

import by.training.entity.Passenger;
import by.training.entity.Story;
import by.training.utility.TransportationState;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/*
 * 100% methods, 97%(71/73) lines coverage
 */
public class GeneratorTest {
	private Generator generator;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void wrongStoriesNumber() throws Exception {
		thrown.expect(IOException.class);
		thrown.expectMessage("elevatorCapacity must be greater or equal to 2");
		generator = new Generator("tests/WrongStoriesNumber.property");
	}

	@Test
	public void wrongCapacityValue() throws Exception {
		thrown.expect(IOException.class);
		thrown.expectMessage("elevatorCapacity must be greater or equal to 1");
		generator = new Generator("tests/WrongCapacityValue.property");
	}

	@Test
	public void wrongPasNumber() throws Exception {
		thrown.expect(IOException.class);
		thrown.expectMessage("passengersNumber must be greater or equal to 1");
		generator = new Generator("tests/WrongPasNumber.property");
	}

	@Test(expected = NumberFormatException.class)
	public void wrongValuesDataFile() throws Exception {
		generator = new Generator("tests/WrongDataConfig.property");
	}

	@Test
	public void testGetters() throws Exception {
		generator = new Generator("tests/CorrectDataConfig.property");
		assertThat(8, is(equalTo(generator.getStoriesNumber())));
		assertThat(1, is(equalTo(generator.getElevatorCapacity())));
		assertThat(10, is(equalTo(generator.getPassengersNumber())));
	}

	@Test
	public void testGeneratePassengers() throws Exception {
		generator = new Generator("tests/CorrectDataConfig.property");
		List<Passenger> passengerList = generator.generatePassengers();
		assertThat(passengerList, hasSize(10));
		assertThat(passengerList, everyItem(instanceOf(Passenger.class)));
		for (Passenger pas : passengerList) {
			assertThat(pas.getDestinationStory(), allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(8)));
			assertThat(pas.getInitialStory(), allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(8)));
			assertThat(pas.getState(), is(equalTo(TransportationState.NOT_STARTED)));
			assertThat(pas.getPassengerID(), allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(10)));
		}
	}

	@Test
	public void testDispatchContainers() throws Exception {
		generator = new Generator("tests/CorrectDataConfig.property");
		List<Passenger> passengerList = generator.generatePassengers();
		List<Story> dispatchArrivalContainer = generator.generateDispatchContainers(passengerList);
		assertThat(dispatchArrivalContainer, hasSize(8));
		int totalSize = 0;
		for (Story story : dispatchArrivalContainer) {
			totalSize += story.size();
		}
		assertThat(totalSize, is(equalTo(10)));
	}

	@Test
	public void testArrivalContainers() throws Exception {
		generator = new Generator("tests/CorrectDataConfig.property");
		assertThat(generator.generateArrivalContainers(), hasSize(8));
	}

}