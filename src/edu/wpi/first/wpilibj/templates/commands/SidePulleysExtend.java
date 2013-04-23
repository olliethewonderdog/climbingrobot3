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
public class ExtendSidePulleys extends CommandGroup {
    
    public ExtendSidePulleys (double leftlength,double rightlength,
            double leftangle,double rightangle,double leftspeed,
            double rightspeed,double lefterror,double righterror) {
        addParallel (new LeftPulleySetLength(leftlength,lefterror,leftspeed));
        addParallel (new LeftRodAngleFree(leftangle,leftlength));
        addParallel(new RightPulleySetLength(rightlength,righterror,rightspeed));
        addParallel(new RightRodAngleFree(rightangle,rightlength));
    }
}