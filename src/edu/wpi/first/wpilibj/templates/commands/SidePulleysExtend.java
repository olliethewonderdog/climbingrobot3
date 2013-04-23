/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author laptop
 */
public class SidePulleysExtend extends CommandGroup {
    
    public SidePulleysExtend (float leftlength,float rightlength,
            float leftangle,float rightangle,float leftspeed,
            float rightspeed,float lefterror,float righterror) {
        addParallel (new LeftPulleySetLength(leftlength,lefterror,leftspeed));
        addParallel (new LeftRodAngleFree(leftangle,leftlength));
        addParallel(new RightPulleySetLength(rightlength,righterror,rightspeed));
        addParallel(new RightRodAngleFree(rightangle,rightlength));
    }
}