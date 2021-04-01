package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.intake.ToggleFlywheelCommand;
import frc.robot.commands.intake.ToggleIntakePositionCommand;

public class OI {
    public XboxController xboxController;
    public JoystickButton toggleFlywheelButton, toggleIntakePositionButton;

    public OI() {

        xboxController = new XboxController(RobotMap.XBOX_CONTROLLER);

        toggleFlywheelButton = new JoystickButton(xboxController, RobotMap.XBOX_A);
        toggleFlywheelButton.whenActive(new ToggleFlywheelCommand(Robot.intakeSubsystem));

        toggleIntakePositionButton = new JoystickButton(xboxController, RobotMap.XBOX_B);
        toggleIntakePositionButton.whenActive(new ToggleIntakePositionCommand(Robot.intakeSubsystem));

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
