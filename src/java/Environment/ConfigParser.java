package Environment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.List;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

public class ConfigParser {

	Map config;
	
	public ConfigParser(String path){
		try {
			BufferedReader confInput = new BufferedReader(new FileReader(path));
			StringBuilder builder = new StringBuilder();
			String str;
			while((str = confInput.readLine()) != null){
				builder.append(str + "\n");
			}
			confInput.close();
			JSONParser parser = JsonParserFactory.getInstance().newJsonParser();
			config = parser.parseJson(builder.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int nAgents(){
		return ((List)config.get("Agents")).size();
	}
	
	public int nNodes(){
		return ((List)config.get("Nodes")).size();
	}
	
	public int nRoads(){
		return ((List)config.get("Roads")).size();
	}
	
	public String getAgName(int idx){
		return (String)((Map)((List)config.get("Agents")).get(idx)).get("Name");
	}
	
	public int getAgStartNode(int idx){
		return Integer.parseInt((String)((Map)((List)config.get("Agents")).get(idx)).get("StartNode"));
	}
	
	public int getAgEndNode(int idx){
		return Integer.parseInt((String)((Map)((List)config.get("Agents")).get(idx)).get("EndNode"));
	}
	
	public String getAgCode(int idx){
		return (String)((Map)((List)config.get("Agents")).get(idx)).get("Agent");
	}
	
	public int getAgSpawnDelay(int idx){
		return Integer.parseInt((String)((Map)((List)config.get("Agents")).get(idx)).get("SpawnDelay"));
	}
	
	public int getAgSpawnCount(int idx){
		return Integer.parseInt((String)((Map)((List)config.get("Agents")).get(idx)).get("SpawnCount"));
	}
	
	public int getNodePosX(int idx){
		return Integer.parseInt((String)((Map)((List)config.get("Nodes")).get(idx)).get("PosX"));
	}
	
	public int getNodePosY(int idx){
		return Integer.parseInt((String)((Map)((List)config.get("Nodes")).get(idx)).get("PosY"));
	}
	
	public int getRoadStartID(int idx){
		return Integer.parseInt((String)((Map)((List)config.get("Roads")).get(idx)).get("StartID"));
	}
	
	public int getRoadEndID(int idx){
		return Integer.parseInt((String)((Map)((List)config.get("Roads")).get(idx)).get("EndID"));
	}
	
	public int getRoadLimit(int idx){
		return Integer.parseInt((String)((Map)((List)config.get("Roads")).get(idx)).get("Limit"));
	}
	
}
