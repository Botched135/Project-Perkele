package example;

public class GameObject {

	//VARIABLE DECLARATION
	protected float xPos;
	protected float yPos;
	protected boolean render;
	protected float hitboxX;
	protected float hitboxY;

	//CONTRUCTERS
	GameObject() {
	 
		xPos = Window.WIDTH/2;
		yPos = Window.HEIGHT/2; 
		render = false;
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
	
	float distToObj(GameObject _obj){
		
		 float distToObj;
		//Pythagoras to calculate distance between objects
		distToObj = (float)(Math.sqrt(Math.pow((this.xPos-_obj.getXPos()),2) + Math.pow((this.yPos-_obj.getYPos()),2)));
		return distToObj;
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
		if(this.distToObj(_obj)<this.hitboxX+_obj.hitboxX){
			return true;
		}else
			return false;
	}
}
