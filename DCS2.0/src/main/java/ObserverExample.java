import java.util.List;
import java.util.ArrayList;


// Subject interface
interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String message);
}

// Observer interface
interface Observer {
    void update(String message);
}

// Concrete subject class
class WeatherStation implements Subject {
    private List<Observer> observers;

    public WeatherStation() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void setMeasurements(String message) {
        notifyObservers(message);
    }
}

// Concrete observer classes
class WeatherApp implements Observer {
    private String appName;

    public WeatherApp(String appName) {
        this.appName = appName;
    }

    @Override
    public void update(String message) {
        System.out.println(appName + " received update: " + message);
    }
}

class WeatherForecast implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Forecast received update: " + message);
    }
}

public class ObserverExample {
    public static void main(String[] args) {
        // Create subject object
        WeatherStation weatherStation = new WeatherStation();

        // Create observer objects
        Observer weatherApp = new WeatherApp("My Weather App");
        Observer weatherForecast = new WeatherForecast();

        // Register observers
        weatherStation.registerObserver(weatherApp);
        weatherStation.registerObserver(weatherForecast);

        // Send update
        weatherStation.setMeasurements("Sunny today, high 25C");
    }
}
