package frc.robot.subsytem.SwerveDrive;

import com.revrobotics.CANSparkMax;

import frc.robot.util.AbsoluteEncoder;
import frc.robot.util.PID;

public class Module {
    public CANSparkMax driveSpark;
    public CANSparkMax steerSpark;
    public AbsoluteEncoder steerEncoder;

    public double desiredWheelSpeed = 0;
    public double desiredSteeringAngle = 0;

    public PID steeringAnglePid = new PID(.001, 0, 0);

    public Module (CANSparkMax driveSpark, CANSparkMax steerSpark, AbsoluteEncoder steerEncoder, PID steeringAnglePid) {
        this.driveSpark = driveSpark;
        this.steerSpark = steerSpark;
        this.steerEncoder = steerEncoder;
        this.steeringAnglePid = steeringAnglePid;
    }
}
