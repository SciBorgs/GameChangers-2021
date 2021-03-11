package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.SwerveJoystickCommand;
import frc.robot.subsystems.SwerveSubsystem;

public class Robot extends TimedRobot
{
  private SwerveSubsystem swerveSubsystem;
  private SwerveJoystickCommand swerveJoystickCommand;

  @Override public void robotInit()
  {
    swerveSubsystem       = new SwerveSubsystem();
    swerveJoystickCommand = new SwerveJoystickCommand(swerveSubsystem);
  }

  @Override public void robotPeriodic()
  {
    CommandScheduler.getInstance().run();
  }

  @Override public void disabledInit() {}

  @Override public void disabledPeriodic() {}

  @Override public void autonomousInit() {}

  @Override public void autonomousPeriodic() {}

  @Override public void teleopInit()
  {
    swerveJoystickCommand.schedule();
  }

  @Override public void teleopPeriodic() {}

  @Override public void testInit() {}

  @Override public void testPeriodic() {}
}
