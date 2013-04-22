/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Brinton
 */
public class LeftSetPawl extends CommandBase {

    private boolean pawlPosition;

    public LeftSetPawl(boolean pawlposition) {
        requires(leftpulley);
        pawlPosition = pawlposition;
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        leftpulley.setLock(pawlPosition);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        boolean b;
        b= leftpulley.pawlLocked;
        return (pawlPosition==b);
        
    }

    // Called once after isFinished returns true
    protected void end() { 
        boolean b;
        b= leftpulley.pawlLocked;
        SmartDashboard.putBoolean("LeftPawl state requested=",pawlPosition ); 
        SmartDashboard.putBoolean("LeftPawl state achieved=",b ); 
        leftpulley.setTape(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
       end();
    }
}
