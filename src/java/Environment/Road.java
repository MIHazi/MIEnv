package Environment;

import java.util.ArrayDeque;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Road {
	int id;
	public ArrayDeque<Car> cars;
	public Node startNode, endNode;
	float angle;
	float speedLimit;
	float length;
	
	public Road(Node nStart, Node nEnd, float limit, int id){
		startNode = nStart;
		endNode = nEnd;
		nStart.addRoad(this);
		speedLimit = limit;
		length = (float) Math.sqrt((nStart.posX - nEnd.posX)*(nStart.posX - nEnd.posX) + (nStart.posY - nEnd.posY)*(nStart.posY - nEnd.posY));
		angle = (float) Math.atan2(nStart.posY - nEnd.posY, nStart.posX - nEnd.posX);
		this.id = id;
	}
	
	public void moveCars(float deltaTime){
		float minSpeed = -1;
		int nFinished = 0;
		for(Car car : cars){
			if(minSpeed < 0)
				minSpeed = Math.min(speedLimit, car.speed);
			if(car.speed < minSpeed)
				minSpeed = car.speed * deltaTime;
			car.roadPos += minSpeed;
			if(car.roadPos >= length){
				endNode.addCar(car);
				nFinished++;
			}
		}
		for(;nFinished > 0; nFinished--){
			cars.removeFirst();
		}
	}
	
	public void addCar(Car car){
		car.placeID = id;
		cars.add(car);
	}
	
	public boolean canAcceptCars(){
		throw new NotImplementedException();
	}
	
	public float getLength() {
		return length;
	}
}
