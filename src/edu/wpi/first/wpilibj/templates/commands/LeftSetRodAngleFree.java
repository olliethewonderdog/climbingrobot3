/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Brinton
 *
 *
 */
public class LeftSetRodAngleFree extends CommandBase {

    private double goalTapeLength;
    private double error;
    private double dGoaltAng;
    SmartDashboard smartdashboard;

    public LeftSetRodAngleFree(double dTAng, double T) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(leftrod);
        goalTapeLength = T;
        // angle of the tape relative to the frame in degrees
        dGoaltAng = dTAng;

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    /**
     * Called repeatedly when this Command is scheduled to run This moves the
     * servo slowly toward a given target angle. The servo speed is reduced to
     * .33 of its maximum, about 50 degrees per second This has two advantages.
     * It does not jerk the rods around. It causes the tape and rod movements to
     * occur at similar rates. The risk with allowing the rod to move quickly to
     * position while the tape is moving slowly is that the servo may think it
     * is finished while the tape is still moving
     */
    protected void execute() {
        // 
        smartdashboard.putNumber("Left Tape Length", leftrod.getTapeLength());
        smartdashboard.putNumber("Frame Angle", leftrod.getFrameAngle());
        smartdashboard.putNumber("Left Rod Servo",
                leftrod.calcServoFromAngle(
                false, Math.toRadians(dGoaltAng), leftrod.getTapeLength()));
        leftrod.setRodAngleFree(.33, dGoaltAng);
    }

    // Make this return true when this Command no longer needs to run execute()
    // tapelength has reached goal and also
    // servo has reached goal
    protected boolean isFinished() {
        //calulates final servo position for 
        double servValfinal;
        servValfinal =
                leftrod.calcServoFromAngle(true, Math.toRadians(dGoaltAng), goalTapeLength);
        double t;
        t = leftrod.getTapeLength();
        boolean tapeDone;
        tapeDone = (Math.abs(t - goalTapeLength) < .3);
        boolean servDone;
        servDone = leftrod.isServoFinished(servValfinal);
        return (servDone & tapeDone);

    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
