package frc.robot.commands.swerve;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveSubsystem;

public class SwerveJoystickCommand extends CommandBase
{
  private SwerveSubsystem swerveSubsystem;

  public SwerveJoystickCommand(SwerveSubsystem swerveSubsystem)
  {
    this.swerveSubsystem = swerveSubsystem;

    addRequirements(swerveSubsystem);
  }

  @Override public void execute()
  {
    swerveSubsystem.joystickDrive();
  }
}
