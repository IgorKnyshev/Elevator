import by.training.core.Controller;
import by.training.core.Generator;
import by.training.core.TransportationTask;
import by.training.entity.Building;
import by.training.entity.Passenger;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class that run elevator application
 *
 * @author  Igor Knyshev <igorknyshev@gmail.com>
 * @see     by.training.core.Controller
 * @see     by.training.core.Generator
 * @see     by.training.core.TransportationTask
 */
class Runner {
	/**
	 * Runner create new generator to
	 * read data from file and generate
	 * containers. Thread pool run threads
	 * for each TransportationTask(for each
	 * passenger).
	 * Controller thread start.
	 * @param args the command line arguments
	 * @throws IOException if generator can't read file
	 */
	public static void main(final String[] args) throws IOException {
		final String file = "src/config.property";
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
