package frc.robot.util;

public class SciMath
{
  public static final double TAU = 2 * Math.PI;

  public static double normalizeAngle(double angle)
  {
    return angle - (Math.floor(angle / TAU) * TAU);
  }
}