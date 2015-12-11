package Environment;

public class Car {
	
	public String name;
	public int speed;
	public int roadPos;
	public boolean onNode = false;
	public boolean started = false, finished = false;
	public int startNode, endNode;
	public int placeID;
	public boolean hasRoute = false;
	public static int length = 20;
	
	public String toString(){
		return "[" + name + "]" + "\n\tspeed: " + speed + "\n\troadPos: " + roadPos + "\n\tplaceID: " + placeID; 
	}
	
}
