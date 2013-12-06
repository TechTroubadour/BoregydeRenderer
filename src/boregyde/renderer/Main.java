package boregyde.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;

import king.jaiden.RATL.MyWindow;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.TextureLoader;

public class Main extends MyWindow {
	public double[][] mountains;
	
	public Main(int w, int h, int fov, String title) {
		super(w, h, fov, title);
	}

	public static void main(String[] args){
		new Main(1600,900,70,"Boregyde Data Renderer");
	}
	
	@Override
	public void init() {
		mountains  = new double[100][100];
		for(int row = 0; row < 100; row++){
			for(int col = 0; col < 100; col++){
				mountains[row][col] = Math.random()*10;
			}
		}
	}

	@Override
	public void input() {
		// TODO Auto-generated method stub
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void draw() {
		super.draw();
		glLoadIdentity();
		glTranslated(-10,-10,-100);
		glColor3d(0.7,0.7,0.7);
		for(int i = 0; i < 99; i++){
			for(int n = 0; n<99;n++){
				glBegin(GL_TRIANGLES);
					glVertex3d(i,n,mountains[i][n]);
					glVertex3d(i+1,n,mountains[i][n]);
					glVertex3d(i+1,n+1,mountains[i][n]);
				glEnd();
			}
		}
	}

}
