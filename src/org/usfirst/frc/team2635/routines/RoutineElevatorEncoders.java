package org.usfirst.frc.team2635.routines;


import static org.usfirst.frc.team2635.common.ElevatorCommon.*;

import org.usfirst.frc.team2635.common.ControlCommon;

import static org.usfirst.frc.team2635.common.ControlCommon.*;
import com.lakemonsters2635.sensor.modules.SensorOneShot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
public class RoutineElevatorEncoders implements IRoutine
{
	double elevation = rightElevatorMotor.getPosition();
	boolean elevationState = false;
	SensorOneShot aimOneShot = new SensorOneShot(false);
	SensorOneShot initOneShot = new SensorOneShot(false);
	public RoutineElevatorEncoders()
	{
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
		
		boolean encodersNotConnected = rightStatus == FeedbackDeviceStatus.FeedbackStatusNotPresent
				|| leftStatus == FeedbackDeviceStatus.FeedbackStatusNotPresent ;
		if(encodersNotConnected)
		{
			return RoutineState.FAULT_ENCODER;
		}
		
		boolean rightLimitHit = !rightElevatorLimit.get();
		boolean leftLimitHit = !leftElevatorLimit.get();
		if(rightLimitHit || leftLimitHit)
		{
			rightElevatorMotor.setPosition(0.0);
			leftElevatorMotor.setPosition(0.0);
		}
		
		if((boolean)aimOneShot.sense(rightJoystick.getRawButton(AIM_BUTTON)))
		{
			//Toggle between up and down
			if(elevationState == false)
			{
				rightElevatorMotor.setPID(ELEVATOR_P_DEFAULT, ELEVATOR_I_DEFAULT, ELEVATOR_D_DEFAULT);
				rightElevatorMotor.clearIAccum();
				leftElevatorMotor.setPID(ELEVATOR_P_DEFAULT, ELEVATOR_I_DEFAULT, ELEVATOR_D_DEFAULT);
				leftElevatorMotor.clearIAccum();
				elevation = ELEVATION_MAX;
				elevationState = true;
			}
			else
			{
				
				rightElevatorMotor.setPID(0.01, 0.00005, 0.0);
				rightElevatorMotor.ClearIaccum();
				leftElevatorMotor.setPID(0.01, 0.00005, 0.0);
				leftElevatorMotor.ClearIaccum();
				elevation = 0.0;
				elevationState = false;
			}
		}
		if((boolean) initOneShot.sense(leftJoystick.getRawButton(7)))
		{
			elevation = 35000;
		}
		rightElevatorMotor.set(elevation);
		leftElevatorMotor.set(elevation);
		
		return RoutineState.NO_FAULT;	
		
	}

	@Override
	public void cleanup()
	{
		
	}


}
