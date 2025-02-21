import java.awt.*;

public abstract class Car implements Movable {

    private final int nrDoors; // Number of doors on the car
    private final double enginePower; // Engine power of the car
    private Color color; // Color of the car
    private final String modelName; // The car model name

    // don't allow external class to affect this

    private double currentSpeed; // The current speed of the car

    private double x = 0;
    private double y = 0;
    private double direction = 0;

    public Car(int nrDoors, Color color, double enginePower, String modelName) {
        this.nrDoors = nrDoors;
        this.color = color;
        this.enginePower = enginePower;
        this.modelName = modelName;

        stopEngine();
    }

    public int getNrDoors() {
        return this.nrDoors;
    }

    public double getEnginePower() {
        return this.enginePower;
    }

    public double getCurrentSpeed() {
        return this.currentSpeed;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color clr) {
        this.color = clr;
    }

    public void startEngine() {
        this.currentSpeed = 0.1;
    }

    public void stopEngine() {
        this.currentSpeed = 0;
    }

    protected abstract double speedFactor(); // override by implementing class

    private void incrementSpeed(double amount) {
        if (amount < 0 || amount > 1) throw new IllegalArgumentException("Amount must be between 0 and 1");
        currentSpeed = Math.min(currentSpeed + speedFactor() * amount, enginePower);
    }

    private void decrementSpeed(double amount) {
        if (amount < 0 || amount > 1) throw new IllegalArgumentException("Amount must be between 0 and 1");
        currentSpeed = Math.max(currentSpeed - speedFactor() * amount, 0);
    }

    public void gas(double amount) {
        incrementSpeed(amount);
    }

    public void brake(double amount) {
        decrementSpeed(amount);
    }

    // Movable implementation
    @Override
    public void move() {
        x += currentSpeed * Math.cos(Math.toRadians(direction));
        y += currentSpeed * Math.sin(Math.toRadians(direction));
    }

    @Override
    public void turnLeft() {
        direction = (direction - 90 + 360) % 360;
    }

    @Override
    public void turnRight() {
        direction = (direction + 90) % 360;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirection() {
        return direction;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
