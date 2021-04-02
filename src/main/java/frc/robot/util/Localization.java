package frc.robot.util;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveSubsystem;
import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.sensors.PigeonIMU;
import java.lang.Math;

public class Localization {
  public static Point currentPos;
  private SwerveSubsystem swerveSubsystem;
  private Timer timer;
  private final double TRACK_LENGTH = 30;
  private final double TRACK_WIDTH  = 29.579;
  private final double WHEEL_RADIUS = 1.0;
  private PigeonIMU pigeon;
  private double prevTime;

  public Localization(SwerveSubsystem swerveSubsystem)
  {
    this.swerveSubsystem = swerveSubsystem;
    currentPos = new Point(0.0,0.0);
    timer = new Timer();
    pigeon = new PigeonIMU(20); // Device #: 20
    prevTime = 0;
    timer.start();
  }

  public double getModuleXVelocity(int index) {
    double speed = 2 * Math.PI * swerveSubsystem.modules[index].drivenSpark.getEncoder().getVelocity() * WHEEL_RADIUS; 
    return Math.cos(swerveSubsystem.modules[index].drivenSpark.getEncoder().getVelocity()) * speed;
  }

  public double getModuleYVelocity(int index) {
    double speed = 2 * Math.PI * swerveSubsystem.modules[index].drivenSpark.getEncoder().getVelocity() * WHEEL_RADIUS; 
    return Math.sin(swerveSubsystem.modules[index].desiredSteeringAngle) * speed;
  }

  public double[] yawPitchRoll() {
    double[] yawPitchRolle = new double[3];
    pigeon.getYawPitchRoll(yawPitchRolle);
    return yawPitchRolle;
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
    double theta = swerveSubsystem.pigeon.getAngle();
    FWD = FWD * Math.cos(theta) - STR * Math.sin(theta);
    STR = FWD * Math.sin(theta) + STR * Math.cos(theta);

    double deltaT = timer.get() - prevTime;
    double newX = currentPos.getX() + deltaT*STR;
    double newY = currentPos.getY() + deltaT*FWD;
    currentPos.setX(newX);
    currentPos.setY(newY);
  }

}
