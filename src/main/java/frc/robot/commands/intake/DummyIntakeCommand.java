package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DummyIntakeCommand extends CommandBase {
    public DummyIntakeCommand() {
        System.out.println("Dummy Command Construct");
    }

    @Override
    public void execute() {
        System.out.println("Dummy Command Execute");
    }
}
