package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveSubsystem;
import java.util.Queue;
import frc.robot.util.Point;

public class NavigateToPointCommand extends CommandBase
{
  public Queue<Point> points;
  private SwerveSubsystem swerveSubsystem;

  public NavigateToPointCommand(SwerveSubsystem swerveSubsystem)
  {
    this.swerveSubsystem = swerveSubsystem;

    addRequirements(swerveSubsystem);
  }

  @Override public void execute()
  {
    swerveSubsystem.drive(0,0,0);
  }

  @Override public boolean isFinished() {
    return points.size() == 0;
  }
}
