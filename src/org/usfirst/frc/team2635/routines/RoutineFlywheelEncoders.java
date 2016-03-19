package org.usfirst.frc.team2635.routines;

import org.usfirst.frc.team2635.components.FlywheelCommon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class RoutineFlywheelEncoders extends FlywheelCommon implements IRoutine 
{
	public RoutineFlywheelEncoders()
	{
	//TODO set pid
		leftFlywheelMotor.changeControlMode(TalonControlMode.Speed);
		leftFlywheelMotor.setPID(SHOOTER_P_DEFAULT, SHOOTER_I_DEFAULT, SHOOTER_D_DEFAULT);
		
		rightFlywheelMotor.changeControlMode(TalonControlMode.Speed);
		rightFlywheelMotor.setPID(SHOOTER_P_DEFAULT, SHOOTER_I_DEFAULT, SHOOTER_D_DEFAULT);
		
		feedMotor.changeControlMode(TalonControlMode.PercentVbus);
		FIRE_SPEED = 50000.0;
		FEED_SPEED = -FIRE_SPEED / 2.5;

	}
	@Override
	public RoutineState run()
	{
		RoutineState routineState = RoutineState.NO_FAULT;

		FeedbackDeviceStatus leftFlywheelStatus = leftFlywheelMotor.isSensorPresent(FeedbackDevice.QuadEncoder);
		FeedbackDeviceStatus rightFlywheelStatus = rightFlywheelMotor.isSensorPresent(FeedbackDevice.QuadEncoder);
		//TODO Experimental
		if(leftFlywheelStatus == FeedbackDeviceStatus.FeedbackStatusNotPresent || leftFlywheelStatus == FeedbackDeviceStatus.FeedbackStatusUnknown 
				|| rightFlywheelStatus == FeedbackDeviceStatus.FeedbackStatusNotPresent || rightFlywheelStatus == FeedbackDeviceStatus.FeedbackStatusUnknown)
		{
			routineState = RoutineState.FAULT_ENCODER;
			routineState.errorMessage = "Flywheel feedback device not found.";
			return routineState;
		}
		
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
			flywheelRoutine.actuate(0.0);
			feedRoutine.actuate(0.0);
		}
		return routineState;
		
	}

	@Override
	public void cleanup()
	{
		flywheelRoutine.actuate(0.0);
		feedRoutine.actuate(0.0);		
	}
	
}
