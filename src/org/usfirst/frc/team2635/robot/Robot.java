
package org.usfirst.frc.team2635.robot;

import org.usfirst.frc.team2635.routines.RoutineDriveVbus;
import org.usfirst.frc.team2635.routines.RoutineElevatorEncoders;
import org.usfirst.frc.team2635.routines.RoutineElevatorVbus;
import org.usfirst.frc.team2635.routines.RoutineFlywheelEncoders;
import org.usfirst.frc.team2635.routines.RoutineFlywheelVBus;
import org.usfirst.frc.team2635.routines.RoutineManager;
import org.usfirst.frc.team2635.routines.RoutineTiltEncoders;
import org.usfirst.frc.team2635.routines.RoutineTiltVbus;
import org.usfirst.frc.team2635.routines.RoutineZeroElevators;
import org.usfirst.frc.team2635.routines.RoutineZeroTilt;
import org.usfirst.frc.team2635.routines.IRoutine.RoutineState;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.usfirst.frc.team2635.robot.Constants.Drive.*;
import static org.usfirst.frc.team2635.robot.Constants.Camera.*;
import static org.usfirst.frc.team2635.robot.Constants.Climber.*;
import static org.usfirst.frc.team2635.robot.Constants.Shooter.*;

import org.usfirst.frc.team2635.common.ClimberCommon;
import org.usfirst.frc.team2635.common.ControlCommon;
import org.usfirst.frc.team2635.common.DriveCommon;
import org.usfirst.frc.team2635.common.ElevatorCommon;
import org.usfirst.frc.team2635.common.FlywheelCommon;
import org.usfirst.frc.team2635.common.TiltCommon;

import static org.usfirst.frc.team2635.robot.Constants.Autonomous.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{
	//TODO: Make everything fit FunctionalityMode structure
	enum State
	{
		Encoder,
		Vbus,
		Zero
		
	}
	RoutineManager flywheelManager;
	RoutineManager elevatorManager;
	RoutineManager tiltManager;
	RoutineManager driveManager;
	Joystick rightJoystick;
	Joystick leftJoystick;
	boolean flagElevatorZero = false;
	boolean flagTiltZero = false;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */

	


	public void smartDashboardInit()
	{
	
			NetworkTable.flush();
			SmartDashboard.putBoolean(AUTO_KEY, true);
			SmartDashboard.putBoolean(REZERO_KEY, false);
			
			//PIDPIDPIDPIDPIDPIDPID
	    	SmartDashboard.putNumber(DRIVE_KEY_P, DRIVE_P_DEFAULT);
	    	SmartDashboard.putNumber(DRIVE_KEY_I, DRIVE_I_DEFAULT);
	    	SmartDashboard.putNumber(DRIVE_KEY_D, DRIVE_D_DEFAULT);
	    	
	    	SmartDashboard.putNumber(CAMERA_X_KEY_P, CAMERA_X_P_DEFAULT);
	    	SmartDashboard.putNumber(CAMERA_X_KEY_I, CAMERA_X_I_DEFAULT);
	    	SmartDashboard.putNumber(CAMERA_X_KEY_D, CAMERA_X_D_DEFAULT);
	    	
	    	SmartDashboard.putNumber(CAMERA_Y_KEY_P, CAMERA_Y_P_DEFAULT);
	    	SmartDashboard.putNumber(CAMERA_Y_KEY_I, CAMERA_Y_I_DEFAULT);
	    	SmartDashboard.putNumber(CAMERA_Y_KEY_D, CAMERA_Y_D_DEFAULT);
	    	
	    	SmartDashboard.putNumber(CLIMBER_KEY_P, CLIMBER_P_DEFAULT);
	    	SmartDashboard.putNumber(CLIMBER_KEY_I, CLIMBER_I_DEFAULT);
	    	SmartDashboard.putNumber(CLIMBER_KEY_D, CLIMBER_D_DEFAULT);
	    	
	    	SmartDashboard.putNumber(ELEVATOR_KEY_P, ELEVATOR_P_DEFAULT);
	    	SmartDashboard.putNumber(ELEVATOR_KEY_I, ELEVATOR_I_DEFAULT);
	    	SmartDashboard.putNumber(ELEVATOR_KEY_D, ELEVATOR_D_DEFAULT);
	    	
	    	SmartDashboard.putNumber(SHOOTER_KEY_P, SHOOTER_P_DEFAULT);
	    	SmartDashboard.putNumber(SHOOTER_KEY_I, SHOOTER_I_DEFAULT);
	    	SmartDashboard.putNumber(SHOOTER_KEY_D, SHOOTER_D_DEFAULT);
	    	
	    	SmartDashboard.putString(DRIVE_MODE_KEY, DRIVE_MODE_SPEED);

	}
    @Override
	public void robotInit() 
    {
    	
	    //if the robot throws a null pointer exception, its probably because something didn't get initialized in here! Check the error thrown (RioLog should have it if the driver station doesn't) to get more info
    	ClimberCommon.init();
    	ControlCommon.init();
    	DriveCommon.init();
    	ElevatorCommon.init();
    	FlywheelCommon.init();
    	TiltCommon.init();
    	smartDashboardInit();
	    rightJoystick = new Joystick(0);
	    leftJoystick = new Joystick(1);
	    flywheelManager = new RoutineManager(new RoutineFlywheelEncoders());
	    elevatorManager = new RoutineManager(new RoutineElevatorEncoders());
	    tiltManager = new RoutineManager(new RoutineTiltEncoders());
	    driveManager = new RoutineManager(new RoutineDriveVbus());
    }
    
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    @Override
	public void autonomousInit() 
    {
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
	public void autonomousPeriodic() 
    {
    	if(SmartDashboard.getBoolean(AUTO_KEY))
    	{
    		//Drive forward whatever distance it is to cross the defense,
    		//Drive backwards to position
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopInit()
    {
    	
	    
    }
    
    /**
     * 
     * @param tiltAngle Angle to tilt the shooter to. This should be calculated in cameraTeleop()
     * @param teleopMode
     */
    public void rezero()
    {
    	
    }
    @Override
	public void teleopPeriodic() 
    {
    	NetworkTable.flush();
    	RoutineState flywheelState = flywheelManager.runRoutine();
    	RoutineState elevatorState = elevatorManager.runRoutine();
    	RoutineState tiltState = tiltManager.runRoutine();
    	SmartDashboard.putString("Flywheel state", flywheelManager.currentRoutine.getClass().getSimpleName());
    	SmartDashboard.putString("Elevator state", elevatorManager.currentRoutine.getClass().getSimpleName());
    	SmartDashboard.putString("Tilt state", tiltManager.currentRoutine.getClass().getSimpleName());
    	SmartDashboard.putBoolean("Right limit", ElevatorCommon.rightElevatorLimit.get());
    	SmartDashboard.putBoolean("Left limit", ElevatorCommon.leftElevatorLimit.get());
    	SmartDashboard.putNumber("Right encoder", ElevatorCommon.rightElevatorMotor.getPosition());
    	SmartDashboard.putNumber("Left encoder", ElevatorCommon.leftElevatorMotor.getPosition());
    	SmartDashboard.putString("Elevator state", elevatorState.toString());
    	//RoutineState driveState = driveManager.runRoutine();
    	
    	
    	if(leftJoystick.getRawButton(REZERO_BUTTON))
    	{
    		tiltManager.changeState(new RoutineZeroTilt());
    		while(true)
    		{
    			RoutineState tiltZeroState = tiltManager.runRoutine();
    			if(tiltZeroState == RoutineState.ROUTINE_FINISHED || tiltZeroState == RoutineState.ROUTINE_FINISHED_WITH_FAULT)
    			{
    				break;
    			}
    		}
    		Timer timer = new Timer();
    		timer.reset();
    		timer.start();
    		while(!timer.hasPeriodPassed(2.0)){}
    		elevatorManager.changeState(new RoutineZeroElevators());
    		while(true)
    		{
    			RoutineState elevatorZeroState = elevatorManager.runRoutine();
    			if(elevatorZeroState == RoutineState.ROUTINE_FINISHED || elevatorZeroState == RoutineState.ROUTINE_FINISHED_WITH_FAULT)
    			{
    				break;
    			}
    		}
    		
    		tiltManager.changeState(new RoutineTiltEncoders());
    		elevatorManager.changeState(new RoutineElevatorEncoders());
    	}
    	if(leftJoystick.getRawButton(REZERO_INTERRUPT_BUTTON))
    	{
    		elevatorManager.changeState(new RoutineElevatorVbus());
    		tiltManager.changeState(new RoutineTiltVbus());
    	}
    	if(rightJoystick.getRawButton(VOLTAGE_MODE_BUTTON))
    	{
    		flywheelManager.changeState(new RoutineFlywheelVBus());
    		elevatorManager.changeState(new RoutineElevatorVbus());
    		tiltManager.changeState(new RoutineTiltVbus());
    	}
    	if(rightJoystick.getRawButton(SPEED_MODE_BUTTON))
    	{
    		flywheelManager.changeState(new RoutineFlywheelEncoders());
    		elevatorManager.changeState(new RoutineElevatorEncoders());
    		tiltManager.changeState(new RoutineTiltEncoders());
    	}
    	switch(flywheelState)
    	{
		case FAULT_ENCODER:
    		SmartDashboard.putBoolean(SHOOTER_FAULT_KEY, true);
    		flywheelManager.changeState(new RoutineFlywheelVBus());
			break;
		case FAULT_MOTOR:
			break;
		case NO_FAULT:
			break;
		case ROUTINE_FINISHED:
			break;
		case ROUTINE_FINISHED_WITH_FAULT:
			break;
		default:
			break;
    		
    	}
    	switch(elevatorState)
    	{
		case FAULT_ENCODER:
    		SmartDashboard.putBoolean(ELEVATOR_FAULT_KEY, true);
    		elevatorManager.changeState(new RoutineElevatorVbus());
			break;
		case FAULT_MOTOR:
			break;
		case NO_FAULT:
			break;
		case ROUTINE_FINISHED:
			elevatorManager.changeState(new RoutineElevatorEncoders());
			break;
		case ROUTINE_FINISHED_WITH_FAULT:
			elevatorManager.changeState(new RoutineElevatorVbus());
		default:
			break;
    	
    	}
    	
    	switch(tiltState)
    	{
		case FAULT_ENCODER:
			SmartDashboard.putBoolean(TILT_FAULT_KEY, true);
	    	tiltManager.changeState(new RoutineTiltVbus());
			break;
		case FAULT_MOTOR:
			break;
		case NO_FAULT:
			break;
		case ROUTINE_FINISHED:
			tiltManager.changeState(new RoutineTiltEncoders());
			break;
		case ROUTINE_FINISHED_WITH_FAULT:
			tiltManager.changeState(new RoutineTiltVbus());
			break;
		default:
			break;
    	
    	}
    		SmartDashboard.putBoolean(TILT_FAULT_KEY, true);
    	
    		
    }
    
    /**
     * This function is called periodically during test mode
     */
    @Override
	public void testPeriodic() 
    {
    	//Add motors and sensors using the LiveWindow class to test them. ex:
    	//LiveWindow.addActuator("Rear right motor", REAR_RIGHT_CHANNEL, rearRightMotor);
    	//I've never actually tried using the LiveWindow so some fiddling may be required
    }
    
}
