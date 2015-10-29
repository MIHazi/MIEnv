package Environment;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Node {

	ArrayDeque<Car> cars;
	ArrayList<Road> roadsFromHere;
	
	float posX, posY;
	
	public Node(float x, float y){
		posX = x;
		posY = y;
	}
	
	public void addCar(Car car){
		cars.addFirst(car);		
	}
	
	
}
