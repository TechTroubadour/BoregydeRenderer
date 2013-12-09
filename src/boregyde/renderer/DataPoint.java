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
			   		 double y, double z, double x, 			// Coordinates
			   		 double verticalSection, 
			   		 double closureDDFT, 
			   		 double closureDDDeg, 
			   		 double doglegSeverity,					// Measurement
			   		 DataPoint previousPoint){	
		this.measuredDepth = measuredDepth;
		this.inclAngle = inclAngle;
		this.driftDirection = driftDirection;
		this.x = x; this.y = -y; this.z = z;
		this.verticalSection = verticalSection;
		this.closureDDFT = closureDDFT;
		this.closureDDDeg = closureDDDeg;
		this.doglegSeverity = doglegSeverity;
		this.previousPoint = previousPoint;
		System.out.println("("+x+","+y+","+z+")");
	}
	public DataPoint(double x, double y, double z, 			// Coordinates
			   		 double doglegSeverity,					// Measurement
			   		 DataPoint previousPoint){	
		this.measuredDepth = 0;
		this.inclAngle = 0;
		this.driftDirection = 0;
		this.x = x; this.y = -y; this.z = z;
		this.verticalSection = 0;
		this.closureDDFT = 0;
		this.closureDDDeg = 0;
		this.doglegSeverity = doglegSeverity;
		this.previousPoint = previousPoint;
		System.out.println("("+x+","+y+","+z+")");
	}
	public void draw(){		
		double ds = doglegSeverity*5;
		if(previousPoint==null){
			glColor3d(1,1,1);
			glBegin(GL_QUADS);
				glVertex3d(x-ds,y,z-ds);
				glVertex3d(x+ds,y,z-ds);
				glVertex3d(x+ds,y,z+ds);
				glVertex3d(x-ds,y,z+ds);
			glEnd();
		}else{
			double[] coords = previousPoint.getCoords();
			double ds2 = previousPoint.getDogleg()*5;
			glBegin(GL_QUADS);
				//Front
				glColor3d(1,0,0);
				glVertex3d(x-ds,y,z+ds);
				glVertex3d(coords[0]-ds2,coords[1],coords[2]+ds2);
				glVertex3d(coords[0]+ds2,coords[1],coords[2]+ds2);
				glVertex3d(x+ds,y,z+ds);
				//Back
				glColor3d(0,1,0);
				glVertex3d(x+ds,y,z-ds);
				glVertex3d(coords[0]+ds2,coords[1],coords[2]-ds2);
				glVertex3d(coords[0]-ds2,coords[1],coords[2]-ds2);
				glVertex3d(x-ds,y,z-ds);
				//Left
				glColor3d(0,0,1);
				glVertex3d(x-ds,y,z-ds);
				glVertex3d(coords[0]-ds2,coords[1],coords[2]-ds2);
				glVertex3d(coords[0]-ds2,coords[1],coords[2]+ds2);
				glVertex3d(x-ds,y,z+ds);
				//Right
				glColor3d(1,0,1);
				glVertex3d(x+ds,y,z+ds);
				glVertex3d(coords[0]+ds2,coords[1],coords[2]+ds2);
				glVertex3d(coords[0]+ds2,coords[1],coords[2]-ds2);
				glVertex3d(x+ds,y,z-ds);
			glEnd();
		}
		glColor4d(0,doglegSeverity,1/(doglegSeverity*5),0.1);
		int cakeSize = 1000;
		glBegin(GL_QUADS);
			glVertex3d(x+cakeSize,y,z-cakeSize);
			glVertex3d(x-cakeSize,y,z-cakeSize);
			glVertex3d(x-cakeSize,y,z+cakeSize);
			glVertex3d(x+cakeSize,y,z+cakeSize);
		glEnd();
	}
	public double[] getCoords(){
		double[] array = {x,y,z};
		return array;
	}
	public double getDogleg(){
		return doglegSeverity;
	}
}
