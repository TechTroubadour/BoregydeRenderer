package boregyde.renderer;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import static org.lwjgl.opengl.GL11.*;

import king.jaiden.RATL.AlphaNumeric;

public class MyButton {
	Rectangle bb;
	boolean hover = false;
	boolean down = false;
	AlphaNumeric an;
	String message;
	Main m;
	int id = 0;
	int value = 0;
	
	public MyButton(int id, int val, String message, int x, int y, int width, int height, AlphaNumeric a, Main m)	{
		bb = new Rectangle(x,y,width,height);
		an = a;
		this.message = message;
		this.m = m;
		this.id = id;
		value = val;
	}
	public void draw(){
		if(hover){
			glColor3f(0,1,0);
		}else{
			glColor3f(1,1,1);
		}
		glEnable(GL_TEXTURE_2D);
		an.write(message,bb.x,bb.y,0,bb.width/message.length(),bb.height);
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_LINE_STRIP);
			glVertex2d(bb.x-1,bb.y-1);
			glVertex2d(bb.x-1,bb.y+bb.height+1);
			glVertex2d(bb.x+bb.width+1,bb.y+bb.height+1);
			glVertex2d(bb.x+bb.width+1,bb.y-1);
			glVertex2d(bb.x-1,bb.y-1);
		glEnd();
	}
	public Rectangle getRectangle(){
		return bb;
	}
}
