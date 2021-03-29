package frc.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.Pair;
import frc.robot.RobotMap;
import frc.robot.hardware.SciAbsoluteEncoder;
import frc.robot.hardware.SciSpark;
import frc.robot.util.PID;
import frc.robot.util.SciMath;
import java.util.Arrays;
import java.util.Comparator;

public class SwerveSubsystem extends SubsystemBase
{
  private final int MODULE_COUNT = RobotMap.SWERVE_MODULE_LUT.length;
  private XboxController xbox = new XboxController(0);

  private static class Module
  {
    private SciSpark drivenSpark;
    private SciSpark steeringSpark;
    private SciAbsoluteEncoder steeringEncoder;
    private PID steeringAnglePID;

    private double desiredWheelSpeed;
    private double desiredSteeringAngle;


    public Module(int drivenSparkPort,
                  int steeringSparkPort,
                  int steeringEncoderPort,
                  boolean flipSteeringEncoder)
    {
      final double DRIVEN_SPARK_GEAR_RATIO     = 3.0 / 40;
      final double STEERING_SPARK_GEAR_RATIO   = 1.0 / 60;
      final double STEERING_ENCODER_GEAR_RATIO = 1.0 / 5;

      /* clang-format off */
      drivenSpark     = new SciSpark(drivenSparkPort, DRIVEN_SPARK_GEAR_RATIO);
      steeringSpark   = new SciSpark(steeringSparkPort, STEERING_SPARK_GEAR_RATIO);
      
      steeringEncoder = new SciAbsoluteEncoder(steeringEncoderPort,
                                               STEERING_ENCODER_GEAR_RATIO,
                                               Math.toRadians(90),
                                               flipSteeringEncoder);

      steeringAnglePID = new PID(0.82, 0, 0);
      /* clang-format on */
    }
  }

  private Module[] modules;

  public SwerveSubsystem()
  {
    modules = new Module[MODULE_COUNT];

    for (int i = 0; i < MODULE_COUNT; ++i) {
      int[] moduleRow = RobotMap.SWERVE_MODULE_LUT[i];

      int drivenSparkPort            = moduleRow[0];
      int steeringSparkPort          = moduleRow[1];
      int steeringEncoderPort        = moduleRow[2];
      boolean flipSteeringEncoder = moduleRow[3] == 1 ? true : false;

      modules[i] = new Module(drivenSparkPort,
                              steeringSparkPort,
                              steeringEncoderPort,
                              flipSteeringEncoder);
      modules[i].drivenSpark.setInverted(true);
    }
  }

  public void joystickDrive()
  {
    // drive(xbox.getX(GenericHID.Hand.kLeft) / 5, -xbox.getY(GenericHID.Hand.kRight) / 5, xbox.getX(GenericHID.Hand.kRight) / 5);
    
    drive(0, 0, 0);
  }

  public void drive(double latVel, double longVel, double omega)
  {
    final double HALF_TRACK_LENGTH = 30 / 2;
    final double HALF_TRACK_WIDTH  = 29.579 / 2;

    final int BL_IDX = 0;
    final int BR_IDX = 1;
    final int FL_IDX = 2;
    final int FR_IDX = 3;

    Pair<Double, Double> wheelLatVel = new Pair<>(
      latVel - omega * HALF_TRACK_LENGTH, latVel + omega * HALF_TRACK_LENGTH);
    Pair<Double, Double> wheelLongVel = new Pair<>(
      longVel + omega * HALF_TRACK_WIDTH, longVel - omega * HALF_TRACK_WIDTH);

    setDesiredModuleStrategy(
      modules[BL_IDX], wheelLatVel.getFirst(), wheelLongVel.getFirst());
    setDesiredModuleStrategy(
      modules[BR_IDX], wheelLatVel.getFirst(), wheelLongVel.getSecond());
    setDesiredModuleStrategy(
      modules[FL_IDX], wheelLatVel.getSecond(), wheelLongVel.getFirst());
    setDesiredModuleStrategy(
      modules[FR_IDX], wheelLatVel.getSecond(), wheelLongVel.getSecond());

    executeDesiredModuleStrategies();
  }

  private void setDesiredModuleStrategy(Module mod,
                                        double wheelLatVel,
                                        double wheelLongVel)
  {
    mod.desiredWheelSpeed = Math.hypot(wheelLatVel, wheelLongVel);
    mod.desiredSteeringAngle =
      SciMath.normalizeAngle(Math.atan2(wheelLongVel, wheelLatVel));
  }

  private void executeDesiredModuleStrategies()
  {
    double maxDesWheelSpeed =
      Arrays.stream(modules)
        .max(Comparator.comparingDouble((mod) -> mod.desiredWheelSpeed))
        .get()
        .desiredWheelSpeed;

    for (int i = 0; i < MODULE_COUNT; ++i) {
      Module mod = modules[i];

      if (maxDesWheelSpeed > 1) {
        mod.desiredWheelSpeed /= maxDesWheelSpeed;
      }

      // optimized angle code?
      // double dir = mod.desiredSteeringAngle % 360.0 - mod.steeringEncoder.getAngle() % 360.0;
      // double sign = Math.signum(dir);
      
      //System.out.println(this.getClass().getSimpleName() + ":"
      //                   + " SETTING " + i + " TO " + mod.desiredWheelSpeed +
      //                   " AND " + Math.toDegrees(mod.desiredSteeringAngle) +
      //                   " DEGREES");                  

      mod.drivenSpark.set(mod.desiredWheelSpeed);
      mod.steeringSpark.set(mod.steeringAnglePID.getOutput(
        mod.desiredSteeringAngle, mod.steeringEncoder.getAngle()));
      
      System.out.println("get(): " + " GETTING " + i + " TO " + mod.steeringSpark.get() +
      " AND " + Math.toDegrees(mod.steeringEncoder.getAngle()) +
      " DEGREES");
      // optimized angle code?
      //mod.steeringSpark.set(sign * mod.steeringAnglePID.getOutput(
      //  SciMath.normalizeAngle(mod.desiredSteeringAngle - mod.steeringEncoder.getAngle()), 0));
    }
  }
}
