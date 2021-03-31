package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveSubsystem;
import java.util.ArrayList;
import frc.robot.util.Point;

public class NavigateToPointCommand extends CommandBase
{
  public ArrayList<Point> points;
  public Point currentPoint;
  public Point targetPoint;

  private SwerveSubsystem swerveSubsystem;
  private final double EPSILON = 0.1;
  private boolean noMorePoints = false;

  public NavigateToPointCommand(SwerveSubsystem swerveSubsystem)
  {
    this.swerveSubsystem = swerveSubsystem;
    this.currentPoint = new Point(0.0,0.0);
    addRequirements(swerveSubsystem);
  }

  @Override public void execute()
  {
    System.out.println("ok");
  }

  @Override public boolean isFinished() {
    return noMorePoints;
  }
}
