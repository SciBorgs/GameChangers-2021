package frc.robot.subsystems.swerve;

import frc.robot.hardware.SciAbsoluteEncoder;
import frc.robot.hardware.SciSpark;
import frc.robot.util.PID;

public class Module
  {
    public SciSpark drivenSpark;
    public SciSpark steeringSpark;
    public SciAbsoluteEncoder steeringEncoder;
    public PID steeringAnglePID;

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