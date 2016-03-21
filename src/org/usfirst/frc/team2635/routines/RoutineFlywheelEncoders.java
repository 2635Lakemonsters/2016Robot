package org.usfirst.frc.team2635.routines;

import static org.usfirst.frc.team2635.components.FlywheelCommon.*;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RoutineFlywheelEncoders implements IRoutine 
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
		SmartDashboard.putString("Left flywheel encoder status", leftFlywheelStatus.toString());
		SmartDashboard.putString("Right flywheel encoder status", rightFlywheelStatus.toString());
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
			spinFlywheels(FIRE_SPEED);
			if(Math.abs(rightFlywheelMotor.getError()) < 3000.0)
			{
				feedMotor.set(1.0);
			}
			
		}
		else if(rightJoystick.getRawButton(FEED_BUTTON))
		{
			spinFlywheels(FEED_SPEED);
			feedMotor.set(-1.0);
		}
		else
		{
			spinFlywheels(0.0);
			feedMotor.set(0.0);
			
		}
		return routineState;
		
	}
	@Override
	public void cleanup()
	{
		spinFlywheels(0.0);
		feedMotor.set(0.0);

	}
	
}
