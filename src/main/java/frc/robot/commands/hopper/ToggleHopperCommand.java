package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.Robot;

public class ToggleHopperCommand extends CommandBase {
    private HopperSubsystem hopperSubsystem;

    public ToggleHopperCommand(HopperSubsystem hopperSubsystem) {
        this.hopperSubsystem = hopperSubsystem;
        addRequirements(Robot.hopperSubsystem);
    }

    @Override public void execute() {
        Robot.intakeSubsystem.toggleHopper();
    }

    @Override boolean isFinished() {
        return true;
    }
}
