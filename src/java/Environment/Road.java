package Environment;

import java.util.ArrayDeque;

import jason.stdlib.min;
import sun.management.snmp.util.MibLogger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Road {
	int id;
	public ArrayDeque<Car> cars;
	public Node startNode, endNode;
	float angle;
	int speedLimit;
	int length;
	
	public Road(Node nStart, Node nEnd, int limit, int id){
		startNode = nStart;
		endNode = nEnd;
		nStart.addRoad(this);
		speedLimit = limit;
		length = (int)Math.sqrt((nStart.posX - nEnd.posX)*(nStart.posX - nEnd.posX) + (nStart.posY - nEnd.posY)*(nStart.posY - nEnd.posY));
		angle = (float) Math.atan2(nStart.posY - nEnd.posY, nStart.posX - nEnd.posX);
		this.id = id;
		cars = new ArrayDeque<Car>();
	}
	
	public void moveCars(float deltaTime){
		float minSpeed = -1;
		for(Car car : cars){
			if(minSpeed < 0){
				minSpeed = Math.min(speedLimit, car.speed);
				//TODO: uncomment if node can only hold one car
				//if(endNode.hasCar() && (car.roadPos + minSpeed * deltaTime) >= length)
				//	minSpeed = (length - car.roadPos)/(float)deltaTime;
			}
			if(car.speed < minSpeed)
				minSpeed = car.speed;
			car.roadPos += minSpeed * deltaTime;
		}
		while(cars.getFirst().roadPos >= length){
			endNode.addCar(cars.removeFirst());
		}
	}
	
	public void addCar(Car car){
		car.placeID = id;
		cars.add(car);
		car.roadPos = 0;
	}
	
	public boolean canAcceptCars(){
		return cars.getLast().roadPos > Car.length;
	}
	
	public float getLength() {
		return length;
	}
}
