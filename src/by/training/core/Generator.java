package by.training.core;

import by.training.entity.Passenger;
import by.training.entity.Story;
import by.training.utility.TransportationState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * Class generates random passengers, dispatch container with
 * that passengers and empty arrival container.
 *
 * @author  Igor Knyshev <igorknyshev@gmail.com>
 * @see     by.training.core.Controller
 */
public class Generator {
	private static final Logger log = LogManager.getLogger("Generator");
	private int storiesNumber;
	private int passengersNumber;
	private int elevatorCapacity;

	/**
	 * Class constructor calls private method
	 * getData() to read data from file and set
	 * instance variables.
	 * @param dataFile data will be read(read - read - read)
	 * from file
	 */
	public Generator(final String dataFile) throws IOException {
		readData(dataFile);
	}

	public int getStoriesNumber() {
		return storiesNumber;
	}

	public int getPassengersNumber() {
		return passengersNumber;
	}

	public int getElevatorCapacity() {
		return elevatorCapacity;
	}

	/**
	 * Method generated list of passengers via reflection
	 * with unique ID, not equal initial and destination
	 * stories and NOT_STARTED Transportation State.
	 * @return list of generated passengers
	 */
	public List<Passenger> generatePassengers() {
		List<Passenger> container = new ArrayList<>();
		Random random = new Random();
		int id = 0;
		try {
			log.info("GENERATING NEW PASSENGERS:");
			while (id < passengersNumber) {
				id++;
				Constructor<Passenger> constructor = Passenger.class.getDeclaredConstructor(Integer.class, TransportationState.class, Integer.class, Integer.class);
				constructor.setAccessible(true);
				int initialStory = random.nextInt(storiesNumber) + 1;
				int destinationStory = initialStory;
				while (initialStory == destinationStory) {
					destinationStory = random.nextInt(storiesNumber) + 1;
				}
				Passenger pas = constructor.newInstance(id, TransportationState.NOT_STARTED, initialStory, destinationStory);
				log.info(pas.toString());
				container.add(pas);
			}
		} catch (NoSuchMethodException | InstantiationException
					| IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return container;
	}

	/**
	 * Method generate dispatch container that
	 * store generated passengers.
	 * @param passengers  generated list of random passengers
	 * @return dispatch container with passengers
	 */
	public List<Story> generateDispatchContainers(final List<Passenger> passengers) {
		List<Story> dispatchContainers = new ArrayList<>(storiesNumber);
		for (int i = 0; i < storiesNumber; i++) {
			Story story = new Story();
			dispatchContainers.add(story);
		}
		log.info("GENERATING NEW DISPATCH CONTAINERS...");
		for (Passenger pas : passengers) {
			dispatchContainers.get(pas.getInitialStory() - 1).addPassenger(pas);
		}
		return dispatchContainers;
	}

	/**
	 * Method generate empty arrival container.
	 * Arrival container will store arrived passengers.
	 * @return empty arrival container
	 */
	public List<Story> generateArrivalContainers() {
		List<Story> arrivalContainers = new ArrayList<>(storiesNumber);
		log.info("GENERATING NEW ARRIVAL CONTAINERS...");
		for (int i = 0; i < storiesNumber; i++) {
			Story story = new Story();
			arrivalContainers.add(story);
		}
		return arrivalContainers;
	}


	/**
	 * Method getData(final String dataFile) read
	 * storiesNumber, elevatorCapacity, passengersNumber
	 * from property file.
	 *
	 * @param dataFile  String value of file path with data
	 * (storiesNumber, elevatorCapacity, passengersNumber) in format:
	 *  storiesNumber=10
	 *  elevatorCapacity=10
	 *  passengersNumber=10
	 *
	 * @throws IOException if there are:
	 * - no file
	 * - wrong file format
	 * - wrong values
	 */
	private void readData(final String dataFile) throws IOException {
		try (Reader fr = new FileReader(dataFile)) {
			Properties properties = new Properties();
			properties.load(fr);
			storiesNumber = Integer.parseInt(properties.getProperty("storiesNumber"));
			if (storiesNumber < 2) {
				throw new IOException("elevatorCapacity must be greater or equal to 2");
			}
			passengersNumber = Integer.parseInt(properties.getProperty("passengersNumber"));
			if (passengersNumber < 1) {
				throw new IOException("passengersNumber must be greater or equal to 1");
			}
			elevatorCapacity = Integer.parseInt(properties.getProperty("elevatorCapacity"));
			if (elevatorCapacity < 1) {
				throw new IOException("elevatorCapacity must be greater or equal to 1");
			}
		}
	}
}
