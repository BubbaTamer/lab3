import java.util.Stack;

public class Transport<T extends Car> {
    private final int MAX_CARS;
    private final Stack<T> carStack = new Stack<>();
    private final Car transport;

    private static final double MAX_DISTANCE = 2;
    private double angle = 0;

    public Transport(Car transportCar, int maxCars) {
        this.MAX_CARS = maxCars;
        this.transport = transportCar;
    }

    public void loadCar(T car) {
        if (!validateLoad(car))
            throw new FullCapacityException("There's no space for this vehicle.");;
        carStack.push(car);
    }

    public T unloadCar() {
        if (angle != 0 || carStack.isEmpty())
            throw new IllegalStateException("Failed to unload car right now.");
        return carStack.pop();
    }

    public void lower(double deg) {
        if (angle - deg >= 0 && transport.getCurrentSpeed() == 0) {
            angle -= deg;
        } else {
            throw new IllegalArgumentException("Cannot lower platform below 0 degrees.");
        }
    }

    public void raise(double deg) {
        if (deg < 0) throw new IllegalArgumentException("Degrees must be positive");
        if (transport.getCurrentSpeed() != 0) {
            throw new IllegalStateException("Cannot raise platform while moving.");
        }
        if (angle + deg > 70) {
            throw new IllegalArgumentException("Cannot exceed 70 degrees");
        }
        angle += deg;
    }

    public void setCarsPosition() {
        for (T car : carStack) {
            car.setPosition(transport.getX(), transport.getY());
        }
    }

    public double getAngle() {
        return angle;
    }

    protected boolean validateLoad(Car car) {
        return angle == 0 &&
                carStack.size() < MAX_CARS &&
                !(car instanceof TransportCar) &&
                calculateDistance(car);
    }

    private boolean calculateDistance(Car car) {
        if (Math.sqrt(Math.pow(car.getX() - transport.getX(), 2) +
                Math.pow(car.getY() - transport.getY(), 2)) > MAX_DISTANCE) {
            throw new IllegalArgumentException("Car is too far away.");
        }
        return true;
    }
}
