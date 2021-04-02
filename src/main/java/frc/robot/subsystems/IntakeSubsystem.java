package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.hardware.SciSpark;
import frc.robot.util.PID;

public class IntakeSubsystem extends SubsystemBase {

    private SciSpark flywheelSpark;
    private boolean flywheelSpinning;
    private final double FLYWHEEL_SPEED = 1.0;

    public SciSpark liftSpark;
    public CANEncoder liftEncoder;
    private PID liftPID;
    private double intakeSetpoint;
    private double intakeAngleChange = Math.toRadians(95); //The angle between the up and down positions of the intake
    private final double intakeStartingAngle = Math.toRadians(10); //Set to some positive value so that PID does not flip out around 0
    private final double liftTolerance = Math.toRadians(5);

    public IntakeSubsystem() {
        final double FLYWHEEL_SPARK_GEAR_RATIO = 9.0 / 1;
        flywheelSpinning = false;

        final double LIFT_SPARK_GEAR_RATIO = 1.0 / 63; // wheel rotation to encoder rotation
        intakeSetpoint = intakeStartingAngle;

        flywheelSpark = new SciSpark(RobotMap.INTAKE_FLYWHEEL_SPARK, FLYWHEEL_SPARK_GEAR_RATIO);
        flywheelSpark.setInverted(true);
        liftSpark = new SciSpark(RobotMap.INTAKE_LIFT_SPARK, LIFT_SPARK_GEAR_RATIO);
        liftSpark.setInverted(true);
        liftSpark.setIdleMode(IdleMode.kCoast);
        liftEncoder = liftSpark.getEncoder();
        liftEncoder.setPosition(intakeStartingAngle);

        liftPID = new PID(0.1, 0, 0);
    }

    public void toggleFlywheel() {
        if (flywheelSpinning) {
            flywheelSpark.set(0.);
        } else {
            flywheelSpark.set(FLYWHEEL_SPEED);
        }
        flywheelSpinning = !flywheelSpinning;
    }

    public void toggleIntakePosition() {
        if (intakeSetpoint == intakeStartingAngle) {
           intakeSetpoint += intakeAngleChange;
        } else {
            intakeSetpoint -= intakeAngleChange;
        }
    }

    public void moveIntakeToPosition() {
        liftSpark.set(liftPID.getOutput(intakeSetpoint, liftEncoder.getPosition()));
    }

    public void stopIntakeLift() {
        liftSpark.set(0);
    }

    public boolean isLiftClose() {
        return (Math.abs(intakeSetpoint - liftEncoder.getPosition()) < liftTolerance);
    }
}