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
		int lastPos = length + 2*Car.length;
		float minSpeed;
		for(Car car : cars){
			minSpeed = Math.min(car.speed, speedLimit);
			if(car.equals(cars.getFirst()) && endNode.hasCar() && (car.roadPos + minSpeed * deltaTime) >= length){
				car.roadPos = (length - Car.length);
			}else if(car.roadPos + minSpeed * deltaTime >= (lastPos - Car.length)){
				car.roadPos = (lastPos - Car.length);
			}else{
				car.roadPos += minSpeed * deltaTime;
			}
			lastPos = car.roadPos;
		}
		while(!cars.isEmpty() && cars.getFirst().roadPos >= length){
			endNode.addCar(cars.removeFirst());
		}
	}
	
	public void addCar(Car car){
		car.placeID = id;
		cars.add(car);
		car.roadPos = 0;
	}
	
	public boolean canAcceptCars(){
		if(cars.isEmpty())
			return true;
		return cars.getLast().roadPos > Car.length;
	}
	
	public float getLength() {
		return length;
	}
}
