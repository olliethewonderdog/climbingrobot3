/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.templates.SI;
import edu.wpi.first.wpilibj.command.WaitForChildren;

/**
 *
 * @author Brinton
 */
public class ClimbFirstRung extends CommandGroup {

  SI si;
  
private double dframeAngle;

    public ClimbFirstRung() {
       

        addSequential(new LeftSetPawl(true));
        addSequential(new RightSetPawl(true));
        addParallel(new LeftPulleySetLength(11, .2,-.8));
        addParallel(new LeftRodFollowTape(true, 11));
        addParallel(new RightPulleySetLength(11, .2, -.8));
        addParallel(new RightRodFollowTape(true,11));
        addParallel(new TopRodAngleFree(80,26));
        addSequential(new WaitForChildren());
        addParallel(new LeftPulleySetLength(6.5, .1,-.7));
        addParallel(new LeftRodFollowTape(true, 6.5));
        addParallel(new RightPulleySetLength(6.5, .1, -.7));
        addParallel(new RightRodFollowTape(true,6.5));
        addParallel(new TopRodAngleFree(80,26));
        addSequential(new WaitForChildren());
    }
}
