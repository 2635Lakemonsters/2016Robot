package org.usfirst.team2635.util;

import com.lakemonsters2635.sensor.interfaces.ISensor;

public class AngleUnwrapper implements ISensor<Double>
{
	double accumulator;
	double previousAngle;
	double jumpTolerance;
	ISensor<Double> angleGetter;
	
	public AngleUnwrapper(double jumpTolerance, ISensor<Double> angleGetter) {
		super();
		accumulator = 0.0;
		previousAngle = 0.0;
		this.jumpTolerance = jumpTolerance;
		this.angleGetter = angleGetter;
	}

	@Override
	public Double sense() {
		double currentAngle = angleGetter.sense();
		double delta = previousAngle - currentAngle;
		if(Math.abs(delta) > jumpTolerance)
		{
			accumulator += delta + jumpTolerance;
		}
		else
		{
			accumulator += delta;
		}
		return accumulator;
	}
	
}
