package org.usfirst.frc.team2635.routines;

import org.usfirst.frc.team2635.routines.IRoutine.RoutineState;

public class RoutineManager
{
	public IRoutine currentRoutine;
	public void changeState(IRoutine newState)
	{
		currentRoutine.cleanup();
		currentRoutine = newState;
	}
	/**
	 * 
	 * @param routine
	 * @param runCount Greater than zero for a count, null for infinite
	 */
	public RoutineManager(IRoutine routine)
	{
		super();
		this.currentRoutine = routine;
	
	}
	public RoutineState runRoutine()
	{
		return currentRoutine.run();		
	}

}
