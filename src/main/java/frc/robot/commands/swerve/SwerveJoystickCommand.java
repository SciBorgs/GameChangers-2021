package frc.robot.commands.swerve;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SwerveJoystickCommand extends CommandBase
{
  public SwerveJoystickCommand()
  {
    addRequirements(Robot.swerveSubsystem);
  }

  @Override public void execute()
  {
    Robot.swerveSubsystem.joystickDrive(Robot.oi.getXboxLeftX(), Robot.oi.getXboxLeftY(), Robot.oi.getXboxRightX());
  }
}
