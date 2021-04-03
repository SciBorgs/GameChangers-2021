package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ToggleHopperCommand extends CommandBase {

    @Override public void execute() {
        Robot.hopperSubsystem.toggleHopper();
    }

    @Override public boolean isFinished() {
        return true;
    }
}
