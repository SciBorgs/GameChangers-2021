package frc.robot.subsytem;

import com.ctre.phoenix.sensors.PigeonIMU;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.util.*;

public class SwerveDriveSubsystem {
    public static enum Mode {
        FIELD_CENTRIC, ROBOT_CENTRIC
    }

    private static final int MODULE_COUNT = 4;
    public Mode mode;

    Module BL = new Module(Hardware.createSparkMax(1, 3.0 / 40), 
                           Hardware.createSparkMax(2, 1.0 / 60), 
                           new AbsoluteEncoder(0, 1.0 / 5, MathHelper.degreesToRadians(90), true), 
                           new PID(0.82, 0, 0));

    Module BR = new Module(Hardware.createSparkMax(5, 3.0 / 40), 
                           Hardware.createSparkMax(6, 1.0 / 60), 
                           new AbsoluteEncoder(1, 1.0 / 5, MathHelper.degreesToRadians(90), true), 
                           new PID(0.82, 0, 0));   
    
    Module FL = new Module(Hardware.createSparkMax(3, 3.0 / 40), 
                           Hardware.createSparkMax(4, 1.0 / 60), 
                           new AbsoluteEncoder(3, 1.0 / 5, MathHelper.degreesToRadians(90), true), 
                           new PID(0.82, 0, 0));

    Module FR = new Module(Hardware.createSparkMax(7, 3.0 / 40), 
                           Hardware.createSparkMax(8, 1.0 / 60), 
                           new AbsoluteEncoder(2, 1.0 / 5, MathHelper.degreesToRadians(90), true), 
                           new PID(0.82, 0, 0));
    


    public SwerveDriveSubsystem(XboxController xbox, PigeonIMU pigeon, Mode mode) {
        this.mode = mode;
    }

    public SwerveDriveSubsystem(XboxController xbox, PigeonIMU pigeon) {
        SwerveDriveSubsystem(xbox, pigeon, Mode.FIELD_CENTRIC);
    }

    public SwerveDriveSubsystem() {
        this.mode = Mode.FIELD_CENTRIC;
    }

    public void switchMode() {
        this.mode = mode == Mode.FIELD_CENTRIC ? Mode.ROBOT_CENTRIC : Mode.FIELD_CENTRIC;
    }
    
    public void joystickDrive()
    {
      /* double v_x = xbox.GetX(frc::GenericHID::JoystickHand::kLeftHand); */
    
      /**
       * Up -> Positive
       * Down -> Negative
       */
      /* double v_y = -xbox.GetY(frc::GenericHID::JoystickHand::kLeftHand); */
    
      /**
       * Left -> Negative -> CCW
       * Right -> Positive -> CW
       */
      /* double omega = xbox.GetX(frc::GenericHID::JoystickHand::kRightHand); */
    
      drive(0, 0.1, 0);
    }


    private void drive(double v_x, double v_y, double omega) {
        int HALF_TRACK_LENGTH = 30 / 2;
        int HALF_TRACK_WIDTH =  (int) (29.579 / 2);

        double[] wheel_v_x = {v_x - omega * HALF_TRACK_LENGTH, v_x + omega * HALF_TRACK_LENGTH};
        double[] wheel_v_y = {v_y + omega * HALF_TRACK_WIDTH,  v_y - omega * HALF_TRACK_WIDTH};
    
        setDesiredModuleStrategy(BL, wheel_v_x[0], wheel_v_x[0]);
        setDesiredModuleStrategy(BR, wheel_v_x[0], wheel_v_x[1]);
        setDesiredModuleStrategy(FL, wheel_v_x[1], wheel_v_x[0]);
        setDesiredModuleStrategy(FR, wheel_v_x[1], wheel_v_x[1]);
        
        double max = Math.max(Math.max(BL.desiredWheelSpeed, BR.desiredWheelSpeed), Math.max(FL.desiredWheelSpeed, FR.desiredWheelSpeed));
        
        executeDesiredModuleStrategies(BL, max);
        executeDesiredModuleStrategies(BR, max);
        executeDesiredModuleStrategies(FL, max);
        executeDesiredModuleStrategies(FR, max);
    }

    private void setDesiredModuleStrategy(Module module, double wheel_v_x, double wheel_v_y) {
        module.desiredWheelSpeed = Math.sqrt(Math.pow(wheel_v_x, 2) + Math.pow(wheel_v_y, 2));
        module.desiredSteeringAngle = MathHelper.normalizeAngle(Math.atan2(wheel_v_x, wheel_v_y));
    }

    private void executeDesiredModuleStrategies(Module module, double max) {
        if (max > 1) {
            module.desiredWheelSpeed /= max;
        }
    
        module.driveSpark.set(module.desiredWheelSpeed);
        module.steerSpark.set(module.steeringAnglePid.getOutput(module.desiredSteeringAngle, 
                                                                module.steerEncoder.getAbsoluteEncoderAngle()));
    }

}
