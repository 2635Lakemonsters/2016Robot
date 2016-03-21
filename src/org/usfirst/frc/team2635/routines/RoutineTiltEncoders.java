package org.usfirst.frc.team2635.routines;


import static org.usfirst.frc.team2635.components.TiltCommon.*;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;


public class RoutineTiltEncoders implements IRoutine
{
	//Need this to get elevation data so the tilter doesn't tilt too soon.
	CANTalon rightElevatorMotor = new CANTalon(RIGHT_ELEVATOR_CHANNEL);
	static double ELEVATION_MAX = 45000.0; 
	static double ELEVATION_ABOVE_CHASSIS = ELEVATION_MAX / 2; 

	public RoutineTiltEncoders() 
	{
		super();
		tiltPID.setPID(CAMERA_Y_P_DEFAULT, CAMERA_Y_I_DEFAULT, CAMERA_Y_D_DEFAULT);
		tiltPID.enable();
	}
	//TODO Because encoder isn't in talon, checking prescense of encoder is more difficult. Need to add a class that periodically checks for encoder delta.
	@Override
	public RoutineState run()
	{
		RoutineState state = RoutineState.NO_FAULT;
		boolean limitHit = !tiltLimit.get();
		//Transform the Z-Axis on the joystick from going [-1,1] to [0,1]
		if(limitHit)
		{
			tiltEncoder.reset();
			
		}
		double tiltPosition = ((-rightJoystick.getRawAxis(TILT_AXIS) + 1) / 2)  * TILT_MAX;
		if(rightElevatorMotor.getPosition() > ELEVATION_ABOVE_CHASSIS)
		{
			tiltPID.setSetpoint(tiltPosition);
		}
		else
		{
			tiltPID.setSetpoint(0.0);
		}
		return state;
	}

	@Override
	public void cleanup()
	{
		
	}

}
