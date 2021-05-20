package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.intake.ToggleFlywheelCommand;
import frc.robot.commands.intake.ToggleIntakePositionCommand;
import frc.robot.commands.swerve.SwerveResetModulesCommand;
import frc.robot.commands.shooter.LowerHoodAngleCommand;
import frc.robot.commands.shooter.RaiseHoodAngleCommand;
import frc.robot.commands.shooter.ShooterToggleCommand;
import frc.robot.commands.hopper.ToggleHopperCommand;

public class OI {
    public XboxController xboxController;
    public JoystickButton toggleFlywheelButton, toggleIntakePositionButton, resetModuleButton, toggleHopperButton, raiseHoodButton, lowerHoodButton, toggleShooterButton;

    public OI() {
        System.out.println("OI constructor");

        xboxController = new XboxController(RobotMap.XBOX_CONTROLLER);
        
        toggleFlywheelButton = new JoystickButton(xboxController, RobotMap.XBOX_B);
        toggleFlywheelButton.whenActive(new ToggleFlywheelCommand());

        toggleIntakePositionButton = new JoystickButton(xboxController, RobotMap.XBOX_BUMPER_RIGHT);
        toggleIntakePositionButton.whenActive(new ToggleIntakePositionCommand());
        

        toggleShooterButton = new JoystickButton(xboxController, RobotMap.XBOX_A);
        toggleShooterButton.whenActive(new ShooterToggleCommand());

        resetModuleButton = new JoystickButton(xboxController, RobotMap.XBOX_Y);
        resetModuleButton.whenActive(new SwerveResetModulesCommand());

        toggleHopperButton = new JoystickButton(xboxController, RobotMap.XBOX_X);
        toggleHopperButton.whenHeld(new ToggleHopperCommand());

        raiseHoodButton = new JoystickButton(xboxController, RobotMap.XBOX_TRIGGER_LEFT);
        raiseHoodButton.whenHeld(new RaiseHoodAngleCommand());

        lowerHoodButton = new JoystickButton(xboxController, RobotMap.XBOX_TRIGGER_RIGHT);
        lowerHoodButton.whenHeld(new LowerHoodAngleCommand());
    }

    public double getXboxLeftX() {
        return xboxController.getRawAxis(RobotMap.XBOX_LEFT_JOY_X);
    }
    public double getXboxLeftY() {
        return xboxController.getRawAxis(RobotMap.XBOX_LEFT_JOY_Y);
    }
    public double getXboxRightX() {
        return xboxController.getRawAxis(RobotMap.XBOX_RIGHT_JOY_X);
    }
    public double getXboxRightY() {
        return xboxController.getRawAxis(RobotMap.XBOX_RIGHT_JOY_Y);
    }

}
