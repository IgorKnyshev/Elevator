package by.training.entity;

import by.training.utility.TransportationState;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class DispatchArrivalContainerTest {
	private DispatchArrivalContainer dispatchArrivalContainer;
	private Passenger pas1;
	private Passenger pas2;
	private Passenger pas3;

	@Before
	public void initialize() {
		pas1 = new Passenger(1, TransportationState.NOT_STARTED, 1, 2);
		pas2 = new Passenger(2, TransportationState.NOT_STARTED, 1, 3);
		pas3 = new Passenger(3, TransportationState.NOT_STARTED, 2, 3);
		List<Story> dispatchContainer = new ArrayList<>();
		List<Story> arrivalContainer = new ArrayList<>();
		Story story1 = new Story();
		Story story2 = new Story();
		story1.addPassenger(pas1);
		story1.addPassenger(pas2);
		story2.addPassenger(pas3);
		dispatchContainer.add(story1);
		dispatchContainer.add(story2);
		arrivalContainer.add(story2);
		arrivalContainer.add(story1);
		dispatchArrivalContainer = new DispatchArrivalContainer(dispatchContainer, arrivalContainer, 2);
	}

	@Test
	public void getDispatchContainer() throws Exception {
		List<Story> storyDispatchList = dispatchArrivalContainer.getDispatchContainer();
		assertThat(storyDispatchList, hasSize(2));
		assertThat(storyDispatchList.get(0).getStoryPassengers(), allOf(hasItems(pas1, pas2), not(hasItem(pas3))));
		assertThat(storyDispatchList.get(1).getStoryPassengers(), allOf(not(hasItems(pas1, pas2)), hasItem(pas3)));
	}

	@Test
	public void getArrivalContainer() throws Exception {
		List<Story> storyArrivalList = dispatchArrivalContainer.getDispatchContainer();
		assertThat(storyArrivalList, hasSize(2));
		assertThat(storyArrivalList.get(0).getStoryPassengers(), allOf(hasItems(pas1, pas2), not(hasItem(pas3))));
		assertThat(storyArrivalList.get(1).getStoryPassengers(), allOf(not(hasItems(pas1, pas2)), hasItem(pas3)));
	}

}