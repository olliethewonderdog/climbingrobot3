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
public class RightRodFollowTape extends CommandBase {
    private boolean floor;
    private double tapegoal;
    private double error;
    private int pulley;
    SmartDashboard smartdashboard;
    
    public RightRodFollowTape(boolean floor, double tapeGoal) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(rightrod);
        this.floor = floor;
        this.tapegoal = tapeGoal;
        this.error=.3;
        this.pulley=2;
    }
    // Called just before this Command runs the first time

    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double tapelength;
        double dtapeangle;
        tapelength = rightrod.getTapeLength();
        SmartDashboard.putNumber("rightFollowTape TapeLength right", tapelength);
        SmartDashboard.putNumber("rightFollowTape Frame Angle ",
                    SI.getrFrameAngle());
       dtapeangle = FrameMath.getClimbTapeAngle(floor, (float)tapelength, pulley);
       SmartDashboard.putNumber("rightFollowTape Climb Tape Angle", 
               FrameMath.getClimbTapeAngle(floor, (float)tapelength, pulley));
        SmartDashboard.putNumber("rightFollowTape Servo Value",
       FrameMath.calcServoFromAngle(true, (float)Math.toRadians(dtapeangle), (float)tapegoal,pulley));
        rightrod.adjustAngleClimbing(floor);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        // when is this finished? when tapelength within .5 icnes
        double t1;
        t1 = rightrod.getTapeLength();
        return (Math.abs(t1 - (float)this.tapegoal) < error);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
