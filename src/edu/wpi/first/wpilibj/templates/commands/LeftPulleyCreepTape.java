/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;
/**
 *
 * @author laptop
 */
public class LeftPulleyCreepTape extends CommandBase {
     double speed;
    
    public LeftPulleyCreepTape(double time, double speedFraction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(leftpulley);
        this.setTimeout(time);
        speed=speedFraction;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        leftpulley.setCreepTape(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}