import java.awt.*;

public class Scania extends Car implements TransportCar {
    private final Transport<Car> transport;

    public Scania() {
        super(2, Color.white, 600, "Scania");
        transport = new Transport<>(this,0); // Doesn't load cars
    }

    public double getCurrentAngle() {
        return transport.getAngle();
    }

    public void raisePlatform(double deg) {
        transport.raise(deg);
    }

    public void lowerPlatform(double deg) {
        transport.lower(deg);
    }

    @Override
    public void move() {
        if (transport.getAngle() != 0)
            throw new IllegalStateException("Transport can't move while ramp is up.");
        super.move();
    }

    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.01;
    }
}
