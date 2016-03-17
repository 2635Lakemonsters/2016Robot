package org.usfirst.frc.team2635.routines;

public interface IRoutine 
{
	public enum RoutineState
	{
		NO_FAULT,
		FAULT;
		
		public String errorMessage = null;
	}
	public RoutineState run();
	public void cleanup();
}
