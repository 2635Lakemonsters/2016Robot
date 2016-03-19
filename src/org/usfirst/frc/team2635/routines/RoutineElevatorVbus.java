package org.usfirst.frc.team2635.routines;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class RoutineElevatorVbus extends ElevatorCommon implements IRoutine
{

	public RoutineElevatorVbus() 
	{
		super();
		rightElevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
		leftElevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
		
	}

	@Override
	public RoutineState run() 
	{
		boolean limitHit = rightElevatorLimit.get() || leftElevatorLimit.get();
		
		if(limitHit)
		{
			rightElevatorMotor.setPosition(0.0);
			leftElevatorMotor.setPosition(0.0);
		}
		
		if(rightJoystick.getRawButton(ELEVATE_UP_BUTTON))
		{
			rightElevatorMotor.set(1.0);
			leftElevatorMotor.set(1.0);
		}
		else if(rightJoystick.getRawButton(ELEVATE_DOWN_BUTTON))
		{
			rightElevatorMotor.set(-0.7);
			leftElevatorMotor.set(-0.7);
		}
		else
		{
			rightElevatorMotor.set(0.0);
			leftElevatorMotor.set(0.0);
		}
		return RoutineState.NO_FAULT;
	}

	@Override
	public void cleanup()
	{
		rightElevatorMotor.set(0.0);
		leftElevatorMotor.set(0.0);
		
	}
	

}
