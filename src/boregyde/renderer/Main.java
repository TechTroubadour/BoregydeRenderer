package boregyde.renderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.input.Keyboard.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import king.jaiden.RATL.MyWindow;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.TextureLoader;

public class Main extends MyWindow {
	public double[][] mountains;
	public double[][][] colors;
	public double rx = 0, ry = 0, zoom = 0, dx = 0, dy = 0, dz = 0;
	ArrayList<DataPoint> dp;
	
	public Main(int w, int h, int fov, String title) {
		super(w, h, fov, title);
	}

	public static void main(String[] args){
		new Main(1600,900,30,"Boregyde Data Renderer");
	}
	
	@Override
	public void init() {
		glDisable(GL_CULL_FACE);
//		glDisable(GL_DEPTH_TEST);
		glFrontFace(GL_CW);
		mountains  = new double[100][100];
		colors  = new double[100][100][3];
		dp = new ArrayList<DataPoint>();
		
		rx = 4;
		ry = 21;
		zoom = 0;
		dx = 6.8;
		dy = -10;
		dz = -19.1;
		
		for(int row = 0; row < mountains.length; row++){
			for(int col = 0; col < mountains[row].length; col++){
				int r = row - 50;
				int c = col - 50;
				mountains[row][col] = Math.random()+Math.pow(1.05,Math.sqrt(Math.pow(r,2)+Math.pow(c,2)));
				
				double colorSeed = r()/3+0.3333333;
				
				colors[row][col][0] = colorSeed;
				colors[row][col][1] = colorSeed/1.94736842105263;
				colors[row][col][2] = 0;
			}
		}
	}

	@Override
	public void input() {
		if(Mouse.next()){
			if(Mouse.isButtonDown(0)){
				System.out.println("rx:"+rx);
				System.out.println("ry:"+ry);
				System.out.println("zoom:"+zoom);
				System.out.println("dx:"+dx);
				System.out.println("dy:"+dy);
				System.out.println("dz:"+dz);
			}
		}
		if(Mouse.isButtonDown(1)){
			ry += Mouse.getDX();
			rx -= Mouse.getDY();
			if(rx<-90)
				rx=-90;
			else if(rx>90)
				rx=90;
		}
		zoom -= Mouse.getDWheel()/100;
		if(Keyboard.isKeyDown(KEY_W)){
			dz+=0.1;
		}if(Keyboard.isKeyDown(KEY_S)){
			dz-=0.1;
		}if(Keyboard.isKeyDown(KEY_A)){
			dx+=0.1;
		}if(Keyboard.isKeyDown(KEY_D)){
			dx-=0.1;
		}if(Keyboard.isKeyDown(KEY_Q)){
			dy-=0.1;
		}if(Keyboard.isKeyDown(KEY_E)){
			dy+=0.1;
		}
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void draw() {
		super.draw();
		glLoadIdentity();
		glTranslated(0,0,-zoom);
		glRotated(rx,1,0,0);
		glRotated(ry,0,1,0);
		glTranslated(-50,0,-50);
		glTranslated(dx,dy,dz);
		drawStructures();
	}
	
	/**
	 * Draws the permanent structures (like mountains, grids, drills, etc)
	 */
	public void drawStructures(){
		// Mountains
		for(int i = 0; i < mountains.length-1; i++){
			for(int n = 0; n<mountains[i].length-1;n++){
				glBegin(GL_TRIANGLES);
					glColor3d(colors[i][n][0],colors[i][n][1],colors[i][n][2]);
					glVertex3d(i,mountains[i][n],n);
					glColor3d(colors[i+1][n][0],colors[i+1][n][1],colors[i+1][n][2]);
					glVertex3d(i+1,mountains[i+1][n],n);
					glColor3d(colors[i+1][n+1][0],colors[i+1][n+1][1],colors[i+1][n+1][2]);
					glVertex3d(i+1,mountains[i+1][n+1],n+1);
				glEnd();
				glBegin(GL_TRIANGLES);
					glColor3d(colors[i+1][n+1][0],colors[i+1][n+1][1],colors[i+1][n+1][2]);
					glVertex3d(i+1,mountains[i+1][n+1],n+1);
					glColor3d(colors[i][n+1][0],colors[i][n+1][1],colors[i][n+1][2]);
					glVertex3d(i,mountains[i][n+1],n+1);
					glColor3d(colors[i][n][0],colors[i][n][1],colors[i][n][2]);
					glVertex3d(i,mountains[i][n],n);
				glEnd();
			}
		}
		
		// Drill
		glPushMatrix();
			glTranslated(50,0,50);
			glColor3d(1,1,1);
			// Y-Axis
//			glBegin(GL_LINES);
//				glVertex3d(0,0,0);
//				glVertex3d(0,100,0);
//			glEnd();
			glBegin(GL_TRIANGLES);
				// Front-right leg
				glColor3d(.5,.5,.5);
				glVertex3d(5,0,5);
				glColor3d(.1,.1,.1);
				glVertex3d(5,5,5);
				glVertex3d(5,5,0);
				glColor3d(.5,.5,.5);
				glVertex3d(5,0,5);
				glColor3d(.1,.1,.1);
				glVertex3d(0,5,5);
				glVertex3d(5,5,5);
				// Front-left leg
				glColor3d(.5,.5,.5);
				glVertex3d(-5,0,5);
				glColor3d(.1,.1,.1);
				glVertex3d(-5,5,5);
				glVertex3d(0,5,5);
				glColor3d(.5,.5,.5);
				glVertex3d(-5,0,5);
				glColor3d(.1,.1,.1);
				glVertex3d(-5,5,0);
				glVertex3d(-5,5,5);
				// Back-left leg
				glColor3d(.5,.5,.5);
				glVertex3d(-5,0,-5);
				glColor3d(.1,.1,.1);
				glVertex3d(-5,5,-5);
				glVertex3d(-5,5,0);
				glColor3d(.5,.5,.5);
				glVertex3d(-5,0,-5);
				glColor3d(.1,.1,.1);
				glVertex3d(0,5,-5);
				glVertex3d(-5,5,-5);
				// Back-right leg
				glColor3d(.5,.5,.5);
				glVertex3d(5,0,-5);
				glColor3d(.1,.1,.1);
				glVertex3d(5,5,-5);
				glVertex3d(0,5,-5);
				glColor3d(.5,.5,.5);
				glVertex3d(5,0,-5);
				glColor3d(.1,.1,.1);
				glVertex3d(5,5,0);
				glVertex3d(5,5,-5);
				// Back cover
				glColor3d(.5,.5,.5);
				glVertex3d(-5,5,-5);
				glVertex3d(5,5,-5);
				glColor3d(.1,.1,.1);
				glVertex3d(0,7,0);
				// Right cover
				glColor3d(.5,.5,.5);
				glVertex3d(5,5,-5);
				glVertex3d(5,5,5);
				glColor3d(.1,.1,.1);
				glVertex3d(0,7,0);
				// Front cover
				glColor3d(.5,.5,.5);
				glVertex3d(5,5,5);
				glVertex3d(-5,5,5);
				glColor3d(.1,.1,.1);
				glVertex3d(0,7,0);
				// Left cover
				glColor3d(.5,.5,.5);
				glVertex3d(-5,5,5);
				glVertex3d(-5,5,-5);
				glColor3d(.1,.1,.1);
				glVertex3d(0,7,0);
				// Back tower
				glColor3d(.5,.5,.5);
				glVertex3d(-3,3,-3);
				glVertex3d(3,3,-3);
				glColor3d(.1,.1,.1);
				glVertex3d(0,30,0);
				// Right tower
				glColor3d(.5,.5,.5);
				glVertex3d(3,3,-3);
				glVertex3d(3,3,3);
				glColor3d(.1,.1,.1);
				glVertex3d(0,30,0);
				// Front tower
				glColor3d(.5,.5,.5);
				glVertex3d(3,3,3);
				glVertex3d(-3,3,3);
				glColor3d(.1,.1,.1);
				glVertex3d(0,30,0);
				// Left tower
				glColor3d(.5,.5,.5);
				glVertex3d(-3,3,3);
				glVertex3d(-3,3,-3);
				glColor3d(.1,.1,.1);
				glVertex3d(0,30,0);
			glEnd();
		glPopMatrix();
	}
	
	public double r(){
		return Math.random();
	}

}
