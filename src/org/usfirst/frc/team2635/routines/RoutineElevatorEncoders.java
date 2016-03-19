package org.usfirst.frc.team2635.routines;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class RoutineElevatorEncoders extends ElevatorCommon implements IRoutine
{

	public RoutineElevatorEncoders()
	{
		super();
		rightElevatorMotor.changeControlMode(TalonControlMode.Position);
		rightElevatorMotor.setPID(ELEVATOR_P_DEFAULT, ELEVATOR_I_DEFAULT, ELEVATOR_D_DEFAULT);
		
		leftElevatorMotor.changeControlMode(TalonControlMode.Position);
		leftElevatorMotor.setPID(ELEVATOR_P_DEFAULT, ELEVATOR_I_DEFAULT, ELEVATOR_D_DEFAULT);
		
	}

	@Override
	public RoutineState run()
	{
		FeedbackDeviceStatus rightStatus = rightElevatorMotor.isSensorPresent(FeedbackDevice.QuadEncoder);
		FeedbackDeviceStatus leftStatus = leftElevatorMotor.isSensorPresent(FeedbackDevice.QuadEncoder);
		
		boolean encodersNotConnected = rightStatus == FeedbackDeviceStatus.FeedbackStatusNotPresent || rightStatus == FeedbackDeviceStatus.FeedbackStatusUnknown 
				|| leftStatus == FeedbackDeviceStatus.FeedbackStatusNotPresent || leftStatus == FeedbackDeviceStatus.FeedbackStatusUnknown;
		if(encodersNotConnected)
		{
			return RoutineState.FAULT_ENCODER;
		}
		if(rightJoystick.getRawButton(AIM_BUTTON))
		{
			
		}
	}

	@Override
	public void cleanup()
	{
		// TODO Auto-generated method stub
		
	}


}
