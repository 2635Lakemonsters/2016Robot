package org.usfirst.frc.team2635.routines;

import org.usfirst.frc.team2635.components.TiltCommon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;

public class RoutineTiltEncoder extends TiltCommon implements IRoutine
{
	Encoder tiltEncoder;
	PIDController tiltPID;

	@Override
	public RoutineState run()
	{
		return null;
	}

	@Override
	public void cleanup()
	{
		// TODO Auto-generated method stub
		
	}

}
