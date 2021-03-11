package frc.robot.util;

import edu.wpi.first.wpilibj.Timer;

public class PID {

    Timer timer;
	private double p, i, d, integral, prevTime, prevError = 0;

    public PID(double p, double i, double d) {
		this.timer = new Timer();
        this.timer.start();
		this.p = p;
		this.i = i;
		this.d = d;
    }
	
    public double getOutput(double setpoint, double currentPoint) {
        double currTime = this.timer.get();
        double dt = currTime - this.prevTime;
        
        double error = setpoint - currentPoint;
        this.integral += error * dt;
        double derivative = (error - this.prevError) / dt;
      
        return this.p * error + this.i * this.integral + this.d * derivative;
    }
}