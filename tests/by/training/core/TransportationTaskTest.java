package by.training.core;

import by.training.entity.Building;
import by.training.entity.Passenger;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/*
 * 100% methods, 81% lines coverage
 */
public class TransportationTaskTest {
	@Test
	public void testSimpleThreads() throws Exception {
		final String file = "tests/CorrectShortConfig.property";
		Generator gnr = new Generator(file);
		Building building = new Building(gnr);
		Controller controller = new Controller(building);
		ExecutorService pool = Executors.newFixedThreadPool(gnr.getPassengersNumber());
		for (Passenger pas : building.getPassengerList()) {
			TransportationTask task = new TransportationTask(pas, building, controller);
			pool.execute(task);
		}
		pool.shutdown();
		Thread controllerThread = new Thread(controller);
		controllerThread.start();
	}

}