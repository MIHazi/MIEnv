package Environment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

import jason.asSyntax.ASSyntax;
import jason.asSyntax.ListTerm;
import jason.asSyntax.LiteralImpl;
import jason.asSyntax.Structure;
import jason.asSyntax.parser.ParseException;
import jason.environment.Environment;
import jason.stdlib.print;
import jason.stdlib.println;

public class TopologyEnvironment extends Environment{
	
	Map config;
	TopologyModel model;
	
	public TopologyEnvironment() {
		super(1);
	}
	
	@Override
	public void init(String[] args) {
		if(args.length < 1)
			throw new IllegalArgumentException("Config file not specified");
		try {
			BufferedReader confInput = new BufferedReader(new FileReader(args[0]));
			StringBuilder builder = new StringBuilder();
			String str;
			while((str = confInput.readLine()) != null){
				builder.append(str + "\n");
			}
			confInput.close();
			JSONParser parser = JsonParserFactory.getInstance().newJsonParser();
			//TODO: DEBUG System.out.println(builder.toString());
			config = parser.parseJson(builder.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//TODO: DEBUG System.out.println("ROOT: \n" + config.toString());
		model = new TopologyModel(config);
		addInitialPercepts();
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
	
	public void updatePercepts(){
		clearPercepts();
		for(Node node : model.nodes){
			addPercept(node.removeLast().name, new LiteralImpl("hasAction"));
		}
	}
	
	@Override
	public void stop() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean executeAction(String agName, Structure act) {
		throw new UnsupportedOperationException();
	}
}
