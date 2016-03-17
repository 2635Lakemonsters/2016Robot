package org.usfirst.frc.team2635.routines;

import org.usfirst.frc.team2635.components.FlywheelCommon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class RoutineFlywheelVBus extends FlywheelCommon implements IRoutine
{
	public RoutineFlywheelVBus()
	{
		super();
		rightFlywheelMotor.changeControlMode(TalonControlMode.PercentVbus);
		leftFlywheelMotor.changeControlMode(TalonControlMode.PercentVbus);
		feedMotor.changeControlMode(TalonControlMode.PercentVbus);
		FEED_SPEED = -1.0;
		FIRE_SPEED = 1.0;
	}
	@Override
	public RoutineState run()
	{
		
		if(rightJoystick.getRawButton(FIRE_BUTTON))
		{
			flywheelRoutine.actuate(FIRE_SPEED);
		}
		else if(rightJoystick.getRawButton(FEED_BUTTON))
		{
			feedRoutine.actuate(FEED_SPEED);
		}
		else
		{
			//Motors do not auto-zero, must be done manually.
			flywheelRoutine.actuate(0.0);
			feedRoutine.actuate(0.0);
		}
		return RoutineState.NO_FAULT;
	}

	@Override
	public void cleanup()
	{
		flywheelRoutine.actuate(0.0);
		feedRoutine.actuate(0.0);
	}

}
