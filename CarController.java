import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
 * This class represents the Controller part in the MVC pattern.
 * It's responsibilities is to listen to the View and responds in a appropriate manner by
 * modifying the model state and the updating the view.
 */

public class CarController {
    // member fields:

    // The delay (ms) corresponds to 20 updates a sec (hz)
    private final int delay = 50;
    // The timer is started with a listener (see below) that executes the statements
    // each step between delays.
    private Timer timer = new Timer(delay, new TimerListener());

    // The frame that represents this instance View of the MVC pattern
    CarView frame;
    // A list of cars, modify if needed
    ArrayList<Car> cars = new ArrayList<>();
    Verkstad<Volvo240> volvo240Verkstad = new Verkstad<>(5);

    //methods:

    public static void main(String[] args) {
        // Instance of this class
        CarController cc = new CarController();

        cc.cars.add(new Saab95());
        cc.cars.add(new Scania());
        cc.cars.add(new Volvo240());

        // Start a new view and send a reference of self
        cc.frame = new CarView("CarSim 1.0", cc);

        // Start the timer
        cc.timer.start();
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);
                if (car instanceof Scania && ((Scania) car).getCurrentAngle() == 70) {
                    continue;
                }
                car.move();

                int x = (int) Math.round(car.getX());
                car.setPosition(x, (i+1)*100);
                int y = (int) Math.round(car.getY());

                if (x >= 300 && y == 300 && car instanceof Volvo240) {
                    try {
                        volvo240Verkstad.loadCar((Volvo240) car);
                        cars.remove(i);
                        System.out.println("Volvo has been loaded into workshop!");
                    } catch (FullCapacityException ex) {
                        ex.printStackTrace();
                    }
                }

                frame.drawPanel.moveit(i, x);

                if (x < 0 || x > 800) {
                    // 2x left turn is 180 turn
                    car.turnLeft();
                    car.turnLeft();
                }

                // repaint() calls the paintComponent method of the panel
                frame.drawPanel.repaint();
            }
        }
    }

    // Calls the gas method for each car once
    void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (Car car : cars) {
            car.gas(gas);
        }
    }

    void brake(int amount) {
        double brake = ((double) amount) / 100;
        for (Car car : cars) {
            car.brake(brake);
        }
    }

    void turboOn() {
        for (Car car : cars) {
            if (car instanceof Saab95) {
                ((Saab95) car).setTurboOn();
            }
        }
    }

    void turboOff() {
        for (Car car : cars) {
            if (car instanceof Saab95) {
                ((Saab95) car).setTurboOff();
            }
        }
    }

    void liftBed() {
        for (Car car : cars) {
            if (car instanceof Scania) {
                ((Scania) car).raisePlatform(70);
            }
        }
    }

    void lowerBed() {
        for (Car car : cars) {
            if (car instanceof Scania) {
                ((Scania) car).lowerPlatform(70);
            }
        }
    }

    void startAllCars() {
        for (Car car : cars) {
            car.startEngine();
        }
    }

    void stopAllCars() {
        for (Car car : cars) {
            car.stopEngine();
        }
    }
}
