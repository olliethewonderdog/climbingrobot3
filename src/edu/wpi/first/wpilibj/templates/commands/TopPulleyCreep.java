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
public class TopPulleyCreep extends CommandBase {
     float speed;
     float time;
   
    
    public TopPulleyCreep(float s) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(toppulley);
        speed=s; 
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
        return false ;
    }

    // Called once after isFinished returns true
    protected void end() {
         leftpulley.setTape(0.f);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}