package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ToggleFlywheelCommand extends CommandBase {
    public ToggleFlywheelCommand()
    {
        System.out.println("Toggle Flywheel Command Constructor");
        addRequirements(Robot.intakeSubsystem);
    }

    @Override public void execute()
    {
        System.out.println("Toggle Flywheel Command Execute");
        Robot.intakeSubsystem.toggleFlywheel();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
