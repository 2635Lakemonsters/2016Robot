package org.usfirst.frc.team2635.routines;

import org.usfirst.frc.team2635.components.ElevatorCommon;

import com.lakemonsters2635.sensor.modules.SensorOneShot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class RoutineElevatorEncoders implements IRoutine
{
	double elevation = 0;
	boolean elevationState = false;
	SensorOneShot aimOneShot = new SensorOneShot(false);
	SensorOneShot initOneShot = new SensorOneShot(false);
	ElevatorCommon common;
	public RoutineElevatorEncoders(ElevatorCommon common)
	{
		this.common = common;
		common.rightElevatorMotor.changeControlMode(TalonControlMode.Position);
		common.rightElevatorMotor.setPID(common.ELEVATOR_P_DEFAULT, common.ELEVATOR_I_DEFAULT, common.ELEVATOR_D_DEFAULT);
		
		common.leftElevatorMotor.changeControlMode(TalonControlMode.Position);
		common.leftElevatorMotor.setPID(common.ELEVATOR_P_DEFAULT, common.ELEVATOR_I_DEFAULT, common.ELEVATOR_D_DEFAULT);
		
	}

	@Override
	public RoutineState run()
	{
		
		FeedbackDeviceStatus rightStatus = common.rightElevatorMotor.isSensorPresent(FeedbackDevice.QuadEncoder);
		FeedbackDeviceStatus leftStatus = common.leftElevatorMotor.isSensorPresent(FeedbackDevice.QuadEncoder);
		
		boolean encodersNotConnected = rightStatus == FeedbackDeviceStatus.FeedbackStatusNotPresent || rightStatus == FeedbackDeviceStatus.FeedbackStatusUnknown 
				|| leftStatus == FeedbackDeviceStatus.FeedbackStatusNotPresent || leftStatus == FeedbackDeviceStatus.FeedbackStatusUnknown;
		if(encodersNotConnected)
		{
			return RoutineState.FAULT_ENCODER;
		}
		
		boolean rightLimitHit = !common.rightElevatorLimit.get();
		boolean leftLimitHit = !common.leftElevatorLimit.get();
		
		if(rightLimitHit || leftLimitHit)
		{
			common.rightElevatorMotor.setPosition(0.0);
			common.leftElevatorMotor.setPosition(0.0);
		}
		
		if((boolean)aimOneShot.sense(common.rightJoystick.getRawButton(common.AIM_BUTTON)))
		{
			//Toggle between up and down
			if(elevationState == false)
			{
				elevation = common.ELEVATION_MAX;
				elevationState = true;
			}
			else
			{
				elevation = 0.0;
				elevationState = false;
			}
		}
		if((boolean) initOneShot.sense(common.leftJoystick.getRawButton(7)))
		{
			elevation = 35000;
		}
		common.rightElevatorMotor.set(elevation);
		common.leftElevatorMotor.set(elevation);
		
		return RoutineState.NO_FAULT;	
		
	}

	@Override
	public void cleanup()
	{
		
	}


}
