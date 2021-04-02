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
        double xSetpoint = xPID.getOutput(goal.getX(), Robot.localization.currentWaypoint.getX());
        double ySetpoint = yPID.getOutput(goal.getY(), Robot.localization.currentWaypoint.getY());
        double maxSetpoint = Math.max(Math.abs(xSetpoint), Math.abs(ySetpoint));
        if (maxSetpoint > 1) {
            xSetpoint /= maxSetpoint;
            ySetpoint /= maxSetpoint;
        }
        double headingSetpoint = headingPID.getOutput(goal.getHeading(), Robot.localization.currentWaypoint.getHeading());
        Robot.swerveSubsystem.drive(xSetpoint, ySetpoint, headingSetpoint);
    }
}