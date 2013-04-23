/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;
import  edu.wpi.first.wpilibj.templates.OI;


/**
 *
 * @author laptop
 */
public class TopPulleyCreepTape extends CommandBase {
     double speed;
     double time;
   
    
    public TopPulleyCreepTape(double s, double t) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(toppulley);
        requires(toprod);
        speed=s;
        time=t;  
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        leftpulley.setTape(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return OI.middleFinish ;
    }

    // Called once after isFinished returns true
    protected void end() {
         leftpulley.setTape(0.);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}