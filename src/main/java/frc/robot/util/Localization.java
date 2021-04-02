package frc.robot.util;

import edu.wpi.first.wpilibj.Timer;

import frc.robot.Robot;

public class Localization {
  public static Point currentPos;
  private Timer timer;
  private final double TRACK_LENGTH = 30;
  private final double TRACK_WIDTH  = 29.579;
  private final double WHEEL_RADIUS = 1.0;
  private double prevTime;

  public Localization()
  {
    currentPos = new Point(0.0,0.0);
    timer = new Timer();
    prevTime = 0;
    timer.start();
  }

  public double getModuleXVelocity(int index) {
    double speed = 2 * Math.PI * Robot.swerveSubsystem.modules[index].drivenSpark.getEncoder().getVelocity() * WHEEL_RADIUS; 
    return Math.cos(Robot.swerveSubsystem.modules[index].drivenSpark.getEncoder().getVelocity()) * speed;
  }

  public double getModuleYVelocity(int index) {
    double speed = 2 * Math.PI * Robot.swerveSubsystem.modules[index].drivenSpark.getEncoder().getVelocity() * WHEEL_RADIUS; 
    return Math.sin(Robot.swerveSubsystem.modules[index].desiredSteeringAngle) * speed;
  }

  public void update()
  {
    double coeff = 1.0;
    double A = (getModuleYVelocity(1) + getModuleYVelocity(0)) / 2;
    double B = (getModuleYVelocity(2) + getModuleYVelocity(3)) / 2;
    double C = (getModuleXVelocity(3) + getModuleXVelocity(1)) / 2;
    double D = (getModuleXVelocity(2) + getModuleXVelocity(0)) / 2;
    
    double ROT =  coeff * ((B - A) / TRACK_LENGTH + (C - D) / TRACK_WIDTH) / 2; // Angular velocity of chassis
    double FWD =  coeff * (ROT * (TRACK_LENGTH / 2) + A - ROT * (TRACK_LENGTH / 2) + B) / 2; // Chassis y-velocity
    double STR =  coeff * (ROT * (TRACK_WIDTH / 2) + C - ROT * (TRACK_WIDTH / 2) + D) / 2; // Chassis x-velocity
    
    // Field centric modifications:
    double theta = Robot.swerveSubsystem.pigeon.getAngle();
    FWD = FWD * Math.cos(theta) - STR * Math.sin(theta);
    STR = FWD * Math.sin(theta) + STR * Math.cos(theta);

    double deltaT = timer.get() - prevTime;
    double newX = currentPos.getX() + deltaT*STR;
    double newY = currentPos.getY() + deltaT*FWD;
    currentPos.setX(newX);
    currentPos.setY(newY);
  }

}
