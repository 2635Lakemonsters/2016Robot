
package org.usfirst.frc.team2635.robot;

import org.usfirst.frc.team2635.modules.ActuatorLauncherFeed;
import org.usfirst.frc.team2635.modules.ActuatorLauncherFeedInvertRight;
import org.usfirst.frc.team2635.modules.ActuatorTwoMotor;
import org.usfirst.frc.team2635.modules.DriveThreeMotor;
import org.usfirst.frc.team2635.modules.DriveThreeMotorTankDrive;
import org.usfirst.frc.team2635.modules.Flywheel;
import org.usfirst.frc.team2635.modules.PIDOutputThreeMotorRotate;
import org.usfirst.frc.team2635.modules.SensorCANTalonPIDError;
import org.usfirst.frc.team2635.modules.SensorThreeAND;
import org.usfirst.frc.team2635.modules.SensorNavxAngle;

import com.kauailabs.navx.frc.AHRS;
import com.lakemonsters2635.actuator.interfaces.BaseActuator;
import com.lakemonsters2635.actuator.modules.ActuatorClosedLoop;
import com.lakemonsters2635.actuator.modules.ActuatorSimple;
import com.lakemonsters2635.sensor.interfaces.BaseSensor;
import com.lakemonsters2635.sensor.modules.SensorHitTest;
import com.lakemonsters2635.sensor.modules.SensorOneShot;
import com.lakemonsters2635.sensor.modules.SensorRawButton;
import com.lakemonsters2635.sensor.modules.SensorTargetAngleFromImage;
import com.lakemonsters2635.sensor.modules.SensorUnwrapper;
import com.lakemonsters2635.util.ImageGrabber;
import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

import static org.usfirst.frc.team2635.robot.Constants.Drive.*;
import static org.usfirst.frc.team2635.robot.Constants.Camera.*;
import static org.usfirst.frc.team2635.robot.Constants.Climber.*;
import static org.usfirst.frc.team2635.robot.Constants.Shooter.*;

import java.net.NetworkInterface;

import static org.usfirst.frc.team2635.robot.Constants.Autonomous.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{
	//TODO: Make everything fit FunctionalityMode structure
	enum FunctionalityMode
	{
		Competition, //More position
		Debug_Vbus, //More vbus
		Debug_Encoder
	}
	FunctionalityMode runMode = FunctionalityMode.Competition;
	
//See Constants.java for constants
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
		CANTalon climberMotor;
		BaseActuator<Double> climber;
		SensorOneShot climbUpOneShot;
		SensorOneShot climbDownOneShot;
	//END CLIMBER VARIABLES
	
	//SHOOTER VARIABLES
		Flywheel flywheel;
		
		DigitalInput leftElevatorLimit;
		DigitalInput rightElevatorLimit;
		
		CANTalon rightFlywheelMotor;
		CANTalon leftFlywheelMotor;
		
		CANTalon feedMotor;
		
		CANTalon rightElevatorMotor;
		CANTalon leftElevatorMotor;
		
		SensorOneShot elevateOneShot;
		
		Encoder tiltEncoder;
		PIDController tiltPID;
		CANTalon tiltMotor;
		
	//END SHOOTER VARIABLES
	//CAMERA VARIABLES
		AHRS navx;
		BaseSensor<NIVision.PointDouble> angleToTargetGrabber;
		SensorUnwrapper angleUnwrapper;
		ImageGrabber camera;
		PIDController cameraXPID;
		USBCamera cam0;
		boolean cameraExists = false;
	//END CAMERA VARIABLES
		
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	public void driveConfigEncoder()
	{
		//TODO: Middle motor is driver
    	rearRightMotor.changeControlMode(TalonControlMode.Follower);
    	rearRightMotor.set(FRONT_RIGHT_CHANNEL);
    	
    	midRightMotor.changeControlMode(TalonControlMode.Follower);
    	midRightMotor.set(FRONT_RIGHT_CHANNEL);
    	
    	frontRightMotor.changeControlMode(TalonControlMode.Speed);
    	frontRightMotor.setPID(DRIVE_P_DEFAULT, DRIVE_I_DEFAULT, DRIVE_D_DEFAULT);
    	
    	rearLeftMotor.changeControlMode(TalonControlMode.Follower);
    	rearLeftMotor.set(FRONT_LEFT_CHANNEL);
    	
    	midLeftMotor.changeControlMode(TalonControlMode.Follower);
    	midLeftMotor.set(FRONT_LEFT_CHANNEL);
    	
    	frontLeftMotor.changeControlMode(TalonControlMode.Speed);
    	frontLeftMotor.setPID(DRIVE_P_DEFAULT, DRIVE_I_DEFAULT, DRIVE_D_DEFAULT);

	}
	public void driveConfigVbus()
	{
    	rearRightMotor = new CANTalon(REAR_RIGHT_CHANNEL);
    	rearRightMotor.changeControlMode(TalonControlMode.PercentVbus);
    	
    	midRightMotor = new CANTalon(MID_RIGHT_CHANNEL);
    	midRightMotor.changeControlMode(TalonControlMode.PercentVbus);
    	
    	frontRightMotor = new CANTalon(FRONT_RIGHT_CHANNEL);
    	frontRightMotor.changeControlMode(TalonControlMode.PercentVbus);
    	
    	rearLeftMotor = new CANTalon(REAR_LEFT_CHANNEL);
    	rearLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
    	
    	midLeftMotor = new CANTalon(MID_LEFT_CHANNEL);
    	midLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
    	
		frontLeftMotor = new CANTalon(FRONT_LEFT_CHANNEL);
    	frontLeftMotor.changeControlMode(TalonControlMode.PercentVbus);

	}
	public void driveInit(FunctionalityMode setupMode)
	{
    	rearRightMotor = new CANTalon(REAR_RIGHT_CHANNEL);	    	
    	midRightMotor = new CANTalon(MID_RIGHT_CHANNEL);
    	frontRightMotor = new CANTalon(FRONT_RIGHT_CHANNEL);
    	rearLeftMotor = new CANTalon(REAR_LEFT_CHANNEL);
    	midLeftMotor = new CANTalon(MID_LEFT_CHANNEL);
		frontLeftMotor = new CANTalon(FRONT_LEFT_CHANNEL);	

		if(setupMode == FunctionalityMode.Competition || setupMode == FunctionalityMode.Debug_Encoder)
		{
			driveConfigEncoder();
			robotDrive = new DriveThreeMotorTankDrive(rearRightMotor, midRightMotor, frontRightMotor, rearLeftMotor, midLeftMotor, frontLeftMotor);
		}
		else if(setupMode == FunctionalityMode.Debug_Vbus)
		{
			driveConfigVbus();
		}
		robotDrive = new DriveThreeMotorTankDrive(rearRightMotor, midRightMotor, frontRightMotor, rearLeftMotor, midLeftMotor, frontLeftMotor);
    	rightJoystick = new Joystick(JOYSTICK_RIGHT_CHANNEL);
    	leftJoystick = new Joystick(JOYSTICK_LEFT_CHANNEL);

	}
	public void shooterConfigVbus()
	{

		ELEVATE_UP_SPEED = 0.5;
		ELEVATE_DOWN_SPEED = -0.3;
		FIRE_SPEED = 1.0;
		FEED_SPEED = -FIRE_SPEED / 2.5;

    	rightFlywheelMotor.changeControlMode(TalonControlMode.PercentVbus);
    	rightFlywheelMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	
    	leftFlywheelMotor.changeControlMode(TalonControlMode.PercentVbus);
    	leftFlywheelMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	
    	rightElevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
    	
    	leftElevatorMotor.changeControlMode(TalonControlMode.PercentVbus);
    	
    	tiltMotor.changeControlMode(TalonControlMode.PercentVbus);
    	tiltPID.disable();
    	
    	feedMotor.changeControlMode(TalonControlMode.PercentVbus);
    	
    	flywheel = new Flywheel(
    			new ActuatorLauncherFeedInvertRight(leftFlywheelMotor, rightFlywheelMotor, feedMotor), 
    			/*new ActuatorSimple(feedMotor), */
    			new ActuatorSimple(feedMotor), 
    			new ActuatorLauncherFeedInvertRight(leftFlywheelMotor, rightFlywheelMotor, feedMotor),
    			new SensorRawButton(LOAD_FRONT_BUTTON, rightJoystick), 
    			new ActuatorTwoMotor(leftElevatorMotor,rightElevatorMotor),
    			new ActuatorSimple(tiltMotor));
	}
	public void shooterConfigEncoder(boolean rezeroEncoders)
	{
    	
    		//1 encoder tick per 20ms
    		ELEVATE_DOWN_SPEED = -1000.0;
    		ELEVATE_UP_SPEED = 1000.0;
    		FIRE_SPEED = -50000.0;
    		FEED_SPEED = -FIRE_SPEED / 2.5;

    		
	    	elevateOneShot = new SensorOneShot(false);
    	
	    	rightFlywheelMotor.setPID(SHOOTER_P_DEFAULT, SHOOTER_I_DEFAULT, SHOOTER_D_DEFAULT);
	    	rightFlywheelMotor.changeControlMode(TalonControlMode.Speed);
	    	rightFlywheelMotor.reverseSensor(true);
    	
	    	leftFlywheelMotor.setPID(SHOOTER_P_DEFAULT, SHOOTER_I_DEFAULT, SHOOTER_D_DEFAULT);
	    	leftFlywheelMotor.changeControlMode(TalonControlMode.Speed);
	    	leftFlywheelMotor.reverseSensor(true);
	    	
	    	rightElevatorMotor.changeControlMode(TalonControlMode.Position);
	    	rightElevatorMotor.reverseSensor(true);
	    	rightElevatorMotor.setPID(ELEVATOR_P_DEFAULT, ELEVATOR_I_DEFAULT, ELEVATOR_D_DEFAULT);
	    	
	    	leftElevatorMotor.changeControlMode(TalonControlMode.Position);
	    	leftElevatorMotor.reverseSensor(true);
	    	leftElevatorMotor.setPID(ELEVATOR_P_DEFAULT, ELEVATOR_I_DEFAULT, ELEVATOR_D_DEFAULT);

	    	
			if(rezeroEncoders)
			{
				tiltEncoder.reset();
				rightElevatorMotor.setPosition(0.0);
	    		leftElevatorMotor.setPosition(0.0);

			}
			
			tiltPID.enable();
			
	    	feedMotor = new CANTalon(FEED_CHANNEL);
    		
	    	
    		flywheel = new Flywheel(
	    			new ActuatorLauncherFeedInvertRight(leftFlywheelMotor, rightFlywheelMotor, feedMotor), 
	    			new ActuatorSimple(feedMotor), 
	    			new ActuatorLauncherFeedInvertRight(leftFlywheelMotor, rightFlywheelMotor, feedMotor),
	    			new SensorThreeAND(
	    					new SensorHitTest(new SensorCANTalonPIDError(rightFlywheelMotor), SHOOTER_ERROR, -SHOOTER_ERROR),
	    					new SensorHitTest(new SensorCANTalonPIDError(rightElevatorMotor), ELEVATION_ERROR, -ELEVATION_ERROR),
	    					new SensorHitTest(new SensorCANTalonPIDError(tiltMotor), TILT_ERROR, -TILT_ERROR)
	    			),
	    			new ActuatorTwoMotor(leftElevatorMotor, rightElevatorMotor),
	    			new ActuatorClosedLoop(tiltPID)
	    	
	    	);

	}
	public void rezeroElevator()
	{
		shooterConfigVbus();
		rightElevatorMotor.set(REZERO_SPEED);
		leftElevatorMotor.set(REZERO_SPEED);
		while(true)
		{
			//TODO: add in time limit for autonomous
			//If the limit switches break, the robot could enter a broken state, so be able to exit rezeroing.
			if(leftJoystick.getRawButton(REZERO_INTERRUPT_BUTTON))
			{
				rightElevatorMotor.set(0.0);
				leftElevatorMotor.set(0.0);
				//Robot is in Vbus mode now.
				return;
			}
			boolean leftLimitHit = !leftElevatorLimit.get();
			boolean rightLimitHit = !rightElevatorLimit.get();
			if(leftLimitHit)
			{
				leftElevatorMotor.set(0.0);
				leftElevatorMotor.setPosition(0.0);
			}
			if(rightLimitHit)
			{
				rightElevatorMotor.set(0.0);
				rightElevatorMotor.setPosition(0.0);
			}
			if(leftLimitHit && rightLimitHit)
			{
				break;
			}
		}
		shooterConfigEncoder(false);
	}
    public void shooterInit(FunctionalityMode setupMode)
    {
    	//NOTE: Right and left limit are true when open
    	leftElevatorLimit = new DigitalInput(LEFT_ELEVATOR_LIMIT_CHANNEL);
    	rightElevatorLimit = new DigitalInput(RIGHT_ELEVATOR_LIMIT_CHANNEL);
    	if(setupMode == FunctionalityMode.Competition || setupMode == FunctionalityMode.Debug_Encoder)
    	{
    		//1 encoder tick per 20ms
    		ELEVATE_DOWN_SPEED = -1000.0;
    		ELEVATE_UP_SPEED = 1000.0;
    		
    		FIRE_SPEED = -50000.0;
	    	elevateOneShot = new SensorOneShot(false);
    		rightFlywheelMotor = new CANTalon(RIGHT_FLYWHEEL_CHANNEL);
    	
	    	leftFlywheelMotor = new CANTalon(LEFT_FLYWHEEL_CHANNEL);
	    	
	    	rightElevatorMotor = new CANTalon(RIGHT_ELEVATOR_CHANNEL);
	    	
	    	leftElevatorMotor = new CANTalon(LEFT_ELEVATOR_CHANNEL);

	    	
	    	tiltMotor = new CANTalon(TILT_CHANNEL);
	    	tiltEncoder = new Encoder(TILT_ENCODER_A, TILT_ENCODER_B);
			tiltPID = new PIDController(CAMERA_Y_P_DEFAULT, CAMERA_Y_I_DEFAULT, CAMERA_Y_D_DEFAULT, tiltEncoder, tiltMotor);

	    	feedMotor = new CANTalon(FEED_CHANNEL);

	    	if(setupMode == FunctionalityMode.Competition)
	    	{
	    		shooterConfigEncoder(true);
	    	}
//	    	if(setupMode == FunctionalityMode.Debug_Encoder)
//	    	{
//		    	flywheel = new Flywheel(
//		    			new ActuatorLauncherFeedInvertRight(leftFlywheelMotor, rightFlywheelMotor, feedMotor), 
//		    			/*new ActuatorSimple(feedMotor), */
//		    			new ActuatorSimple(feedMotor), 
//		    			new ActuatorLauncherFeedInvertRight(leftFlywheelMotor, rightFlywheelMotor, feedMotor),
//		    			new SensorRawButton(LOAD_FRONT_BUTTON, rightJoystick), 
//		    			new ActuatorSimple(rightElevatorMotor),
//		    			new ActuatorClosedLoop(tiltPID)
//		    			);
//	    	}
    	
	    			//Prevent tilt motor from actuating below the chassis
	    			//new ActuatorBlockingMotor(tiltMotor, new SensorHitTest(new SensorCANTalon(rightElevatorMotor), ELEVATION_MAX, ELEVATION_ABOVE_CHASSIS)));
    	}
   
    	else if(setupMode == FunctionalityMode.Debug_Vbus)
    	{
    		shooterConfigVbus();
    	}

    }
	public void climberInit(FunctionalityMode setupMode)
	{
		if(setupMode == FunctionalityMode.Competition || setupMode == FunctionalityMode.Debug_Encoder)
		{
			climberMotor = new CANTalon(CLIMBER_CHANNEL);
			climberMotor.changeControlMode(TalonControlMode.Position);
			climberMotor.setPID(CLIMBER_P_DEFAULT, CLIMBER_I_DEFAULT, CLIMBER_D_DEFAULT);
		}
		else if(setupMode == FunctionalityMode.Debug_Vbus)
		{
			climberMotor = new CANTalon(CLIMBER_CHANNEL);
		}
    	
    	climbUpOneShot = new SensorOneShot(false);
    	climbDownOneShot = new SensorOneShot(false);
    	
    	
	}
	public void cameraInit(FunctionalityMode setupMode)
	{
    	navx = new AHRS(SerialPort.Port.kMXP);
    	
    	//TODO: The navx is mounted vertically rather than horizontally, so this may not get the angle correctly
      	angleUnwrapper = new SensorUnwrapper(180.0, new SensorNavxAngle(navx));
      	try
      	{
      		//use cam0 to change properties of the camera such as FPS, exposure, etc.
    		
      		int session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
      		camera = new ImageGrabber(session, true);
      		
      		
      		//TODO: may need to add argument and logic to represent the camera view angle in the Y direction, if the camera's veiw angle isn't square. Edit the file in the LakeLib project, re export LakeLib to a jar (select built outputs and compression, write to LakeLib.jar) and restart eclipse if this ends up being true
      		angleToTargetGrabber = new SensorTargetAngleFromImage(CAMERA_RESOLUTION_X, CAMERA_RESOLUTION_Y, CAMERA_VIEW_ANGLE, TARGET_ASPECT_RATIO, TARGET_HUE_RANGE, TARGET_SATURATION_RANGE, TARGET_VALUE_RANGE, PARTICLE_AREA_MINIMUM);
      	
      		cameraExists = true;
      	}
      	catch(Exception ex)
      	{
      		cameraExists = false;
      		System.err.println("Camera does not exist!");
      	}
  		cameraXPID = new PIDController(CAMERA_X_P_DEFAULT, CAMERA_X_I_DEFAULT, CAMERA_X_D_DEFAULT, angleUnwrapper, new PIDOutputThreeMotorRotate(robotDrive));

	}
	public void smartDashboardInit(FunctionalityMode setupMode)
	{
		if(setupMode == FunctionalityMode.Competition || setupMode == FunctionalityMode.Debug_Encoder)
		{
			NetworkTable.flush();
			SmartDashboard.putBoolean(AUTO_KEY, true);
			SmartDashboard.putBoolean(REZERO_KEY, false);
			
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
	    	
	    	SmartDashboard.putNumber(SHOOTER_KEY_P, SHOOTER_P_DEFAULT);
	    	SmartDashboard.putNumber(SHOOTER_KEY_I, SHOOTER_I_DEFAULT);
	    	SmartDashboard.putNumber(SHOOTER_KEY_D, SHOOTER_D_DEFAULT);
	    	
	    	SmartDashboard.putString(DRIVE_MODE_KEY, DRIVE_MODE_SPEED);
		}
		else{}

	}
    @Override
	public void robotInit() 
    {
    	
	    //if the robot throws a null pointer exception, its probably because something didn't get initialized in here! Check the error thrown (RioLog should have it if the driver station doesn't) to get more info
	    smartDashboardInit(runMode);
    	driveInit(FunctionalityMode.Debug_Vbus);	
	    shooterInit(runMode);
	    //climberInit();
	    cameraInit(runMode);
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
    @Override
	public void autonomousInit() 
    {
    	frontRightMotor.changeControlMode(TalonControlMode.Position);
    	frontLeftMotor.changeControlMode(TalonControlMode.Position);
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
	public void autonomousPeriodic() 
    {
    	if(SmartDashboard.getBoolean(AUTO_KEY))
    	{
    		//Drive forward whatever distance it is to cross the defense,
    		//Drive backwards to position
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopInit()
    {
    	if(runMode == FunctionalityMode.Competition || runMode == FunctionalityMode.Debug_Encoder)
    	{
    		if(SmartDashboard.getBoolean(REZERO_KEY))
    		{
    			tiltEncoder.reset();
    			rightElevatorMotor.setPosition(0);
    			leftElevatorMotor.setPosition(0);
    		}
	    	frontRightMotor.setPID(SmartDashboard.getNumber(DRIVE_KEY_P), SmartDashboard.getNumber(DRIVE_KEY_I), SmartDashboard.getNumber(DRIVE_KEY_D));
	    	frontLeftMotor.setPID(SmartDashboard.getNumber(DRIVE_KEY_P), SmartDashboard.getNumber(DRIVE_KEY_I), SmartDashboard.getNumber(DRIVE_KEY_D));
	 		
	    	tiltPID.setPID(SmartDashboard.getNumber(CAMERA_Y_KEY_P), SmartDashboard.getNumber(CAMERA_Y_KEY_I), SmartDashboard.getNumber(CAMERA_Y_KEY_D));
			
	 		cameraXPID.setPID(SmartDashboard.getNumber(CAMERA_X_KEY_P), SmartDashboard.getNumber(CAMERA_X_KEY_I), SmartDashboard.getNumber(CAMERA_X_KEY_D));
			
			rightElevatorMotor.setPID(SmartDashboard.getNumber(ELEVATOR_KEY_P), SmartDashboard.getNumber(ELEVATOR_KEY_I), SmartDashboard.getNumber(ELEVATOR_KEY_D));
			
			//climberMotor.setPID(SmartDashboard.getNumber(CLIMBER_KEY_P),SmartDashboard.getNumber(CLIMBER_KEY_I), SmartDashboard.getNumber(CLIMBER_KEY_D));
			
			rightFlywheelMotor.setPID(SmartDashboard.getNumber(SHOOTER_KEY_P), SmartDashboard.getNumber(SHOOTER_KEY_I), SmartDashboard.getNumber(SHOOTER_KEY_D));
			leftFlywheelMotor.setPID(SmartDashboard.getNumber(SHOOTER_KEY_P), SmartDashboard.getNumber(SHOOTER_KEY_I), SmartDashboard.getNumber(SHOOTER_KEY_D));

    	}
	    
    }
    /**
     * 
     * @return Angle for tilter
     */
    public double cameraTeleop(FunctionalityMode teleopMode)
    {
    	if(teleopMode == FunctionalityMode.Competition)
    	{
	    	boolean findTargetAngle = rightJoystick.getRawButton(AIM_BUTTON);
	    	//TODO: Tilt angle is currently set to just always be manually set
	    	//Transform [-1,1] to [0,1]
	    	double tiltAngle = ((-rightJoystick.getRawAxis(TILT_AXIS) + 1) / 2)  * TILT_MAX ;
	    	if(findTargetAngle && cameraExists)
	    	{
	    		//Need to find angle based upon a fully elevated shooter.
	    		flywheel.elevate(ELEVATION_MAX);
	    		//TODO: May want to wrap this logic into a couple of modules and put them in the PIDDrive class. Do this after basic functionality is established
	    		//TODO: Will probably want to add some logic to block untill the flywheel is elevated
	    		if(!cameraXPID.isEnabled())
	    		{
	    			
	    			NIVision.PointDouble angleToTarget = angleToTargetGrabber.sense(camera.getImage());
	    			
	    			SmartDashboard.putNumber("Angle to target X", angleToTarget.x);
	    			SmartDashboard.putNumber("Angle to target Y", angleToTarget.y);
	    			//tiltMotor.setPID(SmartDashboard.getNumber(CAMERA_Y_KEY_P), SmartDashboard.getNumber(CAMERA_Y_KEY_I), SmartDashboard.getNumber(CAMERA_Y_KEY_D));
	    			cameraXPID.setPID(SmartDashboard.getNumber(CAMERA_X_KEY_P), SmartDashboard.getNumber(CAMERA_X_KEY_I), SmartDashboard.getNumber(CAMERA_X_KEY_D));
	    			cameraXPID.setSetpoint(angleToTarget.x);
	    			//PID will drive for us
	    			cameraXPID.enable();
	    			//Tilt angle is ratio between the angle to the target and the maximum angle the camera can see, scaled by the maximum tilt
	    			//TODO: Need to find TILT_MAX
	    			tiltAngle = (angleToTarget.y / CAMERA_VIEW_ANGLE) * TILT_MAX;
	    		}	
	    		
	        	
	    	}
	    	else
	    	{
	    		if(cameraXPID.isEnabled())
	    		{
	    			//Prevent PID from screwing with normal driving
	    			cameraXPID.disable();
	    		}
	
	    	}	
	    	return tiltAngle;
	    	
    	}
    	else
    	{
	    	double tiltAngle = ((-rightJoystick.getRawAxis(TILT_AXIS) + 1) / 2)  * TILT_MAX ;
	    	boolean findTargetAngle = rightJoystick.getRawButton(AIM_BUTTON);
	    	flywheel.elevate(ELEVATION_MAX);
	    	return tiltAngle;
    	}
    }
    /**
     * 
     * @param tiltAngle Angle to tilt the shooter to. This should be calculated in cameraTeleop()
     * @param teleopMode
     */
    public void shooterTeleop(double tiltAngle, FunctionalityMode teleopMode)
    {
    	if(leftJoystick.getRawButton(REZERO_BUTTON))
    	{
    		rezeroElevator();
    	}
    	if(teleopMode == FunctionalityMode.Competition)
    	{
    		//TODO: Might want to make this optional
        	boolean leftElevatorLimitHit = !leftElevatorLimit.get();
        	boolean rightElevatorLimitHit = !rightElevatorLimit.get();
        	if(leftElevatorLimitHit)
        	{
        		leftElevatorMotor.setPosition(0.0);
        	}
        	if(rightElevatorLimitHit)
        	{
        		rightElevatorMotor.setPosition(0.0);
        	}
        	
    		boolean loadFront = rightJoystick.getRawButton(LOAD_FRONT_BUTTON);
	    	boolean elevateUp = rightJoystick.getRawButton(ELEVATE_UP_BUTTON);
	    	boolean elevateDown = rightJoystick.getRawButton(ELEVATE_DOWN_BUTTON);
	    	boolean fire = rightJoystick.getRawButton(FIRE_BUTTON);
	    	boolean aim = (boolean) elevateOneShot.sense(rightJoystick.getRawButton(AIM_BUTTON));
	    	//Only tilt if the shooter is clear from the chassis
	    	
	    	if(rightElevatorMotor.getPosition() > ELEVATION_ABOVE_CHASSIS)
	    	{
	    		flywheel.tilt(tiltAngle);
	    	}
	    	else
	    	{
	    		flywheel.tilt(0.0);
	    	}
	    	if(fire)
	    	{
	    		flywheel.fire(FIRE_SPEED, ELEVATION_MAX, tiltAngle, FEED_SPEED);
	    	}
	    	else
	    	{
	    		if(elevateUp && elevatorPosition < ELEVATION_MAX)
	    		{
	    			elevatorPosition += ELEVATE_UP_SPEED;
	    		}
	    		else if(elevateDown && elevatorPosition > 0)
	    		{
	    			elevatorPosition += ELEVATE_DOWN_SPEED;
	    		}
	    		if(aim)
	    		{
	    			if(elevatorState == true)
	    			{
	    				elevatorPosition = 0;
	    				elevatorState = false;
	    			}
	    			else
	    			{
	    				elevatorPosition = ELEVATION_MAX;
	    				elevatorState = true;
	    			}
	    		}
	    		flywheel.elevate(elevatorPosition);
	    		//Stop the wheels spinning and set the elevation and tilt to the specified amounts
    			
    			if(loadFront)
    	    	{
    	    		flywheel.loadFront(FEED_SPEED);
    	    	}
    			else
    			{
    				flywheel.loadFront(0.0);
    			}
    		

	    	}
	    		    	
	
	    	
    	}
  
    	else if(teleopMode == FunctionalityMode.Debug_Vbus)
    	{
    		boolean loadFront = rightJoystick.getRawButton(LOAD_FRONT_BUTTON);
	    	boolean elevateUp = rightJoystick.getRawButton(ELEVATE_UP_BUTTON);
	    	boolean elevateDown = rightJoystick.getRawButton(ELEVATE_DOWN_BUTTON);
	    	boolean fire = rightJoystick.getRawButton(FIRE_BUTTON);
	    	
	    	boolean tiltUp = rightJoystick.getRawButton(6);
	    	boolean tiltDown = rightJoystick.getRawButton(7);
	    	if(fire)
	    	{
	    		flywheel.wheel(-FIRE_SPEED);
	    	}
	    	else if(loadFront)
	    	{
	    		flywheel.loadFront(FEED_SPEED);
	    	}	    
	    	else if(elevateUp)
	    	{
	    		flywheel.elevate(ELEVATE_UP_SPEED);
	    	}
	    	else if(elevateDown)
	    	{
	    		flywheel.elevate(ELEVATE_DOWN_SPEED);
	    	}
	    	else if(tiltUp)
	    	{
	    		flywheel.tilt(1.0);
	    	}
	    	else if(tiltDown)
	    	{
	    		flywheel.tilt(-1.0);
	    	}
	    	else
	    	{
	    		flywheel.endFire(0.0, 0.0);
	    	}
	    	 	
    	}
    	else if(teleopMode == FunctionalityMode.Debug_Encoder)
    	{
	    	boolean elevateUp = rightJoystick.getRawButton(ELEVATE_UP_BUTTON);
	    	boolean elevateDown = rightJoystick.getRawButton(ELEVATE_DOWN_BUTTON);
	    	boolean fire = rightJoystick.getRawButton(FIRE_BUTTON);
    		boolean loadFront = rightJoystick.getRawButton(LOAD_FRONT_BUTTON);
	    	boolean tiltUp = rightJoystick.getRawButton(6);
	    	boolean tiltDown = rightJoystick.getRawButton(7);
	    	
	    	if(fire)
	    	{
	    		flywheel.wheel(-FIRE_SPEED);
	    	}
	    	else if(loadFront)
	    	{
	    		flywheel.loadFront(FEED_SPEED);
	    	}	    
	    
	    	else if(elevateUp)
    		{
    			elevatorPosition += ELEVATE_UP_SPEED;
    		}
    		else if(elevateDown)
    		{
    			elevatorPosition += ELEVATE_DOWN_SPEED;
    		}	    	
    	
    		else if(tiltUp)
	    	{
	    		debugTiltPosition += 1.0;
	    	}
	    	else if(tiltDown)
	    	{
	    		debugTiltPosition += -1.0;
	    	}
	    	else
	    	{
	    		flywheel.endFire(elevatorPosition, debugTiltPosition);
	    	}
	    	 	
	    	
	
    	}
    }
    public void driveTeleop()
    {
    	double RY = rightJoystick.getRawAxis(RIGHT_Y_AXIS);
    	double LY = -leftJoystick.getRawAxis(LEFT_Y_AXIS);
    	robotDrive.drive(LY, RY);

    }
    public void climberTeleop(FunctionalityMode teleopMode)
    {
		
		if(teleopMode == FunctionalityMode.Competition || teleopMode == FunctionalityMode.Debug_Encoder)
		{
			boolean climbUp = (Boolean) climbUpOneShot.sense(leftJoystick.getRawButton(CLIMB_UP_BUTTON));
			boolean climbDown = (Boolean) climbDownOneShot.sense(leftJoystick.getRawButton(CLIMB_DOWN_BUTTON));

			//TODO: need to find max climber height
	    	if(climbUp && climberIndex < CLIMBER_POSITIONS.length - 1)
	    	{
	    		climberIndex++;
	    	}
	    	else if(climbDown && climberIndex > 0)
	    	{
	    		climberIndex--;
	    	}
	    	climber.actuate(CLIMBER_POSITIONS[climberIndex]);
		}
		else if(teleopMode == FunctionalityMode.Debug_Vbus)
		{
			boolean climbUp = leftJoystick.getRawButton(CLIMB_UP_BUTTON);
			boolean climbDown = leftJoystick.getRawButton(CLIMB_DOWN_BUTTON);

			if(climbUp)
			{
				climber.actuate(1.0);
			}
			else if(climbDown)
			{
				climber.actuate(-1.0);
			}
			else
			{
				climber.actuate(0.0);
			}
		}
    }
    @Override
	public void teleopPeriodic() 
    {
    	//DEBUG
    	
    		SmartDashboard.putNumber("Tilt Encoder", tiltEncoder.getDistance()); //TODO: Max Tilt:
    		SmartDashboard.putNumber("Elevator Encoder Right", rightElevatorMotor.getPosition()); //TODO: Max Elevation:
    		SmartDashboard.putNumber("Elevator encoder left", leftElevatorMotor.getPosition());
    		SmartDashboard.putNumber("Right shooter speed", rightFlywheelMotor.getSpeed());
    		SmartDashboard.putNumber("Left shooter speed", leftFlywheelMotor.getSpeed());    		
    		SmartDashboard.putNumber("Tilt setpoint", tiltPID.getSetpoint());
    		
    		SmartDashboard.putBoolean("Left limit", leftElevatorLimit.get());
    		SmartDashboard.putBoolean("Right limit", rightElevatorLimit.get());
    		SmartDashboard.putNumber(ELEVATION_KEY, (rightElevatorMotor.getPosition() + leftElevatorMotor.getPosition()) / 2);
	    	SmartDashboard.putNumber(TILT_KEY, tiltEncoder.getDistance());
	    	SmartDashboard.putNumber(SHOOTER_SPEED_KEY, (Math.abs(rightFlywheelMotor.getSpeed()) + Math.abs(leftFlywheelMotor.getSpeed())) / 2);
	    	

    		//SmartDashboard.putNumber("Unwrapped navx angle", angleUnwrapper.sense(null));
    	//END DEBUG
    		//TODO: Get drive working, add its config here
    		if(rightJoystick.getRawButton(VOLTAGE_MODE_BUTTON))
    		{
    			runMode = FunctionalityMode.Debug_Vbus;
    			shooterConfigVbus();
    			SmartDashboard.putString(DRIVE_MODE_KEY, DRIVE_MODE_VBUS);
    			
    		}
    		else if(rightJoystick.getRawButton(SPEED_MODE_BUTTON))
    		{
    			
    			runMode = FunctionalityMode.Competition;
    			shooterConfigEncoder(false);
    			SmartDashboard.putString(DRIVE_MODE_KEY, DRIVE_MODE_SPEED);
    		}
    		//If aiming isn't enabled, the tilt angle will be the rightJoystick's Z axis
    		//As a result, cameraTeleop must always be run before shooterTeleop
    		//double tiltAngle = cameraTeleop();
    	 	double tiltAngle = ((-rightJoystick.getRawAxis(TILT_AXIS) + 1) / 2)  * TILT_MAX ;
    	    
    		shooterTeleop(tiltAngle, runMode);
    		
    		driveTeleop();
    		//climberTeleop(runMode);

    }
    
    /**
     * This function is called periodically during test mode
     */
    @Override
	public void testPeriodic() 
    {
    	//Add motors and sensors using the LiveWindow class to test them. ex:
    	//LiveWindow.addActuator("Rear right motor", REAR_RIGHT_CHANNEL, rearRightMotor);
    	//I've never actually tried using the LiveWindow so some fiddling may be required
    }
    
}
