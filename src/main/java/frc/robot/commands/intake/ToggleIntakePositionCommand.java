package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ToggleIntakePositionCommand extends CommandBase {
    private Timer timer;
    public ToggleIntakePositionCommand() {
        System.out.println("Toggle Position Command Constructor");
        addRequirements(Robot.intakeSubsystem);
        timer = new Timer();
    }

    @Override
    public void initialize() {
        //Robot.intakeSubsystem.toggleIntakePosition();
        timer.start();
    }

    @Override public void execute() {
        System.out.println("Toggle Position Command Execute");
        Robot.intakeSubsystem.liftSpark.set(0.3);
    }

    @Override
    public boolean isFinished() {        
        return timer.get() >= 0.1;
    }

    @Override
    public void end(boolean interrupted) {
        Robot.intakeSubsystem.stopIntakeLift();
    }
}
