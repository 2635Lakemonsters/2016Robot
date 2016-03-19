package org.usfirst.frc.team2635.routines;

public interface IRoutine 
{
	public enum RoutineState
	{
	
		/**
		 * Routine completed normally.
		 */
		NO_FAULT,
		/**
		 * Routine has entered a "done" state normally. Will no longer act.
		 */
		ROUTINE_FINISHED,
		/**
		 * Routine has entered a "done" state because of a fault. Will no longer act.
		 */
		ROUTINE_FINISHED_WITH_FAULT,
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
