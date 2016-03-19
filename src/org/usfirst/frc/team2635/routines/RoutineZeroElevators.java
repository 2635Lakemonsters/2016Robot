package org.usfirst.frc.team2635.routines;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

import org.usfirst.frc.team2635.components.ElevatorCommon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

public class RoutineZeroElevators extends ElevatorCommon implements IRoutine
{

	private static final double REZERO_SPEED = -0.4;

	public RoutineZeroElevators(DigitalInput leftElevatorLimit, DigitalInput rightElevatorLimit)
	{
		super(leftElevatorLimit, leftElevatorLimit);
		rightElevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
		leftElevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
		watchdog = new Timer();
		watchdog.reset();
		watchdog.start();
	}
	Timer watchdog;
	
	@Override
	public RoutineState run()
	{
		rightElevatorMotor.set(REZERO_SPEED);
		leftElevatorMotor.set(REZERO_SPEED);
		
		boolean limitHit = !rightElevatorLimit.get() || !leftElevatorLimit.get();
		if(limitHit)
		{
			rightElevatorMotor.set(0.0);
			rightElevatorMotor.setPosition(0.0);
			
			leftElevatorMotor.set(0.0);
			leftElevatorMotor.setPosition(0.0);
			return RoutineState.ROUTINE_FINISHED;
		}
		else if(watchdog.hasPeriodPassed(4.0))
		{
		
			rightElevatorMotor.set(0.0);
			rightElevatorMotor.setPosition(0.0);
			
			leftElevatorMotor.set(0.0);
			leftElevatorMotor.setPosition(0.0);
			//Timeout means that the elevator was not able to hit the limit switch within the alloted time.
			return RoutineState.ROUTINE_FINISHED_WITH_FAULT;
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
