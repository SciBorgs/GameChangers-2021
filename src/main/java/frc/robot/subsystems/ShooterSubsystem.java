package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.hardware.SciAbsoluteEncoder;
import frc.robot.hardware.SciSpark;
import frc.robot.util.PID;

public class ShooterSubsystem extends SubsystemBase {
    public SciSpark shooterLeftSpark, shooterRightSpark;
    public SciSpark hoodSpark;
    //public SciAbsoluteEncoder hoodEncoder;
    private static final double SHOOTER_SPEED = -0.6;
    private boolean flywheelTurning;

    public ShooterSubsystem() {
        shooterLeftSpark = new SciSpark(RobotMap.SHOOTER_LEFT_SPARK, 1);
        shooterRightSpark = new SciSpark(RobotMap.SHOOTER_RIGHT_SPARK, 1);
        flywheelTurning = false;
        //shooterLeftSpark.setIdleMode(IdleMode.kCoast);
        shooterLeftSpark.follow(shooterRightSpark, true);
        hoodSpark = new SciSpark(RobotMap.HOOD_SPARK, 1/600);
        //hoodEncoder = new SciAbsoluteEncoder(RobotMap.HOOD_ENCODER, 1/600);
    };

    public void toggleShooter() {
        if (flywheelTurning) {
            shooterRightSpark.set(0);
            
        } else {
            shooterRightSpark.set(-SHOOTER_SPEED);
        }
        flywheelTurning = !flywheelTurning;
    }

    public void setHoodSpeed(double speed) {
        hoodSpark.set(speed);
    }
}
