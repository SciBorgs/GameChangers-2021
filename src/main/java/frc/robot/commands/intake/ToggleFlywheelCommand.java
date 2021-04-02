package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ToggleFlywheelCommand extends CommandBase {
    public ToggleFlywheelCommand()
    {
        addRequirements(Robot.intakeSubsystem);
    }

    @Override public void execute()
    {
        Robot.intakeSubsystem.toggleFlywheel();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
