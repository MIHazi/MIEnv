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

public class TopologyEnvironment extends Environment{
	
	Map config;
	TopologyModel model;
	
	@Override
	public void init(String[] args) {
		if(args.length < 1)
			throw new IllegalArgumentException("Config file not specified");
		try {
			BufferedReader confInput = new BufferedReader(new FileReader(args[0]));
			StringBuilder builder = new StringBuilder();
			String str;
			while((str = confInput.readLine()) != null){
				builder.append(str);
			}
			confInput.close();
			JSONParser parser = JsonParserFactory.getInstance().newJsonParser();
			config = parser.parseJson(builder.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model = new TopologyModel(config);
		addInitialPercepts();
	}
	
	public void addInitialPercepts(){
		try{
			ListTerm list = ASSyntax.createList();
			for(Integer i = 0; i < model.nodes.size(); i++){
				try {
					list.add(ASSyntax.parseTerm(i.toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			addPercept(ASSyntax.createLiteral("roads", list));
			list.clear();
			for(int i = 0; i < model.roads.size(); i++){
				Road road = model.roads.get(i);
				list.add(ASSyntax.parseTerm("[" + i + "," + road.startNode.id + "," + road.endNode.id + "," + road.speedLimit + "," + road.length + "]"));
			}
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
