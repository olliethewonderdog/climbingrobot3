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
public class ClimbWithSidePulleys extends CommandGroup {
    
    public ClimbWithSidePulleys(boolean floor,double leftLength,double rightLength,
            double leftSpeed,double rightSpeed, double leftError,
            double rightError) {
        addParallel(new LeftPulleySetLength(leftLength, leftError,leftSpeed));
        addParallel(new LeftRodFollowTape(floor, leftLength));
        addParallel(new RightPulleySetLength(rightLength,rightError,rightSpeed));
        addParallel(new RightRodFollowTape(floor,rightLength));
    }
}