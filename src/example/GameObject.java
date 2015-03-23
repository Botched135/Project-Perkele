package example;

import org.newdawn.slick.geom.*;

public class GameObject {

	//VARIABLE DECLARATION
	protected float xPos;
	protected float yPos;
	protected Vector2f vector;
	protected boolean render;
	protected float hitboxX;
	protected float hitboxY;

	//CONTRUCTERS
	GameObject() {
	 
		xPos = Window.WIDTH/2;
		yPos = Window.HEIGHT/2; 
		vector = new Vector2f(Window.WIDTH/2, Window.HEIGHT/2);
		render = false;
	}
 
	GameObject(Vector2f _vector) {
		 
		vector = _vector; 
		render = false;
	 
	}
	
	GameObject(Vector2f _vector, boolean _render) {
		 
		vector = _vector; 
		render = _render;
		
	 
	}
	
	GameObject(float _xPos, float _yPos) {
	 
		xPos = _xPos;
		yPos = _yPos; 
		render = false;
	 
	}
 
	GameObject(float _xPos, float _yPos, boolean _render) {
	 
		xPos = _xPos;
		yPos = _yPos; 
	 	render = _render;
	}
	
	public float getXPos(){
		return xPos;
	}
 
	public float getYPos(){
		return yPos;
	}

	boolean getRenderState(){
		return render;
	}
 
	public void setXPos(float _xPos){
		//Insert if-statement restrictions for the setting of the xPos, if needed.
		xPos = _xPos;
	}
 
	public void setYPos(float _yPos){
		//Insert if-statement restrictions for the setting of the yPos, if needed.
		yPos = _yPos;
	}
 
	public void setRenderstate(boolean _render){
		render = _render;
	}
	public boolean isColliding(GameObject _obj){
		if(this.vector.distance(_obj.vector)<this.hitboxX+_obj.hitboxX){
			return true;
		}else
			return false;
	}
}
