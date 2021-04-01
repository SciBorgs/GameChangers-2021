package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import frc.robot.util.PID;
import frc.robot.hardware.SciPigeon;
import frc.robot.commands.intake.ToggleFlywheelCommand;
import frc.robot.commands.intake.ToggleIntakePositionCommand;
import frc.robot.commands.swerve.SwerveJoystickCommand;
import frc.robot.hardware.SciAbsoluteEncoder;
import frc.robot.hardware.SciSpark;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

public class Robot extends TimedRobot
{
  private SwerveSubsystem swerveSubsystem;
  private SwerveJoystickCommand swerveJoystickCommand;
  private IntakeSubsystem intakeSubsystem;
  private ToggleFlywheelCommand toggleFlywheelCommand;
  private ToggleIntakePositionCommand toggleIntakePositionCommand;
  private XboxController xboxController;
  private JoystickButton toggleIntakeFlywheelButton, toggleIntakePositionButton;


  private NetworkTableInstance networkTables;
  private NetworkTable visionTable;
  final double STEERING_SPARK_GEAR_RATIO    = 1.0 / 60;
  final double STEERING_ENCODER_GEAR_RATIO  = 1.0 / 5;
  final double INTAKE_FLYWHEEL_GEAR_RATIO   = 1.0 / 9;
  final double INTAKE_RETRACTION_GEAR_RATIO = 1.0 / 60; //unknown

  final double SHOOTER_SPARK_GEAR_RATIO    = 1.0 / 1.0;
  final double HOPPER_SPARK_GEAR_RATIO     = 1.0 / 63;

  private SciSpark steeringSpark1, shooterSpark1, shooterSpark2, hopperSpark, intakeFlywheelSpark;
  private SciAbsoluteEncoder steeringEncoder;

  private PID steeringAnglePID;
  
  //private SciPigeon pigeon;

  @Override public void robotInit()
  {
    swerveSubsystem       = new SwerveSubsystem();
    swerveJoystickCommand = new SwerveJoystickCommand(swerveSubsystem);
    intakeSubsystem       = new IntakeSubsystem();
    //toggleFlywheelCommand = new ToggleFlywheelCommand(intakeSubsystem);
    //toggleIntakePositionCommand = new ToggleIntakePositionCommand(intakeSubsystem);

    xboxController = new XboxController(RobotMap.XBOX_CONTROLLER);
    toggleIntakeFlywheelButton = new JoystickButton(xboxController, XboxController.Button.kA.value);
    toggleIntakeFlywheelButton.whenPressed(new ToggleFlywheelCommand(intakeSubsystem));
    toggleIntakePositionButton = new JoystickButton(xboxController, XboxController.Button.kB.value);
    toggleIntakePositionButton.whenPressed(new ToggleIntakePositionCommand(intakeSubsystem));


    //steeringSpark1 = new SciSpark(4, STEERING_SPARK_GEAR_RATIO);
    //shooterSpark1 = new SciSpark(15, SHOOTER_SPARK_GEAR_RATIO);
    //shooterSpark2 = new SciSpark(10, SHOOTER_SPARK_GEAR_RATIO);
    //hopperSpark = new SciSpark(9, HOPPER_SPARK_GEAR_RATIO);
    //hopperSpark.setInverted(true);
    //shooterSpark1.setInverted(true); 
    //shooterSpark2.setInverted(true); 

    //intakeFlywheelSpark = new SciSpark(16, INTAKE_FLYWHEEL_GEAR_RATIO);
    //intakeFlywheelSpark.setInverted(true);

    // steeringEncoder = new SciAbsoluteEncoder(3,
    //                                          STEERING_ENCODER_GEAR_RATIO,
    //                                          0,
    //                                          false);
    // steeringAnglePID = new PID(.82, 0.0, 0.0);
    //pigeon = new SciPigeon(20);

    //networkTables = NetworkTableInstance.getDefault();
    //visionTable = networkTables.getTable("Vision");
  }

  @Override public void robotPeriodic()
  {
    CommandScheduler.getInstance().run();
  }

  @Override public void disabledInit() {}

  @Override public void disabledPeriodic() {
    //System.out.println(Math.toDegrees(steeringEncoder.getAngle()));
  }

  @Override public void autonomousInit() {}

  @Override public void autonomousPeriodic() {}

  @Override public void teleopInit()
  {
    //pigeon.setAngle(0);
    swerveJoystickCommand.schedule();
  }

  @Override public void teleopPeriodic() 
  {  
    //System.out.println(visionTable.getEntry("Port Lateral Position").getDouble(-1));
    //System.out.println(visionTable.getEntry("Port Longitudinal Position").getDouble(-1));
    //System.out.println(visionTable.getEntry("Ball Lateral Position").getDouble(-1));
    //System.out.println(visionTable.getEntry("Ball Longitudinal Position").getDouble(-1));
    //double output = steeringAnglePID.getOutput(Math.toRadians(270), steeringEncoder.getAngle());
    //steeringSpark1.set(output);
    //System.out.println("getAngle(): " + Math.toDegrees(steeringEncoder.getAngle()));

    //System.out.println("Pigeon Yaw Angle: " + Math.toDegrees(pigeon.getAngle()));
    
    //hopperSpark.set(.1);
    //shooterSpark1.set(.1);
    //shooterSpark2.set(.1);
    //System.out.println(intakeFlywheelSpark.getEncoder().getVelocity());
    //intakeFlywheelSpark.set(1);
  }

  @Override public void testInit() {}

  @Override public void testPeriodic() {}
}