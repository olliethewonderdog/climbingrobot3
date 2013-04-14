/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;
/**
 *
 * @author Brinton
 */
public class LeftPulleySetLength extends CommandBase {
   double tapeLength;
    double error;
    double speed;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
   public LeftPulleySetLength(double T, double e,double s) {
        requires(leftpulley);                                
        tapeLength = T;
        error = e;
        speed=s;
    }
    // Called just before this Command runs the first time

    protected void initialize() {
        leftpulley.setTape(speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(leftpulley.getTapeLength() - tapeLength) < error);
    }

    // Called once after isFinished returns true
    protected void end() {
        leftpulley.setTape(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
