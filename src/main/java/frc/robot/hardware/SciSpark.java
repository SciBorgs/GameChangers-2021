package frc.robot.hardware;

import com.revrobotics.CANSparkMax;
import frc.robot.util.SciMath;

public class SciSpark extends CANSparkMax
{

  public SciSpark(int port)
  {
    this(port, 1);
  }

  public SciSpark(int port, double gearRatio)
  {
    super(port, MotorType.kBrushless);

    double multiplier = gearRatio * SciMath.TAU;
    getEncoder().setPositionConversionFactor(multiplier);
    getEncoder().setVelocityConversionFactor(multiplier / 60);
  }
}