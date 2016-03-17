package org.usfirst.frc.team2635.routines;

import org.usfirst.frc.team2635.routines.IRoutine.RoutineState;

public class RoutineManager
{
	IRoutine currentState;
	public void changeState(IRoutine newState)
	{
		currentState.cleanup();
		currentState = newState;
	}
	public RoutineState runState()
	{
		return currentState.run();
	}
}
