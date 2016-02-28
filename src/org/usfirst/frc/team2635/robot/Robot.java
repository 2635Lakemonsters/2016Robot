
package org.usfirst.frc.team2635.robot;

import org.usfirst.frc.team2635.modules.ActuatorLauncherFeed;
import org.usfirst.frc.team2635.modules.DriveThreeMotor;
import org.usfirst.frc.team2635.modules.DriveThreeMotorTankDrive;
import org.usfirst.frc.team2635.modules.Flywheel;
import org.usfirst.frc.team2635.modules.PIDOutputThreeMotorRotate;
import org.usfirst.frc.team2635.modules.SensorCANTalonPIDError;
import org.usfirst.frc.team2635.modules.SensorThreeAND;
import org.usfirst.frc.team2635.modules.SensorNavxAngle;

import com.kauailabs.navx.frc.AHRS;
import com.lakemonsters2635.actuator.interfaces.BaseActuator;
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
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

import static org.usfirst.frc.team2635.robot.Constants.Drive.*;
import static org.usfirst.frc.team2635.robot.Constants.Camera.*;
import static org.usfirst.frc.team2635.robot.Constants.Climber.*;
import static org.usfirst.frc.team2635.robot.Constants.Shooter.*;

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
	FunctionalityMode runMode = FunctionalityMode.Debug_Vbus;
	
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
		
		CANTalon rightFlywheelMotor;
		CANTalon leftFlywheelMotor;
		
		CANTalon feedMotor;
		
		CANTalon rightElevatorMotor;
		CANTalon leftElevatorMotor;
		
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
	public void driveInit(FunctionalityMode setupMode)
	{
		if(setupMode == FunctionalityMode.Competition || setupMode == FunctionalityMode.Debug_Encoder)
		{
			//TODO: might have to invert the output for some of these. There is a function to do that in CANTalon
	    	rearRightMotor = new CANTalon(REAR_RIGHT_CHANNEL);
	    	rearRightMotor.changeControlMode(TalonControlMode.Follower);
	    	rearRightMotor.set(FRONT_RIGHT_CHANNEL);
	    	
	    	midRightMotor = new CANTalon(MID_RIGHT_CHANNEL);
	    	midRightMotor.changeControlMode(TalonControlMode.Follower);
	    	midRightMotor.set(FRONT_RIGHT_CHANNEL);
	    	
	    	frontRightMotor = new CANTalon(FRONT_RIGHT_CHANNEL);
	    	frontRightMotor.changeControlMode(TalonControlMode.Speed);
	    	frontRightMotor.setPID(DRIVE_P_DEFAULT, DRIVE_I_DEFAULT, DRIVE_D_DEFAULT);
	    	
	    	rearLeftMotor = new CANTalon(REAR_LEFT_CHANNEL);
	    	rearLeftMotor.changeControlMode(TalonControlMode.Follower);
	    	rearLeftMotor.set(FRONT_LEFT_CHANNEL);
	    	
	    	midLeftMotor = new CANTalon(MID_LEFT_CHANNEL);
	    	midLeftMotor.changeControlMode(TalonControlMode.Follower);
	    	midLeftMotor.set(FRONT_LEFT_CHANNEL);
	    	
			frontLeftMotor = new CANTalon(FRONT_LEFT_CHANNEL);
	    	frontLeftMotor.changeControlMode(TalonControlMode.Speed);
	    	frontLeftMotor.setPID(DRIVE_P_DEFAULT, DRIVE_I_DEFAULT, DRIVE_D_DEFAULT);
			robotDrive = new DriveThreeMotorTankDrive(rearRightMotor, midRightMotor, frontRightMotor, rearLeftMotor, midLeftMotor, frontLeftMotor);
		}
		else if(setupMode == FunctionalityMode.Debug_Vbus)
		{
			//Default vbus mode
	    	rearRightMotor = new CANTalon(REAR_RIGHT_CHANNEL);	    	
	    	midRightMotor = new CANTalon(MID_RIGHT_CHANNEL);
	    	frontRightMotor = new CANTalon(FRONT_RIGHT_CHANNEL);
	    	rearLeftMotor = new CANTalon(REAR_LEFT_CHANNEL);
	    	midLeftMotor = new CANTalon(MID_LEFT_CHANNEL);
			frontLeftMotor = new CANTalon(FRONT_LEFT_CHANNEL);	
		}
		robotDrive = new DriveThreeMotorTankDrive(rearRightMotor, midRightMotor, frontRightMotor, rearLeftMotor, midLeftMotor, frontLeftMotor);
    	rightJoystick = new Joystick(JOYSTICK_RIGHT_CHANNEL);
    	leftJoystick = new Joystick(JOYSTICK_LEFT_CHANNEL);

	}
    public void shooterInit(FunctionalityMode setupMode)
    {
    	if(setupMode == FunctionalityMode.Competition || setupMode == FunctionalityMode.Debug_Encoder)
    	{
    		//1 encoder tick per 20ms
    		ELEVATE_DOWN_SPEED = -1.0;
    		ELEVATE_UP_SPEED = 1.0;
    		
    		FIRE_SPEED = 500; //TODO: Get actual max speed estimate
	    	rightFlywheelMotor = new CANTalon(RIGHT_FLYWHEEL_CHANNEL);
	    	rightFlywheelMotor.setPID(SHOOTER_P_DEFAULT, SHOOTER_I_DEFAULT, SHOOTER_D_DEFAULT);
	    	rightFlywheelMotor.changeControlMode(TalonControlMode.Speed);
	    	
	    	leftFlywheelMotor = new CANTalon(LEFT_FLYWHEEL_CHANNEL);
	    	leftFlywheelMotor.setPID(SHOOTER_P_DEFAULT, SHOOTER_I_DEFAULT, SHOOTER_D_DEFAULT);
	    	leftFlywheelMotor.changeControlMode(TalonControlMode.Speed);
	    	
	    	rightElevatorMotor = new CANTalon(RIGHT_ELEVATOR_CHANNEL);
	    	rightElevatorMotor.changeControlMode(TalonControlMode.Position);
	    	rightElevatorMotor.setPID(ELEVATOR_P_DEFAULT, ELEVATOR_I_DEFAULT, ELEVATOR_D_DEFAULT);
	    	
	    	leftElevatorMotor = new CANTalon(LEFT_ELEVATOR_CHANNEL);
	    	leftElevatorMotor.changeControlMode(TalonControlMode.Follower);
	    	leftElevatorMotor.set(RIGHT_ELEVATOR_CHANNEL);
	    	
	    	tiltMotor = new CANTalon(TILT_CHANNEL);
	    	tiltMotor.changeControlMode(TalonControlMode.Position);
	    	tiltMotor.setPID(CAMERA_Y_P_DEFAULT, CAMERA_Y_I_DEFAULT, CAMERA_Y_D_DEFAULT);
	    
	    	feedMotor = new CANTalon(FEED_CHANNEL);
	    	
	    	flywheel = new Flywheel(
	    			new ActuatorLauncherFeed(leftFlywheelMotor, rightFlywheelMotor, feedMotor), 
	    			new ActuatorSimple(feedMotor), 
	    			new ActuatorLauncherFeed(leftFlywheelMotor, rightFlywheelMotor, feedMotor),
	    			new SensorThreeAND(
	    					new SensorHitTest(new SensorCANTalonPIDError(rightFlywheelMotor), SHOOTER_ERROR, -SHOOTER_ERROR),
	    					new SensorHitTest(new SensorCANTalonPIDError(rightElevatorMotor), ELEVATION_ERROR, -ELEVATION_ERROR),
	    					new SensorHitTest(new SensorCANTalonPIDError(tiltMotor), TILT_ERROR, -TILT_ERROR)
	    			),
	    			new ActuatorSimple(rightElevatorMotor),
	    			new ActuatorSimple(tiltMotor)
	    	);
	    			//Prevent tilt motor from actuating below the chassis
	    			//new ActuatorBlockingMotor(tiltMotor, new SensorHitTest(new SensorCANTalon(rightElevatorMotor), ELEVATION_MAX, ELEVATION_ABOVE_CHASSIS)));
    	}
    	else if(setupMode == FunctionalityMode.Debug_Vbus)
    	{
    		ELEVATE_UP_SPEED = 0.5;
    		ELEVATE_DOWN_SPEED = -0.3;
    		FIRE_SPEED = 1.0;
	    	rightFlywheelMotor = new CANTalon(RIGHT_FLYWHEEL_CHANNEL);
	    	rightFlywheelMotor.changeControlMode(TalonControlMode.PercentVbus);
	    	rightFlywheelMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
	    	rightFlywheelMotor.enable();
	    	
	    	leftFlywheelMotor = new CANTalon(LEFT_FLYWHEEL_CHANNEL);
	    	leftFlywheelMotor.changeControlMode(TalonControlMode.PercentVbus);
	    	
	    	rightElevatorMotor = new CANTalon(RIGHT_ELEVATOR_CHANNEL);
	    	leftElevatorMotor = new CANTalon(LEFT_ELEVATOR_CHANNEL);
	    	leftElevatorMotor.changeControlMode(TalonControlMode.Follower);
	    	leftElevatorMotor.set(RIGHT_ELEVATOR_CHANNEL);
	    	
	    	tiltMotor = new CANTalon(TILT_CHANNEL);
	    	
	    	feedMotor = new CANTalon(FEED_CHANNEL);
	    	
	    	flywheel = new Flywheel(
	    			new ActuatorLauncherFeed(leftFlywheelMotor, rightFlywheelMotor, feedMotor), 
	    			/*new ActuatorSimple(feedMotor), */
	    			new ActuatorSimple(feedMotor), 
	    			new ActuatorLauncherFeed(leftFlywheelMotor, rightFlywheelMotor, feedMotor),
	    			new SensorRawButton(LOAD_FRONT_BUTTON, rightJoystick), 
	    			new ActuatorSimple(rightElevatorMotor),
	    			new ActuatorSimple(tiltMotor));

    	}
		FEED_SPEED = FIRE_SPEED / 2.5;
	
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
    		cam0 = new USBCamera("cam0");
    		

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
			SmartDashboard.putBoolean(AUTO_KEY, true);
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
		}
		else{}

	}
    @Override
	public void robotInit() 
    {
    
	    //if the robot throws a null pointer exception, its probably because something didn't get initialized in here! Check the error thrown (RioLog should have it if the driver station doesn't) to get more info
	    smartDashboardInit(runMode);
    	driveInit(runMode);	
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
	    	frontRightMotor.setPID(SmartDashboard.getNumber(DRIVE_KEY_P), SmartDashboard.getNumber(DRIVE_KEY_I), SmartDashboard.getNumber(DRIVE_KEY_D));
	    	frontLeftMotor.setPID(SmartDashboard.getNumber(DRIVE_KEY_P), SmartDashboard.getNumber(DRIVE_KEY_I), SmartDashboard.getNumber(DRIVE_KEY_D));
	 		
	    	tiltMotor.setPID(SmartDashboard.getNumber(CAMERA_Y_KEY_P), SmartDashboard.getNumber(CAMERA_Y_KEY_I), SmartDashboard.getNumber(CAMERA_Y_KEY_D));
			
	 		cameraXPID.setPID(SmartDashboard.getNumber(CAMERA_X_KEY_P), SmartDashboard.getNumber(CAMERA_X_KEY_I), SmartDashboard.getNumber(CAMERA_X_KEY_D));
			
			rightElevatorMotor.setPID(SmartDashboard.getNumber(ELEVATOR_KEY_P), SmartDashboard.getNumber(ELEVATOR_KEY_I), SmartDashboard.getNumber(ELEVATOR_KEY_D));
			
			climberMotor.setPID(SmartDashboard.getNumber(CLIMBER_KEY_P),SmartDashboard.getNumber(CLIMBER_KEY_I), SmartDashboard.getNumber(CLIMBER_KEY_D));
			
			rightFlywheelMotor.setPID(SmartDashboard.getNumber(SHOOTER_KEY_P), SmartDashboard.getNumber(SHOOTER_KEY_I), SmartDashboard.getNumber(SHOOTER_KEY_D));
    	}
	    
    }
    /**
     * 
     * @return Angle for tilter
     */
    public double cameraTeleop()
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
    			tiltMotor.setPID(SmartDashboard.getNumber(CAMERA_Y_KEY_P), SmartDashboard.getNumber(CAMERA_Y_KEY_I), SmartDashboard.getNumber(CAMERA_Y_KEY_D));
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
    /**
     * 
     * @param tiltAngle Angle to tilt the shooter to. This should be calculated in cameraTeleop()
     * @param teleopMode
     */
    public void shooterTeleop(double tiltAngle, FunctionalityMode teleopMode)
    {
    	if(teleopMode == FunctionalityMode.Competition)
    	{
	    	boolean loadFront = rightJoystick.getRawButton(LOAD_FRONT_BUTTON);
	    	boolean elevateUp = rightJoystick.getRawButton(ELEVATE_UP_BUTTON);
	    	boolean elevateDown = rightJoystick.getRawButton(ELEVATE_DOWN_BUTTON);
	    	boolean fire = rightJoystick.getRawButton(FIRE_BUTTON);
	    	//Only tilt if the shooter is clear from the chassis
	    	
	    	if(rightElevatorMotor.getPosition() > ELEVATION_ABOVE_CHASSIS)
	    	{
	    		flywheel.tilt(tiltAngle);
	    	}
	    	else
	    	{
	    		flywheel.tilt(TILT_RESTING);
	    	}
	    	if(fire)
	    	{
	    		flywheel.fire(FIRE_SPEED, ELEVATION_MAX, tiltAngle, FEED_SPEED);
	    	}
	    	else
	    	{
	    		if(elevateUp)
	    		{
	    			elevatorPosition += ELEVATE_UP_SPEED;
	    		}
	    		else if(elevateDown)
	    		{
	    			elevatorPosition += ELEVATE_DOWN_SPEED;
	    		}
	    		
	    		//Stop the wheels spinning and set the elevation and tilt to the specified amounts
    			flywheel.endFire(elevatorPosition, tiltAngle);

	    	}
	    	if(loadFront)
	    	{
	    		flywheel.loadFront(LOAD_FRONT_SPEED);
	    	}
	    	else
	    	{
	    		flywheel.loadFront(0.0);
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
	    	
    		if(elevateUp)
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
    	
    		SmartDashboard.putData("Tilt Encoder", tiltMotor); //TODO: Max Tilt:
    		SmartDashboard.putNumber("Elevator Encoder", rightElevatorMotor.getPosition()); //TODO: Max Elevation:
    		SmartDashboard.putData("Right wheel encoder", rightFlywheelMotor); //TODO: Max Speed:
    		SmartDashboard.putNumber("Right shooter speed", rightFlywheelMotor.getPosition());
    		//SmartDashboard.putNumber("Unwrapped navx angle", angleUnwrapper.sense(null));
    	//END DEBUG
    		
    		//If aiming isn't enabled, the tilt angle will be the rightJoystick's Z axis
    		//As a result, cameraTeleop must always be run before shooterTeleop
    		//double tiltAngle = cameraTeleop();
    		double tiltAngle = 0.0;
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
