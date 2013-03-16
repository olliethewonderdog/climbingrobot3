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

    // possibly combine servo and pulley?
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    LeftPulleySetLength(double T, double e, int d) {
        requires(leftpulley);                                //only 1 subsystem per command
        tapeLength = T;
        error = e;
        direction = d;
    }
    double tapeLength;
    double error;
    int direction;
    // Called just before this Command runs the first time

    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    //possible conflict between 2 error values?
    protected void execute() {
        leftpulley.setTapelength(tapeLength, error, direction);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(leftpulley.getTapeLength() - tapeLength) < error;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
