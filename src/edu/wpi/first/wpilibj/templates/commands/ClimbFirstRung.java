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
public class ClimbFirstRung extends CommandGroup {

    Gyro gyro = new Gyro(RobotMap.GYRO_CHAN);
    private double dFrameAngle;

    public ClimbFirstRung() {
        //a Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.


        addSequential(new LeftSetPawl(true));
        addParallel(new LeftPulleySetLength(1, .1, -1));
        addParallel(new LeftRodAngleFollowTape(true, 1));
        //addSequential(new RightSetPawl(true));
        //addParallel(new RightPulleySetLength(1, .1, -1));
        //addParallel(new RightAngleFollowTape(true,1));
        dFrameAngle = gyro.getAngle();
        //addParallel(new TopSetRodAngleFree(85-dFrameAngle,20));

    }
}
