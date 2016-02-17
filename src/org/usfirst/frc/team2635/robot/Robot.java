
package org.usfirst.frc.team2635.robot;

import org.usfirst.frc.team2635.modules.DriveThreeMotor;
import org.usfirst.frc.team2635.modules.DriveThreeMotorTankDrive;
import org.usfirst.frc.team2635.modules.SensorNavxAngle;

import com.kauailabs.navx.frc.AHRS;
import com.lakemonsters2635.actuator.interfaces.BaseDrive;
import com.lakemonsters2635.actuator.modules.DriveArcade;
import com.lakemonsters2635.sensor.interfaces.BaseSensor;
import com.lakemonsters2635.sensor.modules.SensorTargetAngleFromImage;
import com.lakemonsters2635.sensor.modules.SensorUnwrapper;
import com.lakemonsters2635.util.ImageGrabber;
import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
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
	final int REAR_RIGHT_CHANNEL = 0;
	final int MID_RIGHT_CHANNEL = 1;
	final int FRONT_RIGHT_CHANNEL = 2;
	
	final int REAR_LEFT_CHANNEL = 3;
	final int MID_LEFT_CHANNEL = 4;
	final int FRONT_LEFT_CHANNEL = 5;
	
	final int JOYSTICK_LEFT_CHANNEL = 1;
	final int JOYSTICK_RIGHT_CHANNEL = 0;
	
	final int RIGHT_Y_AXIS = 1;
	final int LEFT_Y_AXIS = 1;
	
	//Right hand joystick
	final int AIM_BUTTON = 3;
	final int FIRE_BUTTON = 1;
	
	//Left hand joystick
	final int CLIMB_UP_BUTTON = 3;
	final int CLIMB_DOWN_BUTTON = 2;
	

	final String DRIVE_KEY_P = "Drive P";
	final String DRIVE_KEY_I = "Drive I";
	final String DRIVE_KEY_D = "Drive D";
	
	double P_DEFAULT = 0.0;
	double I_DEFAULT = 0.0;
	double D_DEFAULT = 0.0;
	
	CANTalon rearRightMotor;
	CANTalon midRightMotor;
	CANTalon frontRightMotor;
	
	CANTalon rearLeftMotor;
	CANTalon midLeftMotor;
	CANTalon frontLeftMotor;

	DriveThreeMotor robotDrive;
	
	Joystick rightJoystick;
	Joystick leftJoystick;
	double scaler = 1000.0;

	
	AHRS navx;
	SensorUnwrapper testUnwrapper;
	ImageGrabber camera;
	BaseSensor<NIVision.PointDouble> angleToTargetGrabber;
	final double CAMERA_RESOLUTION_X = 680.0;
	final double CAMERA_RESOLUTION_Y = 460.0;
	final double CAMERA_VIEW_ANGLE = 64.0;
	final double TARGET_ASPECT_RATIO = 14.0/20.0;
	final NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(0, 130);
	final NIVision.Range TARGET_SATURATION_RANGE = new NIVision.Range(0, 255);
	final NIVision.Range TARGET_VALUE_RANGE = new NIVision.Range(250, 255);
	final double PARTICLE_AREA_MINIMUM = 0.5;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
    	SmartDashboard.putNumber(DRIVE_KEY_P, P_DEFAULT);
    	SmartDashboard.putNumber(DRIVE_KEY_I, I_DEFAULT);
    	SmartDashboard.putNumber(DRIVE_KEY_D, D_DEFAULT);
    	
    	rearRightMotor = new CANTalon(REAR_RIGHT_CHANNEL);
    	rearRightMotor.changeControlMode(TalonControlMode.Follower);
    	rearRightMotor.set(FRONT_RIGHT_CHANNEL);
    	
    	midRightMotor = new CANTalon(MID_RIGHT_CHANNEL);
    	midRightMotor.changeControlMode(TalonControlMode.Follower);
    	midRightMotor.set(FRONT_RIGHT_CHANNEL);
    	
    	frontRightMotor = new CANTalon(FRONT_RIGHT_CHANNEL);
    	frontRightMotor.changeControlMode(TalonControlMode.Speed);
    	frontRightMotor.setPID(P_DEFAULT, I_DEFAULT, D_DEFAULT);
    	
    	rearLeftMotor = new CANTalon(REAR_LEFT_CHANNEL);
    	rearLeftMotor.changeControlMode(TalonControlMode.Follower);
    	rearLeftMotor.set(FRONT_LEFT_CHANNEL);
    	
    	midLeftMotor = new CANTalon(MID_LEFT_CHANNEL);
    	midLeftMotor.changeControlMode(TalonControlMode.Follower);
    	midLeftMotor.set(FRONT_LEFT_CHANNEL);
    	
		frontLeftMotor = new CANTalon(FRONT_LEFT_CHANNEL);
    	frontLeftMotor.changeControlMode(TalonControlMode.Speed);
    	frontLeftMotor.setPID(P_DEFAULT, I_DEFAULT, D_DEFAULT);
    	
    	rightJoystick = new Joystick(JOYSTICK_RIGHT_CHANNEL);
    	leftJoystick = new Joystick(JOYSTICK_LEFT_CHANNEL);
        	
    	robotDrive = new DriveThreeMotorTankDrive(rearRightMotor, midRightMotor, frontRightMotor, rearLeftMotor, midLeftMotor, frontLeftMotor);
      	
    	navx = new AHRS(SerialPort.Port.kMXP);
      	testUnwrapper = new SensorUnwrapper(180.0, new SensorNavxAngle(navx));
      	
        int session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
      	camera = new ImageGrabber(session, true);
      	angleToTargetGrabber = new SensorTargetAngleFromImage(CAMERA_RESOLUTION_X, CAMERA_RESOLUTION_Y, CAMERA_VIEW_ANGLE, TARGET_ASPECT_RATIO, TARGET_HUE_RANGE, TARGET_SATURATION_RANGE, TARGET_VALUE_RANGE, PARTICLE_AREA_MINIMUM);
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
    	frontRightMotor.setPID(SmartDashboard.getNumber(DRIVE_KEY_P), SmartDashboard.getNumber(DRIVE_KEY_I), SmartDashboard.getNumber(DRIVE_KEY_D));
    	frontLeftMotor.setPID(SmartDashboard.getNumber(DRIVE_KEY_P), SmartDashboard.getNumber(DRIVE_KEY_I), SmartDashboard.getNumber(DRIVE_KEY_D));
    }
    public void teleopPeriodic() 
    {
    	
        double X = -rightJoystick.getRawAxis(0);
        double Y = -rightJoystick.getRawAxis(1);
        
        robotDrive.drive(X, Y);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() 
    {
    
    }
    
}
