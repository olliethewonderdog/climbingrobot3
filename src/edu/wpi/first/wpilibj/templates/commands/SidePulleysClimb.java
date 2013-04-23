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
public class SidePulleysClimb extends CommandGroup {
    
    public SidePulleysClimb(boolean floor,float leftLength,float rightLength,
          float leftSpeed,float rightSpeed, float leftError,
          float rightError) {
        addParallel(new LeftPulleySetLength(leftLength, leftError,leftSpeed));
        addParallel(new LeftRodFollowTape(floor, leftLength));
        addParallel(new RightPulleySetLength(rightLength,rightError,rightSpeed));
        addParallel(new RightRodFollowTape(floor,rightLength));
    }
}