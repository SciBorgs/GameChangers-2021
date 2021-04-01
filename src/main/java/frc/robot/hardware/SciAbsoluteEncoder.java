package frc.robot.hardware;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.util.SciMath;

public class SciAbsoluteEncoder
{
  private DutyCycleEncoder rawEncoder;

  private double offset;
  private boolean flip;

  public SciAbsoluteEncoder(int port)
  {
    this(port, 1);
  }

  public SciAbsoluteEncoder(int port, double gearRatio)
  {
    this(port, gearRatio, 0);
  }

  public SciAbsoluteEncoder(int port, double gearRatio, double initialAngle)
  {
    this(port, gearRatio, initialAngle, false);
  }

  public SciAbsoluteEncoder(int port,
                            double gearRatio,
                            double initialAngle,
                            boolean flip)
  {
    this.flip = flip;

    rawEncoder = new DutyCycleEncoder(port);

    rawEncoder.setDistancePerRotation(gearRatio * SciMath.TAU);

    while (true) {
      if (rawEncoder.getDistance() != 0) {
        break;
      }
    }

    offset = initialAngle - rawEncoder.getDistance();
  }

  public double getAngle()
  {
    double angle = rawEncoder.getDistance() + offset;
    return SciMath.normalizeAngle(flip ? (2 * Math.PI - angle) : angle);
  }
}