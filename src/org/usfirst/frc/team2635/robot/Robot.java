
package org.usfirst.frc.team2635.robot;

import org.usfirst.frc.team2635.modules.ActuatorTwoMotorInverse;
import org.usfirst.frc.team2635.modules.DriveThreeMotor;
import org.usfirst.frc.team2635.modules.DriveThreeMotorTankDrive;
import org.usfirst.frc.team2635.modules.Flywheel;
import org.usfirst.frc.team2635.modules.PIDOutputThreeMotorRotate;
import org.usfirst.frc.team2635.modules.SensorNavxAngle;

import com.kauailabs.navx.frc.AHRS;
import com.lakemonsters2635.actuator.interfaces.BaseActuator;
import com.lakemonsters2635.actuator.interfaces.BaseDrive;
import com.lakemonsters2635.actuator.modules.ActuatorSimple;
import com.lakemonsters2635.actuator.modules.DriveArcade;
import com.lakemonsters2635.sensor.interfaces.BaseSensor;
import com.lakemonsters2635.sensor.modules.SensorDummy;
import com.lakemonsters2635.sensor.modules.SensorOneShot;
import com.lakemonsters2635.sensor.modules.SensorRawButton;
import com.lakemonsters2635.sensor.modules.SensorRawJoystickAxis;
import com.lakemonsters2635.sensor.modules.SensorTargetAngleFromImage;
import com.lakemonsters2635.sensor.modules.SensorUnwrapper;
import com.lakemonsters2635.util.ImageGrabber;
import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{
//CONSTANTS
	//DRIVE CONSTANTS
		final int REAR_RIGHT_CHANNEL = -1;
		final int MID_RIGHT_CHANNEL = -1;
		final int FRONT_RIGHT_CHANNEL = -1;
		
		final int REAR_LEFT_CHANNEL = -1;
		final int MID_LEFT_CHANNEL = -1;
		final int FRONT_LEFT_CHANNEL = -1;
		
		final int JOYSTICK_LEFT_CHANNEL = 1;
		final int JOYSTICK_RIGHT_CHANNEL = 0;
		
		final int RIGHT_Y_AXIS = 0;
		final int LEFT_Y_AXIS = 0;
		
		final String DRIVE_KEY_P = "Drive P";
		final String DRIVE_KEY_I = "Drive I";
		final String DRIVE_KEY_D = "Drive D";
		
		final double DRIVE_P_DEFAULT = 0.0;
		final double DRIVE_I_DEFAULT = 0.0;
		final double DRIVE_D_DEFAULT = 0.0;
		
		double speedModeScaler = 1000.0;
	//END DRIVE CONSTANTS
	
	//CAMERA CONSTANTS
		final String CAMERA_X_KEY_P = "Camera X P";
		final String CAMERA_X_KEY_I = "Camera X I";
		final String CAMERA_X_KEY_D = "Camera X D";
		
		final String CAMERA_Y_KEY_P = "Camera Y P";
		final String CAMERA_Y_KEY_I = "Camera Y I";
		final String CAMERA_Y_KEY_D = "Camera Y D";
		
		double CAMERA_X_P_DEFAULT = 0.02;
		double CAMERA_X_I_DEFAULT = 0.0006;
		double CAMERA_X_D_DEFAULT = 0.0;

		final double CAMERA_Y_P_DEFAULT = 0.0;
		final double CAMERA_Y_I_DEFAULT = 0.0;
		final double CAMERA_Y_D_DEFAULT = 0.0;
		
		final double CAMERA_RESOLUTION_X = 680.0;
		final double CAMERA_RESOLUTION_Y = 460.0;
		final double CAMERA_VIEW_ANGLE = 64.0;
		final double TARGET_ASPECT_RATIO = 14.0/20.0;
		final NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(0, 130);
		final NIVision.Range TARGET_SATURATION_RANGE = new NIVision.Range(0, 255);
		final NIVision.Range TARGET_VALUE_RANGE = new NIVision.Range(250, 255);
		final double PARTICLE_AREA_MINIMUM = 0.5;
	//END CAMERA CONSTANTS
	
	//SHOOTER CONSTANTS
		final String ELEVATOR_KEY_P = "Elevator P";
		final String ELEVATOR_KEY_I = "Elevator I";
		final String ELEVATOR_KEY_D = "Elevator D";
		
		final double ELEVATOR_P_DEFAULT = 0.0;
		final double ELEVATOR_I_DEFAULT = 0.0;
		final double ELEVATOR_D_DEFAULT = 0.0;

		
		final int RIGHT_FLYWHEEL_CHANNEL = -1;
		final int LEFT_FLYWHEEL_CHANNEL = -1;
		final int FEED_CHANNEL = -1;
		final int TILT_CHANNEL = -1;
		final int ELEVATOR_CHANNEL = -1;
		
		final double FIRE_SPEED = 1.0;
		final double FEED_SPEED = 1.0;
		
		final double TILT_SCALER = 1.0; //TODO: Find maximum tilt distance;
		final double ELEVATION_DISTANCE = 0.0; //TODO: Find maximum elevation distance
		//Right hand joystick
		final int TILT_AXIS = 2;
		final int AIM_BUTTON = 3;
		final int FIRE_BUTTON = 1;
		final int FEED_FORWARD_BUTTON = 2;
		
	
	//END SHOOTER CONSTANTS
	
	//CLIMBER CONSTANTS
		final int LEFT_CLIMBER_CHANNEL = -1;
		final int RIGHT_CLIMBER_CHANNEL = -1;
		
		final String CLIMBER_KEY_P = "Climber P";
		final String CLIMBER_KEY_I = "Climber I";
		final String CLIMBER_KEY_D = "Climber D";
		
		final double CLIMBER_P_DEFAULT = 0.0;
		final double CLIMBER_I_DEFAULT = 0.0;
		final double CLIMBER_D_DEFAULT = 0.0;

		//TODO: find maximum height of climber, make fractions of that height as setpoints
		int climberIndex = 0;
		final double[] CLIMBER_POSITIONS = {0};
		//Left hand joystick
		final int CLIMB_UP_BUTTON = 3;
		final int CLIMB_DOWN_BUTTON = 2;
	//END CLIMBER CONSTANTS
//END CONSTANTS

//VARIABLES
	//DRIVE VARIABLES
		CANTalon rearRightMotor;
		CANTalon midRightMotor;
		CANTalon frontRightMotor;
		
		CANTalon rearLeftMotor;
		CANTalon midLeftMotor;
		CANTalon frontLeftMotor;
		
		DriveThreeMotor robotDrive;
		
		Joystick rightJoystick; //Shooter controls
		Joystick leftJoystick; //Climber controls

	//END DRIVE VARIABLES

	//CLIMBER VARIABLES
		CANTalon rightClimberMotor;
		CANTalon leftClimberMotor;
		BaseActuator<Double> climber;
		SensorOneShot climbUpOneShot;
		SensorOneShot climbDownOneShot;
	//END CLIMBER VARIABLES
	
	//SHOOTER VARIABLES
		Flywheel flywheel;
		CANTalon rightFlywheelMotor;
		CANTalon leftFlywheelMotor;
		CANTalon feedMotor;
		CANTalon elevatorMotor;
		CANTalon tiltMotor;
		
	//END SHOOTER VARIABLES
	
	//CAMERA VARIABLES
		AHRS navx;
		BaseSensor<NIVision.PointDouble> angleToTargetGrabber;
		SensorUnwrapper angleUnwrapper;
		ImageGrabber camera;
		PIDController cameraXPID;
	//END CAMERA VARIABLES
		
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
    	//SMART DASHBOARD INIT
    		//PIDPIDPIDPIDPIDPIDPID
	    	SmartDashboard.putNumber(DRIVE_KEY_P, DRIVE_P_DEFAULT);
	    	SmartDashboard.putNumber(DRIVE_KEY_I, DRIVE_I_DEFAULT);
	    	SmartDashboard.putNumber(DRIVE_KEY_D, DRIVE_D_DEFAULT);
	    	
	    	SmartDashboard.putNumber(CAMERA_X_KEY_P, CAMERA_X_P_DEFAULT);
	    	SmartDashboard.putNumber(CAMERA_X_KEY_I, CAMERA_X_I_DEFAULT);
	    	SmartDashboard.putNumber(CAMERA_X_KEY_D, CAMERA_X_D_DEFAULT);
	    	
	    	SmartDashboard.putNumber(CAMERA_Y_KEY_P, CAMERA_Y_P_DEFAULT);
	    	SmartDashboard.putNumber(CAMERA_Y_KEY_I, CAMERA_Y_I_DEFAULT);
	    	SmartDashboard.putNumber(CAMERA_Y_KEY_D, CAMERA_Y_D_DEFAULT);
	    	
	    	SmartDashboard.putNumber(CLIMBER_KEY_P, CLIMBER_P_DEFAULT);
	    	SmartDashboard.putNumber(CLIMBER_KEY_I, CLIMBER_I_DEFAULT);
	    	SmartDashboard.putNumber(CLIMBER_KEY_D, CLIMBER_D_DEFAULT);
	    	
	    	SmartDashboard.putNumber(ELEVATOR_KEY_P, ELEVATOR_P_DEFAULT);
	    	SmartDashboard.putNumber(ELEVATOR_KEY_I, ELEVATOR_I_DEFAULT);
	    	SmartDashboard.putNumber(ELEVATOR_KEY_D, ELEVATOR_D_DEFAULT);

	    //END SMART DASHBOARD INIT
	    	
	    //DRIVE INIT
	    	rearRightMotor = new CANTalon(REAR_RIGHT_CHANNEL);
	    	//rearRightMotor.changeControlMode(TalonControlMode.Follower);
	    	//rearRightMotor.set(FRONT_RIGHT_CHANNEL);
	    	
	    	midRightMotor = new CANTalon(MID_RIGHT_CHANNEL);
	    	//midRightMotor.changeControlMode(TalonControlMode.Follower);
	    	//midRightMotor.set(FRONT_RIGHT_CHANNEL);
	    	
	    	frontRightMotor = new CANTalon(FRONT_RIGHT_CHANNEL);
	    	//frontRightMotor.changeControlMode(TalonControlMode.Speed);
	    	//frontRightMotor.setPID(DRIVE_P_DEFAULT, DRIVE_I_DEFAULT, DRIVE_D_DEFAULT);
	    	
	    	rearLeftMotor = new CANTalon(REAR_LEFT_CHANNEL);
	    	//rearLeftMotor.changeControlMode(TalonControlMode.Follower);
	    	//rearLeftMotor.set(FRONT_LEFT_CHANNEL);
	    	
	    	midLeftMotor = new CANTalon(MID_LEFT_CHANNEL);
	    	//midLeftMotor.changeControlMode(TalonControlMode.Follower);
	    	//midLeftMotor.set(FRONT_LEFT_CHANNEL);
	    	
			frontLeftMotor = new CANTalon(FRONT_LEFT_CHANNEL);
	    	//frontLeftMotor.changeControlMode(TalonControlMode.Speed);
	    	//frontLeftMotor.setPID(DRIVE_P_DEFAULT, DRIVE_I_DEFAULT, DRIVE_D_DEFAULT);
			robotDrive = new DriveThreeMotorTankDrive(rearRightMotor, midRightMotor, frontRightMotor, rearLeftMotor, midLeftMotor, frontLeftMotor);
	      	
	    	rightJoystick = new Joystick(JOYSTICK_RIGHT_CHANNEL);
	    	leftJoystick = new Joystick(JOYSTICK_LEFT_CHANNEL);
	    //END DRIVE INIT
	    	
    	//SHOOTER INIT
	    	rightFlywheelMotor = new CANTalon(RIGHT_FLYWHEEL_CHANNEL);
	    	//rightFlywheelMotor.changeControlMode(TalonControlMode.Speed);
	    	
	    	leftFlywheelMotor = new CANTalon(LEFT_FLYWHEEL_CHANNEL);
	    	//leftFlywheelMotor.changeControlMode(TalonControlMode.Speed);
	    	feedMotor = new CANTalon(FEED_CHANNEL);
	    	
	    	elevatorMotor = new CANTalon(ELEVATOR_CHANNEL);
	    	elevatorMotor.changeControlMode(TalonControlMode.Position);
	    	elevatorMotor.setPID(ELEVATOR_P_DEFAULT, ELEVATOR_I_DEFAULT, ELEVATOR_D_DEFAULT);
	    	
	    	tiltMotor = new CANTalon(TILT_CHANNEL);
	    	tiltMotor.changeControlMode(TalonControlMode.Position);
	    	tiltMotor.setPID(CAMERA_Y_P_DEFAULT, CAMERA_Y_I_DEFAULT, CAMERA_Y_D_DEFAULT);
	    	
	    	flywheel = new Flywheel(
	    			new ActuatorTwoMotorInverse(leftFlywheelMotor, rightFlywheelMotor), 
	    			new ActuatorSimple(feedMotor), 
	    			new ActuatorTwoMotorInverse(leftFlywheelMotor, rightFlywheelMotor), 
	    			new ActuatorSimple(feedMotor),
	    			new SensorRawButton(FEED_FORWARD_BUTTON, rightJoystick), //TODO: Figure out if there are encoders to read or not
	    			new ActuatorSimple(elevatorMotor),
	    			new ActuatorSimple(tiltMotor));
	    //END SHOOTER INIT
	    	
	    
	    //CLIMBER INIT
	    	rightClimberMotor = new CANTalon(RIGHT_CLIMBER_CHANNEL);
	    	rightClimberMotor.changeControlMode(TalonControlMode.Position);
	    	rightClimberMotor.setPID(CLIMBER_P_DEFAULT, CLIMBER_I_DEFAULT, CLIMBER_D_DEFAULT);
	    	
	    	leftClimberMotor = new CANTalon(LEFT_CLIMBER_CHANNEL);
	    	leftClimberMotor.changeControlMode(TalonControlMode.Position);
	    	leftClimberMotor.setPID(CLIMBER_P_DEFAULT, CLIMBER_I_DEFAULT, CLIMBER_D_DEFAULT);
	    	
	    	climbUpOneShot = new SensorOneShot(false);
	    	climbDownOneShot = new SensorOneShot(false);
	    	
	    	climber = new ActuatorTwoMotorInverse(leftClimberMotor, rightClimberMotor);
	    //END CLIMBER INIT
	    
	    //CAMERA INIT
	    	navx = new AHRS(SerialPort.Port.kMXP);
	      	angleUnwrapper = new SensorUnwrapper(180.0, new SensorNavxAngle(navx));
	        int session = NIVision.IMAQdxOpenCamera("cam0",
	                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	      	camera = new ImageGrabber(session, true);
	      	angleToTargetGrabber = new SensorTargetAngleFromImage(CAMERA_RESOLUTION_X, CAMERA_RESOLUTION_Y, CAMERA_VIEW_ANGLE, TARGET_ASPECT_RATIO, TARGET_HUE_RANGE, TARGET_SATURATION_RANGE, TARGET_VALUE_RANGE, PARTICLE_AREA_MINIMUM);
	      	cameraXPID = new PIDController(CAMERA_X_P_DEFAULT, CAMERA_X_I_DEFAULT, CAMERA_X_D_DEFAULT, angleUnwrapper, new PIDOutputThreeMotorRotate(robotDrive));
      	//END CAMERA INIT
    }
    
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() 
    {
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() 
    {
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopInit()
    {
    	//frontRightMotor.setPID(SmartDashboard.getNumber(DRIVE_KEY_P), SmartDashboard.getNumber(DRIVE_KEY_I), SmartDashboard.getNumber(DRIVE_KEY_D));
    	//frontLeftMotor.setPID(SmartDashboard.getNumber(DRIVE_KEY_P), SmartDashboard.getNumber(DRIVE_KEY_I), SmartDashboard.getNumber(DRIVE_KEY_D));
    }
    public void teleopPeriodic() 
    {
    	//DEBUG
    		boolean climbUp = (Boolean) climbUpOneShot.sense(leftJoystick.getRawButton(CLIMB_UP_BUTTON));
    		boolean climbDown = (Boolean) climbDownOneShot.sense(leftJoystick.getRawButton(CLIMB_DOWN_BUTTON));
    		SmartDashboard.putBoolean("climbUp", climbUp);
    		SmartDashboard.putBoolean("climbDown", climbDown);
    		SmartDashboard.putNumber("Tilt Encoder", tiltMotor.getPosition());
    		SmartDashboard.putNumber("Elevator Encoder", elevatorMotor.getPosition());
    		SmartDashboard.putNumber("Climber left encoder", leftClimberMotor.getPosition());
    		SmartDashboard.putNumber("Unwrapped navx angle", angleUnwrapper.sense(null));
    		SmartDashboard.putNumber("Tilt axis", rightJoystick.getRawAxis(TILT_AXIS));
    	//END DEBUG
        //TELEOP SHOOTER AND CAMERA AND DRIVE
        	boolean findTargetAngle = rightJoystick.getRawButton(AIM_BUTTON);
        	boolean fire = rightJoystick.getRawButton(FIRE_BUTTON);
        	//TODO: change this when you figure out y axis camera shenannigans
        	double tiltAngle = rightJoystick.getRawAxis(TILT_AXIS) * TILT_SCALER;
        	if(findTargetAngle)
        	{
        		if(!cameraXPID.isEnabled())
        		{
        			
        			NIVision.PointDouble angleToTarget = angleToTargetGrabber.sense(camera.getImage());
        			SmartDashboard.putNumber("Angle to target X", angleToTarget.x);
        			SmartDashboard.putNumber("Angle to target Y", angleToTarget.y);
        			tiltMotor.setPID(SmartDashboard.getNumber(CAMERA_Y_KEY_P), SmartDashboard.getNumber(CAMERA_Y_KEY_I), SmartDashboard.getNumber(CAMERA_Y_KEY_D));
        			cameraXPID.setPID(SmartDashboard.getNumber(CAMERA_X_KEY_P), SmartDashboard.getNumber(CAMERA_X_KEY_I), SmartDashboard.getNumber(CAMERA_X_KEY_D));
        			cameraXPID.setSetpoint(angleToTarget.x);
        			//PID will drive for us
        			cameraXPID.enable();
        			//TODO: need to set tilt to some distance that is probably the ratio between the angle to the target and the veiw angle, multiplied by the maximum tilt distance
        		}	
        		
	        	
        	}
        	else
        	{
        		if(cameraXPID.isEnabled())
        		{
        			//Prevent PID from screwing with normal driving
        			cameraXPID.disable();
        		}
        		tiltAngle = rightJoystick.getRawAxis(TILT_AXIS) * TILT_SCALER; 
        		//Drive normally 
            	double RY = -rightJoystick.getRawAxis(RIGHT_Y_AXIS);
            	double LY = -leftJoystick.getRawAxis(LEFT_Y_AXIS);

            	robotDrive.drive(LY, RY);

        	}
        	if(fire)
        	{
        		//TODO: integrate vision processing, figure out elevateMagnitude
        		flywheel.fire(FIRE_SPEED, ELEVATION_DISTANCE, tiltAngle, FEED_SPEED);
        	}
        //END TELEOP SHOOTER AND CAMERA AND DRIVE
        //TELEOP CLIMBER
        	
        	if(climbUp && climberIndex < CLIMBER_POSITIONS.length)
        	{
        		climberIndex++;
        	}
        	else if(climbDown && climberIndex > 0)
        	{
        		climberIndex--;
        	}
        	climber.actuate(CLIMBER_POSITIONS[climberIndex]);
        //END TELEOP CLIMBER

    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() 
    {
    
    }
    
}
