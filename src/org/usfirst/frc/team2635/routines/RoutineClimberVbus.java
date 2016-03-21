package org.usfirst.frc.team2635.routines;

import org.usfirst.frc.team2635.components.ClimberCommon;

public class RoutineClimberVbus extends ClimberCommon implements IRoutine
{

	@Override
	public RoutineState run()
	{
		if(leftJoystick.getRawButton(UP_BUTTON))
		{
			climberMotor.set(1.0);
		}
		else if(leftJoystick.getRawButton(DOWN_BUTTON))
		{
			climberMotor.set(-1.0);
		}
		else
		{
			climberMotor.set(0.0);
		}
		return RoutineState.NO_FAULT;
	}

	@Override
	public void cleanup()
	{
		climberMotor.set(0.0);
	}
	
}
