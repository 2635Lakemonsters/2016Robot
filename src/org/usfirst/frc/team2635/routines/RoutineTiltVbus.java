package org.usfirst.frc.team2635.routines;

import org.usfirst.frc.team2635.components.TiltCommon;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class RoutineTiltVbus extends TiltCommon implements IRoutine
{

	public RoutineTiltVbus()
	{
		super();
		tiltMotor.changeControlMode(TalonControlMode.PercentVbus);
	}
	static int TILT_UP_BUTTON = 6;
	static int TILT_DOWN_BUTTON = 7;
	static double TILT_SPEED = 1.0;
	@Override
	public RoutineState run()
	{
		if(rightJoystick.getRawButton(TILT_UP_BUTTON))
		{
			tiltMotor.set(TILT_SPEED);
		}
		else if(rightJoystick.getRawButton(TILT_DOWN_BUTTON))
		{
			tiltMotor.set(-TILT_SPEED);
		}
		else
		{
			tiltMotor.set(0.0);
		}
		return RoutineState.NO_FAULT;
	}

	@Override
	public void cleanup()
	{
		tiltMotor.set(0.0);
	}
	
}