package org.usfirst.frc.team2635.routines;

import org.usfirst.frc.team2635.components.DriveCommon;
import org.usfirst.frc.team2635.modules.DriveThreeMotorTankDrive;
import org.usfirst.frc.team2635.routines.IRoutine.RoutineState;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class RoutineDriveVbus extends DriveCommon implements IRoutine
{

	/**
	 * Changes all the motors to PercentVbus mode, initializes robotDrive to a DriveThreeMotorTankDrive.
	 */
	public RoutineDriveVbus()
	{
		super();
    	rearRightMotor.changeControlMode(TalonControlMode.PercentVbus);
    	midRightMotor.changeControlMode(TalonControlMode.PercentVbus);
    	frontRightMotor.changeControlMode(TalonControlMode.PercentVbus);   	
    	rearLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
    	midLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
    	frontLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
    	
		robotDrive = new DriveThreeMotorTankDrive(rearRightMotor, midRightMotor, frontRightMotor, rearLeftMotor, midLeftMotor, frontLeftMotor);
	}
	
	@Override
	public RoutineState run() 
	{	
    	double RY = rightJoystick.getRawAxis(RIGHT_Y_AXIS);
    	double LY = -leftJoystick.getRawAxis(LEFT_Y_AXIS);
    	robotDrive.drive(LY, RY);
    	return RoutineState.NO_FAULT;
	}

	@Override
	public void cleanup() 
	{
		//Prevent states that forget to zero drive from making the robot go out of control
		robotDrive.drive(0, 0);
	} 

}
