package Environment;

import java.util.ArrayDeque;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Road {
	ArrayDeque<Car> cars;
	public Node startNode, endNode;
	float angle;
	float speedLimit;
	float length;
	
	public Road(Node nStart, Node nEnd, float limit){
		startNode = nStart;
		endNode = nEnd;
		speedLimit = limit;
		length = (float) Math.sqrt((nStart.posX - nEnd.posX)*(nStart.posX - nEnd.posX) + (nStart.posY - nEnd.posY)*(nStart.posY - nEnd.posY));
		angle = (float) Math.atan2(nStart.posY - nEnd.posY, nStart.posX - nEnd.posX);
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
	
	public boolean canAcceptCars(){
		throw new NotImplementedException();
	}
}
