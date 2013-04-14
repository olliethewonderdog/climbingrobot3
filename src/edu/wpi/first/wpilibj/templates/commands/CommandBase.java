package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.SI;
import edu.wpi.first.wpilibj.templates.FrameMath;
import edu.wpi.first.wpilibj.templates.subsystems.LeftPulley;
import edu.wpi.first.wpilibj.templates.subsystems.LeftRod;
import edu.wpi.first.wpilibj.templates.subsystems.RightPulley;
import edu.wpi.first.wpilibj.templates.subsystems.TopPulley;
import edu.wpi.first.wpilibj.templates.subsystems.RightRod;
import edu.wpi.first.wpilibj.templates.subsystems.TopRod;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use
 * CommandBase.exampleSubsystem
 *
 * @author Author
 */
public abstract class CommandBase extends Command {
    public static OI oi;
    public static SI si;
    public static FrameMath math;
    // Create a single static instance of all of your subsystems
    public static LeftPulley leftpulley = new LeftPulley();
    public static LeftRod leftrod = new LeftRod();
    public static RightPulley rightpulley = new RightPulley();
    public static RightRod rightrod = new RightRod();
    public static TopPulley toppulley = new TopPulley();
    public static TopRod toprod = new TopRod();
    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();
        si= new SI();
        math=new FrameMath();
        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(leftpulley);
        SmartDashboard.putData(leftrod);
        SmartDashboard.putData(rightrod);
        SmartDashboard.putData(rightpulley);
        SmartDashboard.putData(toprod);
        SmartDashboard.putData(toppulley);
        
    }
    public CommandBase(String name) {
        super(name);
    }
    public CommandBase() {
        super();
    }
}
