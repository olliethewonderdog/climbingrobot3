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
public class LeftRodFollowTape extends CommandBase {

    private boolean floor;
    private double tapegoal;
    private double error;
    private int pulley;
    SmartDashboard smartdashboard;
    public LeftRodFollowTape(boolean floor, double tapeGoal) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(leftrod);
        this.floor = floor;
        this.tapegoal = tapeGoal;
        this.error=.3;
       this.pulley=1;
    }
    // Called just before this Command runs the first time

    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double tapelength;
        double dtapeangle;
        tapelength = leftrod.getTapeLength();
        SmartDashboard.putNumber("leftFollowTape TapeLength left", tapelength);
        SmartDashboard.putNumber("leftFollowTape Frame Angle ",
                    SI.getrFrameAngle());
       dtapeangle = FrameMath.getClimbTapeAngle(floor, tapelength, pulley);
       SmartDashboard.putNumber("leftFollowTape Climb Tape Angle", 
               FrameMath.getClimbTapeAngle(floor, tapelength, pulley));
        SmartDashboard.putNumber("leftFollowTape Servo Value",
       FrameMath.calcServoFromAngle(true, Math.toRadians(dtapeangle), tapegoal,pulley));
        leftrod.adjustAngleClimbing(floor);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        // when is this finished? when tapelength within .5 icnes
        double t1;
        t1 = leftrod.getTapeLength();
        return (Math.abs(t1 - this.tapegoal) < error);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
