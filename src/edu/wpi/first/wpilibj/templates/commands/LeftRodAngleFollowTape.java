/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

/**
 *
 * @author Brinton
 */
public class LeftRodAngleFollowTape extends CommandBase {
   
private boolean floor;
private double tapegoal;
       
   
    public LeftRodAngleFollowTape(boolean floor,double tapeGoal) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires (leftrod);
        this.floor=floor;
        this.tapegoal=tapeGoal;
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
       leftrod.adjustAngleClimbing(floor);
 
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       // when is this finished? when tapelength within .5 icnes
        double t1;
        t1= leftrod.getTapeLength();
        return (Math.abs(t1-this.tapegoal)<.5);     
        }
       

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}