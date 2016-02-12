package org.usfirst.team2635.util;

import com.lakemonsters2635.sensor.interfaces.ISensor;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AngleUnwrapper implements ISensor<Double>
{
	Double accumulator;
	Double previousAngle;
	Double jumpTolerance;
	ISensor<Double> angleGetter;
	
	public AngleUnwrapper(Double jumpTolerance, ISensor<Double> angleGetter) {
		super();
		accumulator = 0.0;
		previousAngle = null;
		this.jumpTolerance = jumpTolerance;
		this.angleGetter = angleGetter;
	}

	@Override
	public Double sense() {
		if(previousAngle == null)
		{
			//Initialization
			previousAngle = angleGetter.sense();
			return accumulator;
		}
		Double currentAngle = angleGetter.sense();
		Double delta = previousAngle - currentAngle;
		SmartDashboard.putNumber("Delta", delta);
		if(delta > jumpTolerance)
		{
			accumulator -= delta - jumpTolerance*2;
		}
		else if (delta < -jumpTolerance)
		{
			accumulator -= delta + jumpTolerance*2;
		}
		else
		{
			accumulator -= delta;
		}
		previousAngle = currentAngle;

		return accumulator;
	}
	
}
