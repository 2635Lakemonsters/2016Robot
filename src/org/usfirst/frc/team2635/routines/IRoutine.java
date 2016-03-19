package org.usfirst.frc.team2635.routines;

public interface IRoutine 
{
	public enum RoutineState
	{
	
		/**
		 * Routine completed normally
		 */
		NO_FAULT,
		/**
		 * Routine has been set to run a set number of times, has ran that many times, and now will no longer run.
		 */
		ROUTINE_FINISHED,
		/**
		 * A motor has entered a fault condition, usually a lack of movement.
		 */
		FAULT_MOTOR,
		/**
		 * An encoder has entered a fault condition, usually a lack of feedback.
		 */
		FAULT_ENCODER;
		
		/**
		 * Optional.
		 */
		public String errorMessage = null;
	}
	
	public RoutineState run();
	public void cleanup();
}
