package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import java.lang.Math;

public class RobotLocalizationCommand extends CommandBase
{
  private SwerveSubsystem swerveSubsystem;
  private Timer timer;
  private final double TRACK_LENGTH = 30;
  private final double TRACK_WIDTH  = 29.579;
  private double prevTime;

  public RobotLocalizationCommand(SwerveSubsystem swerveSubsystem)
  {
    this.swerveSubsystem = swerveSubsystem;
    addRequirements(swerveSubsystem);
    timer = new Timer();
    prevTime = 0;
    timer.start();
  }

  public double getModuleXVelocity(int index) {
    return Math.cos(swerveSubsystem.modules[index].desiredSteeringAngle) * swerveSubsystem.modules[index].desiredWheelSpeed;
  }

  public double getModuleYVelocity(int index) {
    return Math.sin(swerveSubsystem.modules[index].desiredSteeringAngle) * swerveSubsystem.modules[index].desiredWheelSpeed;
  }

  @Override public void execute()
  {
    double A = (getModuleYVelocity(1) + getModuleYVelocity(0)) / 2;
    double B = (getModuleYVelocity(2) + getModuleYVelocity(3)) / 2;
    double C = (getModuleXVelocity(3) + getModuleXVelocity(1)) / 2;
    double D = (getModuleXVelocity(2) + getModuleXVelocity(0)) / 2;
    
    double ROT  = ((B - A) / TRACK_LENGTH + (C - D) / TRACK_WIDTH) / 2; // Angular velocity of chassis
    double FWD = (ROT * (TRACK_LENGTH / 2) + A - ROT * (TRACK_LENGTH / 2) + B) / 2; // Chassis y-velocity
    double STR = (ROT * (TRACK_WIDTH / 2) + C - ROT * (TRACK_WIDTH / 2) + D) / 2; // Chassis x-velocity
    // Field centric modifications:
    // FWD = FWD * cos(theta) + STR * sin(theta)
    // STR = STR * cos(theta) - FWD * sin(theta)
    double deltaT = timer.get() - prevTime;
    double newX = Robot.currentPos.getX() + deltaT*STR;
    double newY = Robot.currentPos.getY() + deltaT*FWD;
    Robot.currentPos.setX(newX);
    Robot.currentPos.setY(newY);
  }
}
