package example;

public class GameObject {

	private float xPos;
	private float yPos;
	private boolean render;

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
 
 float getxPos(){
	 return xPos;
 }
 
 float getyPos(){
	 return yPos;
 }

 boolean getRenderState(){
	 return render;
 }
 
 void setxPos(float _xPos){
	 //Insert if-statement restrictions for the setting of the xPos, if needed.
	 xPos = _xPos;
 }
 
 void setyPos(float _yPos){
	 //Insert if-statement restrictions for the setting of the yPos, if needed.
	 yPos = _yPos;
 }
 
 void setRenderstate(boolean _render){
	 render = _render;
 }
}