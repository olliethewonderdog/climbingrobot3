/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.templates.OI;
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

        // 1.Extend top pulley to 43 inches while maintaining rod angle of 45
        // degrees below the frame

        // addParallel(new TopPulleySetLength(44,.1,1);
        // addParallel(new TopSetRodAngleFree(.33,-45);

        // 2.Lower the top rod to 60 degrees to hook rung

        // addSequential(new TopSetRodAngleFree(.33,-60);

        // 3. Retract top tape to 30 inches 
        // Issue: since the top pulley does not have a pawl
        // it will need to hold its position while the sidepulleys disengage 
        // from the the lower rung, extend and grab the upper rung
        // We probably need a command that focuses on holding the center pulley
        // in a position possibly using the PID system and/or Wait() instructions

        // addParallel (new TopPulleySetLength(30,.1,-1));
        // addParallel (new TopRodAngleFollowTape(30))

        // 4. Unlock pawls on sides

        addSequential(new LeftSetPawl(false));
        // addSequential(new RightSetPawl(false));

        //
        // 5. Get  frameangle in degrees
        //
        dFrameAngle = gyro.getAngle();
        //
        // 6.Extend the side tapes to reach the next rung  
        // at an angle that will allow them to hook the rung
        // get frame angle
        //
        addParallel(new LeftPulleySetLength(35, .1, 1));
        addParallel(new LeftSetRodAngleFree(.33, 85 - dFrameAngle));
        // addParallel(new RightPulleySetLength(35,.1,1));
        // addParallel(new RightSetRodAngleFree(.33,80-dframeangle);
        //
        // 7. Lower the side rods to hook the rung
        // 
        addParallel(new LeftSetRodAngleFree(.33, 60 - dFrameAngle));
        // addParallel(new RightSetRodAngleFree(.33,60-frameangle));
        //
        // 9.  Lock side pawls and retract the side tapes to 20 inches, maintaining angle
        //
        addSequential(new LeftSetPawl(true));
        // addSequential (new RightSetPawl(true));
        addParallel(new LeftPulleySetLength(20, .1, -1));
        addParallel(new LeftRodAngleFollowTape(false, 20));
        // addParallel(new RightPulleySetLength(20,.1,-1));
        // addParallel(new RightAdjustRodAngleClimb(false,20));
        //
        // 10. LIft up center rod angle is guess
        dFrameAngle = gyro.getAngle();
        //
        // addSequential(new TopSetRodAngleFree(.33,85-dFrameAngle);
        //
        // 11. Retract sidetapes to 1 inch
        //
        addParallel(new LeftPulleySetLength(1, .1, -1));
        addParallel(new LeftRodAngleFollowTape(false, 1));
        // addParallel(new RightPulleySetLength(1,.1,-1));
        // addParallel(new RightRodAngleFollowTape(false),1);
        //

    }
}
