package frc.robot.util;

import frc.robot.Robot;

public class Auto {
    private static PID xPID, yPID, headingPID;

    private static Waypoint currDestination = new Waypoint(new Point(0, 0), 0);

    public static void start () {
        xPID = new PID(0.1, 0, 0);
        yPID = new PID(0.1, 0, 0);
        headingPID = new PID (0.5, 0, 0);
    }

    public static void setCurrDestination (Waypoint newDestination) {
        currDestination = newDestination;
    }

    public static void autoMovement() {
        double xSetpoint = xPID.getOutput(currDestination.getX(), Localization.currentWaypoint.getX());
        double ySetpoint = yPID.getOutput(currDestination.getY(), Localization.currentWaypoint.getY());
        double maxSetpoint = Math.max(Math.abs(xSetpoint), Math.abs(ySetpoint));
        if (maxSetpoint > 1) {
            xSetpoint /= maxSetpoint;
            ySetpoint /= maxSetpoint;
        }
        double headingSetpoint = headingPID.getOutput(currDestination.getHeading(), Localization.currentWaypoint.getHeading());
        Robot.swerveSubsystem.drive(xSetpoint, ySetpoint, headingSetpoint);
    }
}