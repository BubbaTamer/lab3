public class Verkstad<T extends Car> {
    private final Transport<T> transport;

    public Verkstad(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Capacity must be a positive integer");
        transport = new Transport<>(null, capacity);
    }

    public void loadCar(T car) {
        transport.loadCar(car);
    }

    public T getLastCar() {
        return transport.unloadCar();
    }
}
