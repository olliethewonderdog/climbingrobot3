/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;

/**
 *
 * @author Brinton
 */
public class PrepareFirstRung extends CommandGroup {

    public PrepareFirstRung() {
        
        addParallel(new LeftRodAngleFree(75, 31));
        addParallel(new LeftPulleySetLength(31, .1,.5));
        addParallel(new RightRodAngleFree(75, 31));
        addParallel(new RightPulleySetLength(31, .1, .5));
        addParallel(new TopRodAngleFree(80, 26));
        addParallel(new TopPulleySetLength(26, .1, .5));
        addSequential(new WaitForChildren());
    }
}
