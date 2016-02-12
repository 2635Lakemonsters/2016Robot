package org.usfirst.frc.team2635.composites;

import com.lakemonsters2635.actuator.interfaces.BaseDrive;
import com.lakemonsters2635.sensor.interfaces.ISensor;

import edu.wpi.first.wpilibj.PIDController;

public class PIDDrive extends BaseDrive
{
	BaseDrive driveMethod;
	ISensor<Double> setPointGetter;
	ISensor<Boolean> enablePIDChecker;
	PIDController pid;
	public PIDDrive(BaseDrive driveMethod, ISensor<Double> setPointGetter, ISensor<Boolean> enablePIDChecker,
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
		if(enablePIDChecker.sense())
		{
			pid.setSetpoint(setPointGetter.sense());
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
		if(enablePIDChecker.sense())
		{
			pid.setSetpoint(setPointGetter.sense());
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
