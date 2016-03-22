package org.usfirst.frc.team2635.common;

import edu.wpi.first.wpilibj.Joystick;

/**
 * A container class that contains all of the things that everything has, such as input devices (Joysticks)
 * @author Tristan
 *
 */
public class ControlCommon  
{
	public static void init()
	{
		rightJoystick = new Joystick(JOYSTICK_RIGHT_CHANNEL);
		leftJoystick = new Joystick(JOYSTICK_LEFT_CHANNEL);

	}
	public static int JOYSTICK_LEFT_CHANNEL = 1;
	public static int JOYSTICK_RIGHT_CHANNEL = 0;

	public static Joystick rightJoystick;
	public static Joystick leftJoystick;
	
	public static int RIGHT_Y_AXIS = 1;
	public static int LEFT_Y_AXIS = 1;
	public static int TILT_AXIS = 2;
	


}
