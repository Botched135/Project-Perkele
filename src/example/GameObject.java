package example;

import org.newdawn.slick.geom.*;

public class GameObject {

	//VARIABLE DECLARATION
	
	protected Vector2f vector;
	protected boolean render;
	protected float hitboxX;
	protected float hitboxY;
	protected byte ID; //PLAYER == 1:	ENEMY == 2:		ARMOR == 3:		WEAPON == 4:
	public int LootLevel=1;

	//CONTRUCTERS
	GameObject() {

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

	boolean getRenderState(){
		return render;
		
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
