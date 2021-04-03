package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.Robot;

public class ToggleHopperCommand extends CommandBase {

    public ToggleHopperCommand(HopperSubsystem hopperSubsystem) {
        addRequirements(Robot.hopperSubsystem);
    }

    @Override public void execute() {
        Robot.hopperSubsystem.toggleHopper();
    }

    @Override public boolean isFinished() {
        return true;
    }
}
