package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class ToggleIntakePositionCommand extends CommandBase {
    private IntakeSubsystem intakeSubsystem;

    public ToggleIntakePositionCommand(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;

        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        this.intakeSubsystem.toggleIntakePosition();
    }

    @Override public void execute() {
        this.intakeSubsystem.moveIntakeToPosition();
    }

    @Override
    public boolean isFinished() {        
        return this.intakeSubsystem.isLiftClose();
    }
}
