package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.commands.swerve.SwerveJoystickCommand;
import frc.robot.hardware.SciAbsoluteEncoder;
import frc.robot.hardware.SciSpark;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.swerve.SwerveSubsystem;
import frc.robot.util.PID;
import frc.robot.subsystems.ShooterSubsystem;

public class Robot extends TimedRobot
{
  public static SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
  public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public static ShooterSubsystem shooterSubsystem = new ShooterSubsystem();

  public static OI oi = new OI();
  //public static SwerveJoystickCommand swerveJoystickCommand = new SwerveJoystickCommand();

  public static NetworkTableInstance networkTables;
  public static NetworkTable visionTable;

  final double DRIVEN_SPARK_GEAR_RATIO     = 3.0 / 40;
  final double STEERING_SPARK_GEAR_RATIO    = 1.0 / 60;
  final double STEERING_ENCODER_GEAR_RATIO  = 1.0 / 5;

  final double INTAKE_FLYWHEEL_GEAR_RATIO   = 1.0 / 9;
  final double INTAKE_RETRACTION_GEAR_RATIO = 1.0 / 60; //unknown

  final double SHOOTER_SPARK_GEAR_RATIO    = 1.0 / 1.0;
  final double HOPPER_SPARK_GEAR_RATIO     = 1.0 / 63;

  private SciSpark steeringSpark1, driveSpark1, shooterSpark1, shooterSpark2, hopperSpark, intakeFlywheelSpark;
  private SciAbsoluteEncoder steeringEncoder;

  private PID testPID;
  
  // private SciPigeon pigeon;

  @Override public void robotInit()
  {
    // toggleFlywheelCommand = new ToggleFlywheelCommand(intakeSubsystem);
    // toggleIntakePositionCommand = new ToggleIntakePositionCommand(intakeSubsystem);

    // steeringSpark1 = new SciSpark(4, STEERING_SPARK_GEAR_RATIO);
    // driveSpark1 = new SciSpark(5, DRIVEN_SPARK_GEAR_RATIO);

    // shooterSpark1 = new SciSpark(15, SHOOTER_SPARK_GEAR_RATIO);
    // shooterSpark2 = new SciSpark(10, SHOOTER_SPARK_GEAR_RATIO);
    // shooterSpark1.setInverted(true); 
    // shooterSpark2.setInverted(true);

    // hopperSpark = new SciSpark(9, HOPPER_SPARK_GEAR_RATIO);
    // hopperSpark.setInverted(true);

    // intakeFlywheelSpark = new SciSpark(16, INTAKE_FLYWHEEL_GEAR_RATIO);
    // intakeFlywheelSpark.setInverted(true);

    // steeringEncoder = new SciAbsoluteEncoder(3,
    //                                          STEERING_ENCODER_GEAR_RATIO,
    //                                          0,
    //                                          false);
    // testPID = new PID(.82, 0.0, 0.0);
    // pigeon = new SciPigeon(20);

    // networkTables = NetworkTableInstance.getDefault();
    // visionTable = networkTables.getTable("Vision");
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
    (new SwerveJoystickCommand()).schedule();
  }

  @Override public void teleopPeriodic() 
  {  
    // System.out.println(visionTable.getEntry("Port Lateral Position").getDouble(-1));
    // System.out.println(visionTable.getEntry("Port Longitudinal Position").getDouble(-1));
    // System.out.println(visionTable.getEntry("Ball Lateral Position").getDouble(-1));
    // System.out.println(visionTable.getEntry("Ball Longitudinal Position").getDouble(-1));
    
    // double output = testPID.getOutput(Math.toRadians(270), steeringEncoder.getAngle());
    // steeringSpark1.set(output);

    // hopperSpark.set(.1);

    // shooterSpark1.set(.1);
    // shooterSpark2.set(.1);

  }

  @Override public void testInit() {}

  @Override public void testPeriodic() {}
}