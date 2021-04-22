package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.hardware.SciAbsoluteEncoder;
import frc.robot.hardware.SciSpark;

public class ShooterSubsystem extends SubsystemBase {
    public SciSpark shooterLeftSpark, shooterRightSpark;
    public SciSpark hoodSpark;
    public SciAbsoluteEncoder hoodEncoder;
    private static final double SHOOTER_SPEED = 0.1;
    private boolean flywheelTurning;

    public ShooterSubsystem() {
        shooterLeftSpark = new SciSpark(RobotMap.SHOOTER_LEFT_SPARK, 1);
        shooterRightSpark = new SciSpark(RobotMap.SHOOTER_RIGHT_SPARK, 1);
        flywheelTurning = false;
        shooterRightSpark.setIdleMode(IdleMode.kCoast);
        hoodSpark = new SciSpark(RobotMap.HOOD_SPARK, 1/600);
        hoodEncoder = new SciAbsoluteEncoder(RobotMap.HOOD_ENCODER, 1/600);
    };

    public void toggleShooter() {
        if (flywheelTurning) {
            shooterLeftSpark.set(0);
        } else {
            shooterLeftSpark.set(SHOOTER_SPEED);
        }
        flywheelTurning = !flywheelTurning;
    }
}
