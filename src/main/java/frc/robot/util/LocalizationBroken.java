package frc.robot.util;

import edu.wpi.first.wpilibj.Timer;

import frc.robot.Robot;

public class LocalizationBroken {
  public static Point currentPos = new Point(0.0,0.0);
  private static Timer timer;
  private static final double TRACK_LENGTH = 30;
  private static final double TRACK_WIDTH  = 29.579;
  private static final double WHEEL_RADIUS = 2.0;
  private static double prevTime;

  private static double getModuleXVelocity(int index) {
    double speed = Robot.swerveSubsystem.modules[index].drivenSpark.getEncoder().getVelocity() * WHEEL_RADIUS; 
    //double speed = Robot.swerveSubsystem.modules[index].desiredWheelSpeed;
    return Math.cos(Robot.swerveSubsystem.modules[index].steeringEncoder.getAngle()) * speed;
  }

  private static double getModuleYVelocity(int index) {
    double speed = Robot.swerveSubsystem.modules[index].drivenSpark.getEncoder().getVelocity() * WHEEL_RADIUS; 
    //double speed = Robot.swerveSubsystem.modules[index].desiredWheelSpeed;
    return Math.sin(Robot.swerveSubsystem.modules[index].steeringEncoder.getAngle()) * speed;
  }

  public static void start() {
    timer = new Timer();
    prevTime = 0;
    timer.start();
  }

  public static void update()
  {
    double coeff = 1.0;
    double A = (getModuleYVelocity(1) + getModuleYVelocity(0)) / 2;
    double B = (getModuleYVelocity(2) + getModuleYVelocity(3)) / 2;
    double C = (getModuleXVelocity(3) + getModuleXVelocity(1)) / 2;
    double D = (getModuleXVelocity(2) + getModuleXVelocity(0)) / 2;
    
    //System.out.println("A: " + A);
    //System.out.println("B: " + B);
    //System.out.println("C: " + C);
    //System.out.println("D: " + D);

    //System.out.println("Y-velocity: " + getModuleYVelocity(1));
    //System.out.println("X-velocity: " + getModuleXVelocity(1));

    double ROT =  coeff * ((B - A) / TRACK_LENGTH + (C - D) / TRACK_WIDTH) / 2; // Angular velocity of chassis
    double FWD =  coeff * (ROT * (TRACK_LENGTH / 2) + A - ROT * (TRACK_LENGTH / 2) + B) / 2; // Chassis y-velocity
    double STR =  coeff * (ROT * (TRACK_WIDTH / 2) + C - ROT * (TRACK_WIDTH / 2) + D) / 2; // Chassis x-velocity
    
    //System.out.println("ROT: " + ROT + "\tFWD: " + FWD + "\tSTR: " + STR);

    // Field centric modifications:
    double theta = Robot.swerveSubsystem.pigeon.getAngle();
    FWD = FWD * Math.cos(theta) - STR * Math.sin(theta);
    STR = FWD * Math.sin(theta) + STR * Math.cos(theta);
    
    //System.out.println("Theta: " + Math.toDegrees(theta) + "\tFWD: " + FWD + "\tSTR: " + STR);

    double deltaT = timer.get() - prevTime;
    double newX = currentPos.getX() + deltaT*STR;
    double newY = currentPos.getY() + deltaT*FWD;
    currentPos.setX(newX);
    currentPos.setY(newY);
    prevTime = timer.get();
  }
}

