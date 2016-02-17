package org.usfirst.frc.team2635.composites;

import com.lakemonsters2635.actuator.interfaces.BaseDrive;
import com.lakemonsters2635.sensor.interfaces.BaseSensor;

import edu.wpi.first.wpilibj.PIDController;

public class PIDDrive extends BaseDrive
{
	BaseDrive driveMethod;
	BaseSensor<Double> setPointGetter;
	BaseSensor<Boolean> enablePIDChecker;
	PIDController pid;
	public PIDDrive(BaseDrive driveMethod, BaseSensor<Double> setPointGetter, BaseSensor<Boolean> enablePIDChecker,
			PIDController pid) {
		super();
		this.driveMethod = driveMethod;
		this.setPointGetter = setPointGetter;
		this.enablePIDChecker = enablePIDChecker;
		this.pid = pid;
	}
	@Override
	public boolean drive(double X, double Y) 
	{
		if(enablePIDChecker.sense(null))
		{
			pid.setSetpoint(setPointGetter.sense(null));
			pid.enable();
		}
		else	
		{
			pid.disable();
			driveMethod.drive(X, Y);
		}
		return false;
	}
	@Override
	public boolean drive(double X, double Y, double rotation) 
	{
		if(enablePIDChecker.sense(null))
		{
			pid.setSetpoint(setPointGetter.sense(null));
			pid.enable();
		}
		else	
		{
			pid.disable();
			driveMethod.drive(X, Y, rotation);
		}
		return false;
	}
	
	
}
