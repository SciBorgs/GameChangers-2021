package frc.robot.util;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Hardware {

    public static final int SECONDS_PER_MINUTE = 60;
    public static double ROTATIONS_TO_RADIANS_MULTIPLIER = 2 * Math.PI;
    public static double DEGREES_TO_RADIANS_MULTIPLIER = Math.PI / 180;
 
    public static CANSparkMax createSparkMax(int port, double gearRatio) {
        CANSparkMax spark = new CANSparkMax(port, MotorType.kBrushless);
        
        double multiplier = gearRatio * ROTATIONS_TO_RADIANS_MULTIPLIER;
        spark.getEncoder().setPositionConversionFactor(multiplier);
        spark.getEncoder().setVelocityConversionFactor(multiplier / SECONDS_PER_MINUTE);
        
        return spark;
    }

    public double getPigeonHeading(PigeonIMU pigeon) {
        return MathHelper.normalizeAngle(MathHelper.DegreesToRadians(pigeon.getFusedHeading()));
    }

}

