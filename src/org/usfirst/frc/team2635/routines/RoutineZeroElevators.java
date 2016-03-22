package org.usfirst.frc.team2635.routines;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

import static org.usfirst.frc.team2635.common.ElevatorCommon.*;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

public class RoutineZeroElevators implements IRoutine
{

	private static final double REZERO_SPEED = 0.4;

	public RoutineZeroElevators()
	{
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

		boolean limitHit = (!rightElevatorLimit.get()) || (!leftElevatorLimit.get());
		
		if (limitHit)
		{
			System.out.println("limit hit");
			rightElevatorMotor.set(0.0);
			rightElevatorMotor.setPosition(0.0);

			leftElevatorMotor.set(0.0);
			leftElevatorMotor.setPosition(0.0);
			return RoutineState.ROUTINE_FINISHED;
		} else if (watchdog.hasPeriodPassed(2.0))
		{
			System.out.println("motor fail");
			rightElevatorMotor.set(0.0);
			rightElevatorMotor.setPosition(0.0);

			leftElevatorMotor.set(0.0);
			leftElevatorMotor.setPosition(0.0);
			// Timeout means that the elevator was not able to hit the limit
			// switch within the alloted time.
			return RoutineState.ROUTINE_FINISHED_WITH_FAULT;
		}
		rightElevatorMotor.set(REZERO_SPEED);
		leftElevatorMotor.set(REZERO_SPEED);

		return RoutineState.NO_FAULT;
	}

	@Override
	public void cleanup()
	{
		rightElevatorMotor.set(0.0);
		leftElevatorMotor.set(0.0);
	}

}
