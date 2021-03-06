/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;
import edu.wpi.first.wpilibj.templates.FrameMath;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.SI;
/**
 *
 * @author Brinton
 */
public class TopRodFollowTape extends CommandBase {
    private boolean floor;
    private float tapegoal;
    private float error;
    private int pulley;
    SmartDashboard smartdashboard;
    
    public TopRodFollowTape(boolean floor, float tapeGoal) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(toprod);
        this.floor = floor;
        this.tapegoal = tapeGoal;
        this.error=.3f;
        this.pulley=0;
    }
    // Called just before this Command runs the first time

    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
       float tapelength;
       float dtapeangle;
       tapelength = toprod.getTapeLength();
       SmartDashboard.putNumber("topFollowTape TapeLength top", tapelength);
       SmartDashboard.putNumber("topFollowTape Frame Angle ",
                    SI.getrFrameAngle());
       dtapeangle = FrameMath.getClimbTapeAngle(floor, (float)tapelength, pulley);
       SmartDashboard.putNumber("topFollowTape Climb Tape Angle", 
               FrameMath.getClimbTapeAngle(floor, (float)tapelength, pulley));
       SmartDashboard.putNumber("topFollowTape Servo Value",
       FrameMath.calcServoFromAngle(true, (float)Math.toRadians(dtapeangle), (float)tapegoal,pulley));
       
        toprod.adjustAngleClimbing(floor);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        // when is this finished? when tapelength within error
        return (Math.abs(toprod.getTapeLength() - this.tapegoal) < error);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
