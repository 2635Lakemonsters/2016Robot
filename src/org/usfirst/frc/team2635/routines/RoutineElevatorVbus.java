package org.usfirst.frc.team2635.routines;

import org.usfirst.frc.team2635.components.ElevatorCommon;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;

public class RoutineElevatorVbus implements IRoutine
{
	ElevatorCommon common;

	public RoutineElevatorVbus(ElevatorCommon common) 
	{
		this.common = common;
		common.rightElevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
		common.leftElevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
		
	}

	@Override
	public RoutineState run() 
	{
		boolean limitHit = !common.rightElevatorLimit.get() || !common.leftElevatorLimit.get();
		
		if(limitHit)
		{
			common.rightElevatorMotor.setPosition(0.0);
			common.leftElevatorMotor.setPosition(0.0);
		}
		
		if(common.rightJoystick.getRawButton(common.ELEVATE_UP_BUTTON))
		{
			common.rightElevatorMotor.set(1.0);
			common.leftElevatorMotor.set(1.0);
		}
		else if(common.rightJoystick.getRawButton(common.ELEVATE_DOWN_BUTTON))
		{
			common.rightElevatorMotor.set(-0.7);
			common.leftElevatorMotor.set(-0.7);
		}
		else
		{
			common.rightElevatorMotor.set(0.0);
			common.leftElevatorMotor.set(0.0);
		}
		return RoutineState.NO_FAULT;
	}

	@Override
	public void cleanup()
	{
		common.rightElevatorMotor.set(0.0);
		common.leftElevatorMotor.set(0.0);
		
	}
	

}
