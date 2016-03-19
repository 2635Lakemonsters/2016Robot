package org.usfirst.frc.team2635.routines;

import org.usfirst.frc.team2635.components.TiltCommon;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Timer;

public class RoutineZeroTilt extends TiltCommon implements IRoutine
{

	private static final double REZERO_SPEED = 0.4;
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
		tiltMotor.set(-REZERO_SPEED);
		if(limitHit ||	watchdog.hasPeriodPassed(4.0))
		{
			tiltMotor.set(0.0);
			tiltEncoder.reset();
			
			return RoutineState.ROUTINE_FINISHED;
		}
		return RoutineState.NO_FAULT;
	}

	@Override
	public void cleanup()
	{
		tiltMotor.set(0.0);
	}

}
