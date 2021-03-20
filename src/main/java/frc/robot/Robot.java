package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.SwerveJoystickCommand;
import frc.robot.hardware.SciAbsoluteEncoder;
import frc.robot.hardware.SciSpark;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.util.PID;

public class Robot extends TimedRobot
{
  // private SwerveSubsystem swerveSubsystem;
  // private SwerveJoystickCommand swerveJoystickCommand;
  final double STEERING_SPARK_GEAR_RATIO   = 1.0 / 60;
  final double STEERING_ENCODER_GEAR_RATIO = 1.0 / 5;
  
  private SciSpark steeringSpark1;
  private SciAbsoluteEncoder steeringEncoder;

  private PID steeringAnglePID;

  @Override public void robotInit()
  {
    //swerveSubsystem       = new SwerveSubsystem();
    //swerveJoystickCommand = new SwerveJoystickCommand(swerveSubsystem);
    steeringSpark1 = new SciSpark(4, STEERING_SPARK_GEAR_RATIO);
    steeringEncoder = new SciAbsoluteEncoder(3,
                                             STEERING_ENCODER_GEAR_RATIO,
                                             Math.toRadians(90),
                                             false);
    steeringAnglePID = new PID(.82, 0.0, 0.0);
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
    // swerveJoystickCommand.schedule();
  }

  @Override public void teleopPeriodic() 
  {
     double output = steeringAnglePID.getOutput(Math.toRadians(270), steeringEncoder.getAngle());
    steeringSpark1.set(output);
    // System.out.println(steeringAnglePID.getOutput(Math.toDegrees(steeringEncoder.getAngle()), 45));
    // System.out.println(Math.toDegrees(steeringEncoder.getAngle()));
  }

  @Override public void testInit() {}

  @Override public void testPeriodic() {}
}