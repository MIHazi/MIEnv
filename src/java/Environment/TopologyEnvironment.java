package Environment;

import jason.asSyntax.ASSyntax;
import jason.asSyntax.ListTerm;
import jason.asSyntax.Structure;
import jason.asSyntax.parser.ParseException;
import jason.environment.Environment;
import jason.environment.TimeSteppedEnvironment;

public class TopologyEnvironment extends Environment{
	
	ConfigParser config;
	TopologyModel model;
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
		addInitialPercepts();
		timer = new StepTimer(new UpdateTimer(), Integer.parseInt(args[1]));
	}
	
	public void addInitialPercepts(){
		try{
			ListTerm nodeList = ASSyntax.createList();
			for(Integer i = 0; i < model.nodes.size(); i++){
					nodeList.add(ASSyntax.createNumber(i));
			}
			addPercept(ASSyntax.createLiteral("nodes", nodeList));
			ListTerm roadList = ASSyntax.createList();
			for(Integer i = 0; i < model.roads.size(); i++){
				Road road = model.roads.get(i);
				roadList.add(ASSyntax.parseTerm("[" + i + "," + road.startNode.id + "," + road.endNode.id + "," + road.speedLimit + "," + road.length + "]"));
			}
			addPercept(ASSyntax.createLiteral("roads", roadList));
		}
		catch(ParseException e){
			e.printStackTrace();
		}
	}
	
	public void addInitialPercepts(String agName){
		try{
			ListTerm nodeList = ASSyntax.createList();
			for(Integer i = 0; i < model.nodes.size(); i++){
					nodeList.add(ASSyntax.createNumber(i));
			}
			addPercept(agName, ASSyntax.createLiteral("nodes", nodeList));
			ListTerm roadList = ASSyntax.createList();
			for(Integer i = 0; i < model.roads.size(); i++){
				Road road = model.roads.get(i);
				roadList.add(ASSyntax.parseTerm("[" + i + "," + road.startNode.id + "," + road.endNode.id + "," + road.speedLimit + "," + road.length + "]"));
			}
			addPercept(agName, ASSyntax.createLiteral("roads", roadList));
		}
		catch(ParseException e){
			e.printStackTrace();
		}
	}
	
	public void updatePercepts(){
		clearPercepts();
		for(Node node : model.nodes){
			addPercept(node.removeLast().name, ASSyntax.createLiteral("has_action"));
		}
	}
	
	
	@Override
	public boolean executeAction(String agName, Structure act) {
		boolean result = false;
		if(act.getFunctor().equals("turn")){
			String term = act.getTerm(0).toString();
			int roadID = Integer.parseInt(term);
			if(model.carCanTurn(agName, roadID))
				result = true;
		}
		if(result)
			updatePercepts();
		return result;
	}
	
	class UpdateTimer implements TimerCallback{

		public void callback(int deltaTime) {
			model.moveCars(deltaTime);
			//Update graphics
			updatePercepts();
			
		}
		
	}
}
