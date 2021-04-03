package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.intake.ToggleFlywheelCommand;
import frc.robot.commands.intake.ToggleIntakePositionCommand;
import frc.robot.commands.swerve.SwerveResetModulesCommand;
import frc.robot.commands.hopper.ToggleHopperCommand;
import frc.robot.commands.intake.DummyIntakeCommand;

public class OI {
    public XboxController xboxController;
    public JoystickButton toggleFlywheelButton, toggleIntakePositionButton, toggleHopperButton, resetModuleButton;

    public OI() {
        System.out.println("OI constructor");

        xboxController = new XboxController(RobotMap.XBOX_CONTROLLER);

        toggleFlywheelButton = new JoystickButton(xboxController, RobotMap.XBOX_A);
        toggleFlywheelButton.whenActive(new ToggleFlywheelCommand());
        
        toggleIntakePositionButton = new JoystickButton(xboxController, RobotMap.XBOX_B);
        toggleIntakePositionButton.whenActive(new ToggleIntakePositionCommand());

        resetModuleButton = new JoystickButton(xboxController, RobotMap.XBOX_Y);
        resetModuleButton.whenActive(new SwerveResetModulesCommand());

        toggleHopperButton = new JoystickButton(xboxController, RobotMap.XBOX_X);
        toggleHopperButton.whenActive(new ToggleHopperCommand());
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
