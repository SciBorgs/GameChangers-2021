package frc.robot.subsystems;

import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.hardware.SciAbsoluteEncoder;
import frc.robot.hardware.SciSpark;
import frc.robot.util.PID;
import frc.robot.util.SciMath;

public class IntakeSubsystem extends SubsystemBase {

    private SciSpark flywheelSpark;
    private boolean flywheelSpinning;
    private final double FLYWHEEL_SPEED = 0.1;

    private SciSpark liftSpark;
    private CANEncoder liftEncoder;
    private PID liftPID;
    private double intakeSetpoint;
    private double intakeAngleChange = Math.toRadians(40); //The angle between the up and down positions of the intake
    private final double intakeStartingAngle = Math.toRadians(10); //Set to some positive value so that PID does not flip out around 0
    private final double liftTolerance = Math.toRadians(5);

    public IntakeSubsystem() {
        final double FLYWHEEL_SPARK_GEAR_RATIO = 1.0 / 1;
        flywheelSpinning = false;

        final double LIFT_SPARK_GEAR_RATIO = 1.0 / 1;
        final double LIFT_ENCODER_GEAR_RATIO = 1.0 / 1; // wheel rotation to encoder rotation
        intakeSetpoint = intakeStartingAngle;


        flywheelSpark = new SciSpark(RobotMap.INTAKE_FLYWHEEL_SPARK, FLYWHEEL_SPARK_GEAR_RATIO);
        liftSpark = new SciSpark(RobotMap.INTAKE_LIFT_SPARK, LIFT_SPARK_GEAR_RATIO);
        liftEncoder = liftSpark.getEncoder();
        liftEncoder.setPosition(intakeStartingAngle);
        liftEncoder.setPositionConversionFactor(2 * Math.PI * LIFT_ENCODER_GEAR_RATIO);

        liftPID = new PID(0.3, 0, 0);
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

    public boolean isLiftClose() {
        return (Math.abs(intakeSetpoint - liftEncoder.getPosition()) < liftTolerance);
    }
}