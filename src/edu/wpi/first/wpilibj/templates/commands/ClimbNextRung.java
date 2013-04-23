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
public class ClimbNextRung extends CommandGroup {
    public ClimbNextRung() {
        //1.Extend top pulley to 45 inches while maintaining rod angle of 80 
        // degreesto horizon. Then pause
        addSequential(new TopPulleyExtend(45f,80f,1f,.1f));
        addSequential(new TopPulleyExtend(45f,60f,.8f,.2f));
        addSequential(new TopPulleyClimb(42f,-.3f,.1f));
        addSequential(new TopPulleyClimb(26f,-.9f,.1f));
        addSequential(new  TopPulleyCreep(-.15f));
        addSequential(new LeftSetPawl(false));
        addSequential(new RightSetPawl(false));
        addSequential(new SidePulleysExtend(36f,36f,80f,80f,.9f,.9f,.5f,.5f));
         addSequential (new LeftSetPawl(true));
         addSequential (new RightSetPawl(true));
        addSequential(new SidePulleysClimb(false,6.5f,6.5f,.1f,.1f,-.8f,-.7f));
        // addParallel(new TopPulleySetLength(45f,.1f,1));
        // addParallel(new TopRodAngleFree(80f,45f));
         //addSequential (new WaitForChildren());
         //addSequential (new TopPause());
         // resume when apprppriate
        // 2.Lower the top rod to 60 degrees relative to horizon to hook rung 
         // and pause
         //addSequential(new TopRodAngleFree(60f,45f));
        // addSequential(new TopPause());
         //
        // 3. Retract top tape to 26 inches 
        //
        // addParallel (new TopPulleySetLength(26f,.1f,-1));
        // addParallel (new TopRodFollowTape(false, 26));
        // addSequential (new WaitForChildren());
         //  
        // 4. Unlock pawls on sides while creeping center tape.
        // Issue: since the top pulley does not have a pawl
        // it will need to hold its position while the sidepulleys disengage 
        // from the the lower rung, extend and grab the upper rung.
        // The solution is a "CreepTape" command that retracts the pulley
        //  at a fraction of its maximum voltage. This 
        // parameter fractional speed, 
        //  are a guess.  We will need to tune it.
        // during the CreepTape commands, we do not adjust the angle of the tape.
        // because the tapelength should not change enough to matter.
        //
        addParallel (new TopPulleyCreep(-.15f));
        //
        addSequential(new LeftSetPawl(false));
        addSequential(new RightSetPawl(false));
        //
        // 5.Extend the side tapes to reach the next rung  
        // at an angle that will allow them to hook the rung.
        //  Hold position with the center tape while they do this.
        //  
        addSequential(new SidePulleysExtend(36f,36f,80f,80f,.9f,.9f,.5f,.5f));
        //
        // 6. Now lower the side rods to hook the rung. 
        //
        addSequential(new SidePulleysExtend(36f,36f,60f,60f,.9f,.9f,.5f,.5f));
        // 7..Pause sides
        //
        addSequential (new SidePause());
        // resume when ready
        //
        // 8.  Lock side pawls and retract the side tapes to 20 inches,
        // maintaining angle. Creep for  2 seconds to hold
        // position till the pawls lock
        //
         addSequential (new LeftSetPawl(true));
         addSequential (new RightSetPawl(true));
         addSequential (new SidePulleysClimb(false,20f,20f,-.8f,-.7f,.5f,.5f));
        //
        // 9. Lift up center rod angle 
        //
         addParallel(new TopPulleySetLength(28f,.1f,.5f));
         addParallel(new TopRodAngleFree(85f,28f));
        //
        // 10. Retract sidetapes to 6.5 inch
        //
        //
        addSequential(new SidePulleysClimb(false,6.5f,6.5f,.1f,.1f,-.8f,-.7f));
    }
}
