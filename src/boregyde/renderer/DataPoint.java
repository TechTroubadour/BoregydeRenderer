package boregyde.renderer;
import static org.lwjgl.opengl.GL11.*;

public class DataPoint {
	private double measuredDepth, 
				   inclAngle, 
				   driftDirection, 
				   x, y, z, 			// Coordinates
				   verticalSection, 
				   closureDDFT, 
				   closureDDDeg, 
				   doglegSeverity;		// Measurement
	private DataPoint previousPoint;
	public DataPoint(double measuredDepth,
					 double inclAngle, 
			   		 double driftDirection, 
			   		 double x, double y, double z, 			// Coordinates
			   		 double verticalSection, 
			   		 double closureDDFT, 
			   		 double closureDDDeg, 
			   		 double doglegSeverity,					// Measurement
			   		 DataPoint previousPoint){	
		this.measuredDepth = measuredDepth;
		this.inclAngle = inclAngle;
		this.driftDirection = driftDirection;
		this.x = x; this.y = y; this.z = z;
		this.verticalSection = verticalSection;
		this.closureDDFT = closureDDFT;
		this.closureDDDeg = closureDDDeg;
		this.doglegSeverity = doglegSeverity;
		this.previousPoint = previousPoint;
	}
	public DataPoint(double x, double y, double z, 			// Coordinates
			   		 double doglegSeverity,					// Measurement
			   		 DataPoint previousPoint){	
		this.measuredDepth = 0;
		this.inclAngle = 0;
		this.driftDirection = 0;
		this.x = x; this.y = y; this.z = z;
		this.verticalSection = 0;
		this.closureDDFT = 0;
		this.closureDDDeg = 0;
		this.doglegSeverity = doglegSeverity;
		this.previousPoint = previousPoint;
	}
	public void draw(){
		if(previousPoint==null){
			glColor3d(1,1,1);
			glBegin(GL_QUADS);
				glVertex3d(x-doglegSeverity,y,z-doglegSeverity);
				glVertex3d(x+doglegSeverity,y,z-doglegSeverity);
				glVertex3d(x+doglegSeverity,y,z+doglegSeverity);
				glVertex3d(x-doglegSeverity,y,z+doglegSeverity);
			glEnd();
		}else{
			System.out.println("<Needs Implementaion");
		}
	}
}
