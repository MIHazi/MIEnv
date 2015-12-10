package Environment;

import java.util.ArrayList;

import jason.asSyntax.ASSyntax;
import jason.asSyntax.ListTerm;
import jason.asSyntax.Structure;
import jason.asSyntax.parser.ParseException;
import jason.environment.Environment;
import jason.functions.Sqrt;
import view.TopologyView;

public class TopologyEnvironment extends Environment{
	
	ConfigParser config;
	TopologyModel model;
	TopologyView view;
	StepTimer timer;
	int step = 0;
	
	public TopologyEnvironment() {
		super(2);
	}
	
	@Override
	public void init(String[] args) {
		if(args.length < 2)
			throw new IllegalArgumentException("Config file, or timer interval not specified");
		config = new ConfigParser(args[0]);
		model = new TopologyModel(config);
		view = model.getView();
		addInitialPercepts();
		timer = new StepTimer(new UpdateTimer(), Integer.parseInt(args[1]));
	}
	
	public void addInitialPercepts(){
		try{
			for(int i = 0; i < model.nodes.size(); i++){
				Node node = model.nodes.get(i);
				addPercept(ASSyntax.createLiteral("node", ASSyntax.parseList("[" + i + "," + node.posX + "," + node.posY + "]")));
			}
			for(int i = 0; i < model.roads.size(); i++){
				Road road = model.roads.get(i);
				addPercept(ASSyntax.createLiteral("road", ASSyntax.parseList("[" + i + "," + road.startNode.id + "," + road.endNode.id + "," + road.speedLimit + "," + road.length + "]")));
			}
			for(int i = 0; i<model.roads.size(); i++){
				Node node1 = model.nodes.get(i);
				for(int j = 0; j<model.nodes.size();j++){
					Node node2 = model.nodes.get(j);
					int distance = (int)Math.sqrt((double)(node2.posX-node1.posX)*(node2.posX-node1.posX)+(node2.posY-node1.posY)*(node2.posY-node1.posY));
					addPercept(ASSyntax.createLiteral("heuristic", ASSyntax.parseList("[" + i + "," + j + "," + distance + "]")));
				}
			}
			/*ListTerm nodeList = ASSyntax.createList();
			for(Integer i = 0; i < model.nodes.size(); i++){
					Node node = model.nodes.get(i);
					nodeList.add(ASSyntax.parseList("[" + i + "," + node.posX + "," + node.posY + "]"));
			}
			addPercept(ASSyntax.createLiteral("nodes", nodeList));
			ListTerm roadList = ASSyntax.createList();
			for(Integer i = 0; i < model.roads.size(); i++){
				Road road = model.roads.get(i);
				roadList.add(ASSyntax.parseList("[" + i + "," + road.startNode.id + "," + road.endNode.id + "," + road.speedLimit + "," + road.length + "]"));
			}
			addPercept(ASSyntax.createLiteral("roads", roadList));*/
			addIndividualPercepts();
		}
		catch(ParseException e){
			e.printStackTrace();
		}
	}
	
	public void updatePercepts(){
		clearAllPercepts();
		addIndividualPercepts();
		for(Node node : model.nodes){
			if(node.hasCar())
				addPercept(node.peekLast().name, ASSyntax.createLiteral("has_action"));
		}
	}
	
	void addIndividualPercepts(){
		for(ArrayList<Car> carList : model.cars){
			for(Car car : carList){
				addPercept(car.name, ASSyntax.createLiteral("start_pos", ASSyntax.createNumber(car.startNode)));
				addPercept(car.name, ASSyntax.createLiteral("end_pos", ASSyntax.createNumber(car.endNode)));
			}
		}
	}
	
	@Override
	public void stop() {
		timer.stop();
	}
	
	@Override
	public boolean executeAction(String agName, Structure act) {
		boolean result = false;
		if(act.getFunctor().equals("turn")){
			String term = act.getTerm(0).toString();
			int roadID = Integer.parseInt(term);
			result = model.moveCarToRoad(agName, roadID);
		}
		else if(act.getFunctor().equals("has_route")){
			model.getCarByName(agName).hasRoute = true;
			if(model.allCarsHaveRoute()){
				System.out.println("All finished");
				timer.start();
			}
			result = true;
		}
		else if(act.getFunctor().equals("exit_sim")){
			result = model.exitCar(agName);
			if(model.allCarsFinished()){
				stop();
			}
		}
		return result;
	}
	
	class UpdateTimer implements TimerCallback{

		public void callback(int deltaTime) {
			System.out.println("[ENV] : Updating model");
			model.update(deltaTime/25f, step);
			System.out.println("[ENV] : Updating percepts");
			updatePercepts();
			System.out.println("[ENV] : Step " + step + " started");
			step++;
		}
		
	}
}
