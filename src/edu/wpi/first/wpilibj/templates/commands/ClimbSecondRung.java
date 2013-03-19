/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author Brinton
 */
public class ClimbSecondRung extends CommandGroup {

    Gyro gyro = new Gyro(RobotMap.GYRO_CHAN);
    private double dFrameAngle;

    public ClimbSecondRung() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order. // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // 1.Extend top pulley to 45 inches while maintaining rod angle of 45
        // degrees below the frame

        // addParallel(new TopPulleySetLength(45,.1,1));
        // addParallel(new TopSetRodAngleFree(-45,45));

        // 2.Lower the top rod to 60 degrees to hook rung

        // addSequential(new TopSetRodAngleFree(-60,45));

        // 3. Retract top tape to 26 inches 
        //
        // addParallel (new TopPulleySetLength(26,.1,-1));
        // addParallel (new TopRodAngleFollowTape(26))
        //
        // 4.We need to break up sets parallel commands with a sequential command that 
        // does something trivial, in this case, creep the center tape for a tenth of a second
        // otherwise it executes the whole set of consecutive parallel commands
        // in parallel. 
        // 
        // addSequential (newTopPulleyCreepTape(.1,.1));
        //
        // 5. Unlock pawls on sides while creeping center tape.
        // Issue: since the top pulley does not have a pawl
        // it will need to hold its position while the sidepulleys disengage 
        // from the the lower rung, extend and grab the upper rung.
        // The solution is a "CreepTape" command that retracts the pulley
        // for a few seconds, at a fraction of its maximum voltage. Those 
        // parameters,time and fractional speed, 
        //  are a guess.  We will need to tune them.
        // Here allow  .5 seconds for the pawls to disengage.
        //during the CreepTape commands, we do not adjust the angle of the tape.
        // because the tapelength should not change enough to matter.
        //
        addParallel(new LeftSetPawl(false));
        // addParallel(new RightSetPawl(false));
        // addParallel (new TopPulleyCreepTape(.5,.1));
        //
        // 6. Get  frameangle in degrees
        //
        dFrameAngle = gyro.getAngle();
        //
        // 7. Add a a trivial command to break up the consecutive parallel commands.
        // In this case,creep for one tenth of a second.

        // addSequential (new TopPulleyCreepTape(.1,.1));

        // 8.Extend the side tapes to reach the next rung  
        // at an angle that will allow them to hook the rung.
        //  Hold position with the center tape while they do this.
        //  Allow 5 seconds for the extension.( 2 may be enough)
        //
        //addParallel (new TopPulleyCreepTape(5,.1));
        addParallel(new LeftPulleySetLength(37, .1, 1));
        addParallel(new LeftSetRodAngleFree( 85 - dFrameAngle,37));
        // addParallel(new RightPulleySetLength(37,.1,1));
        // addParallel(new RightSetRodAngleFree(85-dframeangle,37));
        //
        // 9.Add a a trivial command to break up the consecutive parallel command.
        // In this case,creep for one tenth of a second. 
        //
        // addSequential (new TopPulleyCreepTape(.1,.1);
        //
        // 10. Now lower the side rods to hook the rung. Allow two seconds
        //
        // addParallel (new TopPulleyCreepTape(2,.1));
        addParallel(new LeftSetRodAngleFree( 60 - dFrameAngle,37));
        // addParallel(new RightSetRodAngleFree(60-dframeangle,37));
        //
        // 11.Add a a trivial command to break up the consecutive parallel command.
        // In this case,creep for one tenth of a second. 
        //
        // addSequential (new TopPulleyCreepTape(.1,.1));
        //
        // 12.  Lock side pawls and retract the side tapes to 20 inches,
        // maintaining angle. Creep for  2 seconds to hold
        // position till the pawls lock
        //
        //addParallel (new TopPulleyCreepTape(1,.1));
        addParallel(new LeftSetPawl(true));
        // addParallel (new RightSetPawl(true));
        addParallel(new LeftPulleySetLength(20, .1, -1));
        addParallel(new LeftRodAngleFollowTape(false, 20));
        // addParallel(new RightPulleySetLength(20,.1,-1));
        // addParallel(new RightAdjustRodAngleClimb(false,20));
        //
        // 13. LIft up center rod angle  based on frameangle
        dFrameAngle = gyro.getAngle();
        //
        // addSequential(new TopSetRodAngleFree(85-dFrameAngle,25));
        //
        // 14. Retract sidetapes to 5.5 inch
        //
        //
        addParallel(new LeftPulleySetLength(5.5, .1, -1));
        addParallel(new LeftRodAngleFollowTape(false, 1));
        // addParallel(new RightPulleySetLength(5.5,.1,-1));
        // addParallel(new RightRodAngleFollowTape(false),1);
        //

    }
}
