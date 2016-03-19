package org.usfirst.frc.team2635.routines;

import org.usfirst.frc.team2635.routines.IRoutine.RoutineState;

public class RoutineManager
{
	IRoutine currentRoutine;
	Integer runCount;
	public void changeState(IRoutine newState, Integer runCount)
	{
		currentRoutine.cleanup();
		currentRoutine = newState;
	}
	/**
	 * 
	 * @param routine
	 * @param runCount Greater than zero for a count, null for infinite
	 */
	public RoutineManager(IRoutine routine, Integer runCount)
	{
		super();
		this.currentRoutine = routine;
		this.runCount = runCount;
	}
	public RoutineState runState()
	{
		if(runCount != null)
		{
			if(runCount < 0)
			{
				runCount--;
				return currentRoutine.run();

			}
			else
			{
				return RoutineState.ROUTINE_FINISHED;
			}
		}
		else
		{
			return currentRoutine.run();
		}
	}
	public void resetCount(Integer runCount)
	{
		this.runCount = runCount;
	}
}
