package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.command.SwerveDriveJoystickCommand;
import frc.robot.subsytem.SwerveDrive.SwerveDriveSubsystem;

public class Robot extends TimedRobot {
  public static SwerveDriveSubsystem swerveDriveSubsystem = new SwerveDriveSubsystem();

  @Override
  public void robotInit() {}


  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() { }

  @Override
  public void autonomousPeriodic() { }

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    new SwerveDriveJoystickCommand();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
