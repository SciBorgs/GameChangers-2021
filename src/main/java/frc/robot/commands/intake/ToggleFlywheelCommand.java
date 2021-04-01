package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class ToggleFlywheelCommand extends CommandBase {
    private IntakeSubsystem intakeSubsystem;

    public ToggleFlywheelCommand(IntakeSubsystem intakeSubsystem)
    {
        this.intakeSubsystem = intakeSubsystem;

        addRequirements(intakeSubsystem);
    }

    @Override public void execute()
    {
        this.intakeSubsystem.toggleFlywheel();
    }
}
