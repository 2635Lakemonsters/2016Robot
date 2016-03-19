package org.usfirst.frc.team2635.routines;

import org.usfirst.frc.team2635.components.DriveCommon;
import org.usfirst.frc.team2635.modules.DriveThreeMotorTankDrive;
import org.usfirst.frc.team2635.routines.IRoutine.RoutineState;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class RoutineDriveVbus implements IRoutine
{

	/**
	 * Changes all the motors to PercentVbus mode, initializes robotDrive to a DriveThreeMotorTankDrive.
	 */
	DriveCommon common; 
	public RoutineDriveVbus(DriveCommon common)
	{
		this.common = common;
    	common.rearRightMotor.changeControlMode(TalonControlMode.PercentVbus);
    	common.midRightMotor.changeControlMode(TalonControlMode.PercentVbus);
    	common.frontRightMotor.changeControlMode(TalonControlMode.PercentVbus);   	
    	common.rearLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
    	common.midLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
    	common.frontLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
    	
    	common.robotDrive = new DriveThreeMotorTankDrive(common.rearRightMotor, common.midRightMotor, common.frontRightMotor, common.rearLeftMotor, common.midLeftMotor, common.frontLeftMotor);
	}
	
	@Override
	public RoutineState run() 
	{	
    	double RY = common.rightJoystick.getRawAxis(common.RIGHT_Y_AXIS);
    	double LY = -common.leftJoystick.getRawAxis(common.LEFT_Y_AXIS);
    	common.robotDrive.drive(LY, RY);
    	return RoutineState.NO_FAULT;
	}

	@Override
	public void cleanup() 
	{
		//Prevent states that forget to zero drive from making the robot go out of control
		common.robotDrive.drive(0, 0);
	} 

}
