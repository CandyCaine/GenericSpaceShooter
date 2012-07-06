package me.rich.gss.stage;

import java.util.ArrayList;

import me.rich.gss.entity.mob.MobType;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.xml.XMLElement;
import org.newdawn.slick.util.xml.XMLElementList;
import org.newdawn.slick.util.xml.XMLParser;

public class Stage {

	private int id;
	private ArrayList<SpawnQueue> spawnQueues;
	private int currentQueue;
	private int finalQueue;
	
	private boolean stageOver;
	
	private XMLParser xmlParser;
	private XMLElementList rootList;
	
	public Stage(int id) {
		setID(id);
		spawnQueues = new ArrayList<SpawnQueue>();
		stageOver = false;
		xmlParser = new XMLParser();
		validateXML();
		generateSpawnQueues();
		
	}
	
	private void setID(int id) {
		this.id = id;
	}
	
	public int getID () {
		return id;
	}
	
	private void validateXML() {
		try {
			rootList = xmlParser.parse("resource/asset/stage/stage" + id + "/wavequeue.xml").getChildrenByName("stage");
			if (!(rootList.get(0).getIntAttribute("id") == id)) {
				System.err.println("Error: Stage " + id + ", identity does not match XML.");
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	private void generateSpawnQueues() {		
		XMLElement root = null;
		try {
			root = xmlParser.parse("resource/asset/stage/stage" + id + "/wavequeue.xml");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		XMLElement queue = root.getChildrenByName("queue").get(0);
		XMLElementList wave = queue.getChildren();
		int queueCount = 0;
		for (int i = 0; i < wave.size(); i++) {
			String id;
			MobType type;
			String size;
			
			type = MobType.determineType(wave.get(i).getChildrenByName("mob").get(0).getContent());
			id = wave.get(i).getAttribute("order");
			size = wave.get(i).getChildrenByName("size").get(0).getContent();
			
			spawnQueues.add(new SpawnQueue(Integer.parseInt(id), type, Integer.parseInt(size)));
			queueCount++;
		}
		
		finalQueue = queueCount;
		currentQueue = 0;
	}
	
	public SpawnQueue getCurrentSpawnQueue() {
		return spawnQueues.get(currentQueue);
	}
	
	public SpawnQueue getSpawnQueue(int id) {
		if (id >= 0 && id <= finalQueue) {
			return spawnQueues.get(id);
		} else {
			return null;
		}
	}
	
	public ArrayList<SpawnQueue> getSpawnQueueList() {
		return spawnQueues;
	}
	
	public void incrementCurrentQueue() {
		currentQueue++;
		if (currentQueue == finalQueue) {
			stageOver = true;
			currentQueue--;
		}
	}
	
	public boolean isStageOver() {
		return stageOver;
	}
}
