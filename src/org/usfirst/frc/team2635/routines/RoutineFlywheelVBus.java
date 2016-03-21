package org.usfirst.frc.team2635.routines;

import static org.usfirst.frc.team2635.components.FlywheelCommon.*;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class RoutineFlywheelVBus implements IRoutine
{
	public RoutineFlywheelVBus()
	{
		super();
		rightFlywheelMotor.changeControlMode(TalonControlMode.PercentVbus);
		leftFlywheelMotor.changeControlMode(TalonControlMode.PercentVbus);
		feedMotor.changeControlMode(TalonControlMode.PercentVbus);
		FEED_SPEED = -0.5;
		FIRE_SPEED = 1.0;
	}
	@Override
	public RoutineState run()
	{
		
		if(rightJoystick.getRawButton(FIRE_BUTTON))
		{
			spinFlywheels(FIRE_SPEED);
			//TODO add a wait time
			feedMotor.set(1.0);
		}
		else if(rightJoystick.getRawButton(FEED_BUTTON))
		{
			spinFlywheels(FEED_SPEED);
			feedMotor.set(-1.0);
		}
		else
		{
			//Motors do not auto-zero, must be done manually.
			spinFlywheels(0.0);
			feedMotor.set(0.0);
		}
		return RoutineState.NO_FAULT;
	}

	@Override
	public void cleanup()
	{
		spinFlywheels(0.0);
		feedMotor.set(0.0);
	}

}
