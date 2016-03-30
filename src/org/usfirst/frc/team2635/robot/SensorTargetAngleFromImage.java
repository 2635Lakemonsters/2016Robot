package org.usfirst.frc.team2635.robot;

import java.awt.Point;
import java.util.Comparator;
import java.util.Vector;


import com.lakemonsters2635.sensor.interfaces.BaseSensor;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.ColorMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SensorTargetAngleFromImage extends BaseSensor <NIVision.PointDouble>
{

	double CAMERA_RESOLUTION_X;
	double CAMERA_RESOLUTION_Y;
	double VIEW_ANGLE;
	double ASPECT_RATIO;
	public NIVision.Range HUE_RANGE; //= new NIVision.Range(0, 5);	//Default hue range for yellow tote
	public NIVision.Range SAT_RANGE; //= new NIVision.Range(0, 10);	//Default saturation range for yellow tote
	public NIVision.Range VAL_RANGE; //= new NIVision.Range(250, 255);	//Default value range for yellow tote
	Double AREA_MINIMUM;
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	Image binaryImage;

	public SensorTargetAngleFromImage(double CAMERA_RESOLUTION_X, double CAMERA_RESOLUTION_Y, double VIEW_ANGLE, double ASPECT_RATIO,
			NIVision.Range hueRange, NIVision.Range saturationRange, NIVision.Range valueRange, double particleAreaMinimum)
	{
		super();
		HUE_RANGE = hueRange;
		SAT_RANGE = saturationRange;
		VAL_RANGE = valueRange;
		AREA_MINIMUM = particleAreaMinimum;
		this.CAMERA_RESOLUTION_X = CAMERA_RESOLUTION_X;
		this.CAMERA_RESOLUTION_Y = CAMERA_RESOLUTION_Y;
		this.VIEW_ANGLE = VIEW_ANGLE;
		this.ASPECT_RATIO = ASPECT_RATIO;
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 1.0, 0, 0);
		binaryImage = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);

	}
	public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
		double Area;
		double AreaByImageArea;
		double BoundingRectLeft;
		double BoundingRectTop;
		double BoundingRectWidth;
		double BoundingRectHeight;

		public int compareTo(ParticleReport r)
		{
			return (int)(r.AreaByImageArea - this.AreaByImageArea);
		}

		public int compare(ParticleReport r1, ParticleReport r2)
		{
			return (int)(r1.AreaByImageArea - r2.AreaByImageArea);
		}
	};

	@Override
	public NIVision.PointDouble sense(Object objImage) 
	{
		NIVision.Image image = null;
		try 
		{
			image = (NIVision.Image)objImage;
		}
		catch(ClassCastException ex)
		{
			ex.printStackTrace();
		}
		//Look at the color frame for colors that fit the range. Colors that fit the range will be transposed as a 1 to the binary frame.
		NIVision.imaqColorThreshold(binaryImage, image, 255, ColorMode.HSV, HUE_RANGE, SAT_RANGE, VAL_RANGE);
		//Filter out particles that are too small
		NIVision.imaqParticleFilter4(binaryImage, binaryImage, criteria, filterOptions, null);

		int numParticles = NIVision.imaqCountParticles(binaryImage, 1);        
		if(numParticles > 0)
		{
			ParticleReport bestParticle = null;
			//Vector<ParticleReport> particles = new Vector<ParticleReport>();
			for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
			{
				ParticleReport par = new ParticleReport();
				par.Area = NIVision.imaqMeasureParticle(binaryImage, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
				par.AreaByImageArea = NIVision.imaqMeasureParticle(binaryImage, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
				par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryImage, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				par.BoundingRectLeft = NIVision.imaqMeasureParticle(binaryImage, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				par.BoundingRectHeight = NIVision.imaqMeasureParticle(binaryImage, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
				par.BoundingRectWidth = NIVision.imaqMeasureParticle(binaryImage, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
				if(bestParticle == null
						|| Math.abs(par.BoundingRectHeight / par.BoundingRectWidth - ASPECT_RATIO) < Math.abs(bestParticle.BoundingRectHeight / bestParticle.BoundingRectWidth) - ASPECT_RATIO
						&& par.BoundingRectTop <= CAMERA_RESOLUTION_Y * 0.70
						)
				{
					bestParticle = par;
				}
				//particles.add(par);

			}
			//particles.sort(null);
			double bestParticleMidpointX = bestParticle.BoundingRectLeft + bestParticle.BoundingRectWidth/2.0;
			double bestParticleMidpointY = bestParticle.BoundingRectTop;
			double bestParticleMidpointXAimingCoordnates = pixelCoordnateToAimingCoordnate(bestParticleMidpointX, CAMERA_RESOLUTION_X);
			double bestParticleMidpointYAimingCoordnates = pixelCoordnateToAimingCoordnate(bestParticleMidpointY, CAMERA_RESOLUTION_Y);
			return new NIVision.PointDouble(aimingCoordnateToAngle(bestParticleMidpointXAimingCoordnates, VIEW_ANGLE), bestParticleMidpointY);

		}
		else
		{
			return new NIVision.PointDouble(0.0, 0.0);
		}
	}
	public double pixelCoordnateToAimingCoordnate(double pixelCoordnate, double resolution)
	{

		return (pixelCoordnate - resolution / 2.0) / (resolution / 2.0);
	}
	public double aimingCoordnateToAngle(double aimingCoordnate, double viewingAngle)
	{
		return aimingCoordnate * viewingAngle/2.0;
	}
	@Override
	public void setPIDSourceType(PIDSourceType pidSource)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public PIDSourceType getPIDSourceType()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public double pidGet()
	{
		// TODO Auto-generated method stub
		return 0;
	}


}
