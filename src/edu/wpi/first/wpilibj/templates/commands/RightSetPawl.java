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
public class RightSetPawl extends CommandBase {

    private boolean pawlPosition;

    public RightSetPawl(boolean pawlposition) {
        requires(rightpulley);
        pawlPosition = pawlposition;
        
    }
    // Called just before this Command runs the first time
    protected void initialize() { 
        rightpulley.setLock(pawlPosition);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        ;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        boolean b;
        b= rightpulley.pawlLocked;
        return (pawlPosition==b);
    }

    // Called once after isFinished returns true
    protected void end() {
        rightpulley.setTape(0);
        boolean b;
        b= rightpulley.pawlLocked;
        SmartDashboard.putBoolean("RightPawl state requested=",pawlPosition );
        SmartDashboard.putBoolean("RightPawl  ended state achieved=",b ); 
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
