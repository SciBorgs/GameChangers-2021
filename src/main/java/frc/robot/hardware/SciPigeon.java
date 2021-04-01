package frc.robot.hardware;

import com.ctre.phoenix.sensors.PigeonIMU;

public class SciPigeon extends PigeonIMU {

    public SciPigeon(int id) {
        super(id);
    }

    private double[] yawPitchRole(){
        double[] yawPitchRoll = new double[3];
        super.getYawPitchRoll(yawPitchRoll);
        return yawPitchRoll;
    }

    public double getAngle() {
        return Math.toRadians(yawPitchRole()[0]);
    }

    public double getPitch() {
        return Math.toRadians(yawPitchRole()[1]);
    }
    
    public double getRole() {
        return Math.toRadians(yawPitchRole()[2]);
    }
    
    public void setAngle(double angle) {
        super.setYaw(angle);
    }
}