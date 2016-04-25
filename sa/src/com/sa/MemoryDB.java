/*
 * Memory DB class generate ID when sensor is added.
 * we keep last used ID and they always increase;
 * we synchronized add and update methods in case 
 * of race condition (for example many POST requests)
 * I am using generic types to keep me DRY
 */

package com.sa;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MemoryDB<T extends TemperatureSensor> {

	private HashMap<Integer, T> data;
	private Integer lastID;

	public MemoryDB() {
		data = new HashMap<>();
		lastID = 0;
	}

	public synchronized String addSensor(T sensor) {
		lastID++;
		data.put(lastID, sensor);
		JSONObject obj = new JSONObject();
		obj.put("id", lastID);
		return obj.toJSONString();
	}

	public synchronized void updateSensor(Integer id, T sensor) {
		data.replace(id, sensor);
	}

	public T getSensor(Integer id) {
		return data.get(id);
	}

	public JSONArray ToJSON() {
		JSONArray arr = new JSONArray();
		JSONObject obj;
		for (Entry<Integer, T> entry : data.entrySet()) {
			obj = entry.getValue().toJSON();
			obj.put("id", entry.getKey());
			arr.add(obj);
		}
		return arr;
	}

}
