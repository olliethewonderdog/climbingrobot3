/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.SI;

/**
 *
 * @author Brinton
 */
public class PrepareFirstRung extends CommandGroup {
    
    public PrepareFirstRung() {
        SI.reset();
        Timer.delay(.5);
        double dFrameAngle=SI.getdFrameAngle();
        SmartDashboard.putNumber("PrepareFirstRung dFrameangle ",dFrameAngle);
       // addSequential(new LeftSetPawl(false));
        //addSequential(new RightSetPawl(false));
        //SmartDashboard.putString("PrepareFR LeftPawl", "is set" ); 
        //addParallel(new LeftRodAngleFree(50., 31));
       // addParallel(new LeftPulleySetLength(31., .1,.8));
        //addParallel(new RightRodAngleFree(75, 31));
        //addParallel(new RightPulleySetLength(31, .1, .8));
       addParallel(new TopRodAngleFree(60,20));
       addParallel(new TopPulleySetLength(20, .1, 1));
        addSequential(new WaitForChildren());
    }
}
