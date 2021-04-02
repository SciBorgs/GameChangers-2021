package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.Pair;
import frc.robot.RobotMap;
import frc.robot.hardware.SciAbsoluteEncoder;
import frc.robot.hardware.SciSpark;
import frc.robot.util.PID;
import frc.robot.util.SciMath;
import java.util.Arrays;
import java.util.Comparator;
import frc.robot.hardware.SciPigeon;

public class SwerveSubsystem extends SubsystemBase
{
  private final int MODULE_COUNT = RobotMap.SWERVE_MODULE_LUT.length;

  public SciPigeon pigeon = new SciPigeon(20);
  private boolean useGyro = true;

  private final double JOYSTICK_LIMITER = 2.0 / 5;
  private static class Module
  {
    public SciSpark drivenSpark;
    private SciSpark steeringSpark;
    private SciAbsoluteEncoder steeringEncoder;
    private PID steeringAnglePID;

    public double desiredWheelSpeed;
    public double desiredSteeringAngle;

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
                                               Math.toRadians(0),
                                               flipSteeringEncoder);
      System.out.println("initial angle: " + Math.toDegrees(steeringEncoder.getAngle()));

      steeringAnglePID = new PID(0.82, 0, 0);

      /* clang-format on */
    }
  }

  public Module[] modules;

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
    if (useGyro) {
      pigeon.setAngle(Math.toRadians(0));
    } 
  }

  public void joystickDrive(double rawX, double rawY, double rawRot)
  {
    double x   =  1 * Math.signum(rawX) * Math.pow(rawX, 2);
    double y   = -1 * Math.signum(rawY) * Math.pow(rawY, 2);
    double rot =  1 * Math.signum(rawRot) * Math.pow(rawRot, 2);
    if((rawX < .2) && (rawX > -.2) &&
       (rawY < .2) && (rawY > -.2) &&
       (rawRot < .2) && (rawRot > -.2)){
      x = 0;
      y = 0;
      rot = 0;
    } else if ((rawRot < .2) && (rawRot > -.2)){
      rot = 0;
    }
   
    drive(x, y, rot);
    //System.out.println("rot: " + rot);
  }

  public void drive(double latVel, double longVel, double omega)
  {
    // System.out.println(omega);
    //final double TRACK_LENGTH = 30;
    //final double TRACK_WIDTH  = 29.579;
    final double TRACK_LENGTH = 1;
    final double TRACK_WIDTH = 1;

    double diagonal = Math.sqrt(Math.pow(TRACK_LENGTH, 2) + 
                                Math.pow(TRACK_WIDTH,2));

    final int BL_IDX = 0;
    final int BR_IDX = 1;
    final int FL_IDX = 2;
    final int FR_IDX = 3;

    Pair<Double, Double> wheelLatVel  = new Pair<>(0.0, 0.0);
    Pair<Double, Double> wheelLongVel = new Pair<>(0.0, 0.0);

    if(useGyro) {
      double gyroAngle = SciMath.normalizeAngle(pigeon.getAngle());
      gyroAngle = SciMath.normalizeAngle(gyroAngle);
      gyroAngle = SciMath.normalizeAngle(gyroAngle + Math.PI);
      double latVelGyro  = latVel * Math.cos(gyroAngle) +  longVel * Math.sin(gyroAngle);
      double longVelGyro = - latVel * Math.sin(gyroAngle) +  longVel * Math.cos(gyroAngle);
      wheelLatVel = new Pair<>(
        latVelGyro - (omega * TRACK_LENGTH / diagonal), latVelGyro + (omega * TRACK_LENGTH / diagonal));

      wheelLongVel = new Pair<>(
        longVelGyro + (omega * TRACK_WIDTH / diagonal), longVelGyro - (omega * TRACK_WIDTH / diagonal));
        //System.out.println("gyroAngle: " + Math.toDegrees(gyroAngle));
    } else {
      wheelLatVel = new Pair<>(
        latVel - (omega * TRACK_LENGTH / diagonal), latVel + (omega * TRACK_LENGTH / diagonal));
        //System.out.println("latVel1: " + wheelLatVel.getFirst() + "\t latVel2: " + wheelLatVel.getSecond());

      wheelLongVel = new Pair<>(
        longVel + (omega * TRACK_WIDTH / diagonal), longVel - (omega * TRACK_WIDTH / diagonal));
        //System.out.println("longVel1: " + wheelLongVel.getFirst() + "\t longVel2: " + wheelLongVel.getSecond());
    }

    setDesiredModuleStrategy(
      modules[BL_IDX], wheelLatVel.getFirst(), wheelLongVel.getFirst());
    setDesiredModuleStrategy(
      modules[BR_IDX], wheelLatVel.getFirst(), wheelLongVel.getSecond());
    setDesiredModuleStrategy(
      modules[FL_IDX], wheelLatVel.getSecond(), wheelLongVel.getFirst());
    setDesiredModuleStrategy(
      modules[FR_IDX], wheelLatVel.getSecond(), wheelLongVel.getSecond());

    executeDesiredModuleStrategies();
    System.out.println("gyroAngle: " + Math.toDegrees(SciMath.normalizeAngle(pigeon.getAngle())));
  }

  private void setDesiredModuleStrategy(Module mod,
                                        double wheelLatVel,
                                        double wheelLongVel)
  {
    mod.desiredWheelSpeed = Math.hypot(wheelLatVel, wheelLongVel);
    mod.desiredSteeringAngle = SciMath.normalizeAngle(Math.atan2(wheelLongVel, wheelLatVel));
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
    
      // if (useGyro) {
      //   mod.desiredSteeringAngle = SciMath.normalizeAngle(mod.desiredSteeringAngle + Math.PI);
      // }

      //System.out.println(this.getClass().getSimpleName() + ":"
      //                   + " SETTING " + i + " TO " + mod.desiredWheelSpeed +
      //                   " AND " + Math.toDegrees(mod.desiredSteeringAngle) +
      //                   " DEGREES");                  

      // optimized angle code?

      mod.drivenSpark.set(mod.desiredWheelSpeed);
      
      double difference_angle = mod.desiredSteeringAngle - mod.steeringEncoder.getAngle();
      if (Math.abs(difference_angle) > Math.PI) {
        double sign = Math.signum(difference_angle);
        difference_angle -= sign * 2 * Math.PI; 
      }

      // difference angle is somewhere between -PI and PI
      double output = mod.steeringAnglePID.getOutput(difference_angle, 0);
      mod.steeringSpark.set(output);

      // System.out.println("desSteeringAngle " + i + ": " + mod.desiredSteeringAngle);
    }
  }

  public void setZero () {
    for (int i = 0; i < MODULE_COUNT; ++i) {
      Module mod = modules[i];
      mod.drivenSpark.set(0);
      mod.steeringSpark.set(0);
    }
  }
}

