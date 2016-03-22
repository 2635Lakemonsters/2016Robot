package org.usfirst.frc.team2635.routines;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.usfirst.frc.team2635.common.ElevatorCommon.*;
import static org.usfirst.frc.team2635.common.ControlCommon.*;
import edu.wpi.first.wpilibj.DigitalInput;
public class RoutineElevatorVbus implements IRoutine
{

	public RoutineElevatorVbus() 
	{
		rightElevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
		leftElevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
		
	}

	@Override
	public RoutineState run() 
	{
		SmartDashboard.putNumber("Right elevator encoder", rightElevatorMotor.getPosition());
		SmartDashboard.putNumber("Left elevator encoder", leftElevatorMotor.getPosition());
		boolean limitHit = !rightElevatorLimit.get() || !leftElevatorLimit.get();
		SmartDashboard.putBoolean("Elevator limit hit", limitHit);
		if(limitHit)
		{
			rightElevatorMotor.setPosition(0.0);
			leftElevatorMotor.setPosition(0.0);
		}
		
		if(rightJoystick.getRawButton(ELEVATE_UP_BUTTON))
		{
			rightElevatorMotor.set(-1.0);
			leftElevatorMotor.set(-1.0);
		}
		else if(rightJoystick.getRawButton(ELEVATE_DOWN_BUTTON))
		{
			rightElevatorMotor.set(0.7);
			leftElevatorMotor.set(0.7);
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
