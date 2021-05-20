package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ToggleHopperCommand extends CommandBase {
    public ToggleHopperCommand()
    {
        addRequirements(Robot.hopperSubsystem);
    }

    @Override
    public void execute() {
        Robot.hopperSubsystem.setHopperSpeed(0.7);
        System.out.println("Toggle Hopper Execute");
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override public void end(boolean interrupted) {
        Robot.hopperSubsystem.setHopperSpeed(0.0);
        System.out.println("Toggle Hopper END");
    }
}