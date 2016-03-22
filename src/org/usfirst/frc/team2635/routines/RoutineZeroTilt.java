package org.usfirst.frc.team2635.routines;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

import static org.usfirst.frc.team2635.common.TiltCommon.*;

import edu.wpi.first.wpilibj.Timer;

public class RoutineZeroTilt implements IRoutine
{

	private static final double REZERO_SPEED = -0.4;
	public RoutineZeroTilt()
	{
		super();
		tiltPID.disable();
		tiltMotor.changeControlMode(TalonControlMode.PercentVbus);
		watchdog = new Timer();
		watchdog.reset();
		watchdog.start();
		
	}
	Timer watchdog;
	@Override
	public RoutineState run()
	{
		boolean limitHit = !tiltLimit.get();
		if(limitHit ||	watchdog.hasPeriodPassed(4.0))
		{
			tiltMotor.set(0.0);
			tiltEncoder.reset();
			tiltPID.enable();
			tiltPID.setSetpoint(-50);
			return RoutineState.ROUTINE_FINISHED;
		}
		tiltMotor.set(REZERO_SPEED);

		return RoutineState.NO_FAULT;
	}

	@Override
	public void cleanup()
	{
		tiltMotor.set(0.0);
	}

}
