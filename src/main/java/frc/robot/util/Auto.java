package frc.robot.util;

import frc.robot.Robot;

public class Auto {
    private PID xPID, yPID, headingPID;

    public Auto() {
        xPID = new PID(0.1, 0, 0);
        yPID = new PID(0.1, 0, 0);
        headingPID = new PID (0.5, 0, 0);
    }

    public void autoMovement(Waypoint goal) {
        double xSetpoint = MaxMagnitude(xPID.getOutput(goal.getX(), Robot.localization.currentWaypoint.getX()), 1);
        double ySetpoint = MaxMagnitude(yPID.getOutput(goal.getY(), Robot.localization.currentWaypoint.getY()), 1);
        double headingSetpoint = MaxMagnitude(headingPID.getOutput(goal.getHeading(), Robot.localization.currentWaypoint.getHeading()), 1);
        Robot.swerveSubsystem.drive(xSetpoint, ySetpoint, headingSetpoint);
    }

    public double MaxMagnitude(double input, double maxMagnitude) {
        maxMagnitude = Math.abs(maxMagnitude);
        if (input > maxMagnitude) {
            return maxMagnitude;
        } else if (input < -maxMagnitude) {
            return -maxMagnitude;
        } else {
            return input;
        }
    }
}