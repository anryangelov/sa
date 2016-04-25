/*	This class define Temperature sensor and emulator logic. 
 * 	TemperatureSensor is super class for humidity sensor 
 *  and implement some functionality used from child class - humidity.
 *  When sensor is initialize it start never endless while loop
 *  in separate thread and randomly change sensor parameters. 
 */

package com.sa;

import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.JSONObject;

public class TemperatureSensor implements Runnable {

	private final static double MIN_TEMP = -40;
	private final static double MAX_TEMP = 100;
	private final static double DELTA_TEMP = 5.0;
	private final static long MIN_TIME_SLEEP = 1000;
	private final static long MAX_TIME_SLEEP = 5000;

	protected double temperature;

	public TemperatureSensor() {
		this.temperature = SensorHelper.getValueSensor(MIN_TEMP, MAX_TEMP);
		start(); // start while loop in separate thread;
	}

	public String toString() {
		return String.format("TemperatureSensor(%.2fC)", temperature);
	}

	public double getTemperature() {
		return temperature;
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("temperature", temperature);
		return obj;
	}

	protected void updateValues() {
		temperature = SensorHelper.changeValuesSensor(temperature, MIN_TEMP,
				MAX_TEMP, DELTA_TEMP); // randomly changed
	}

	public void run() {
		while (true) {
			updateValues();
			long mills = ThreadLocalRandom.current().nextLong(MIN_TIME_SLEEP,
					MAX_TIME_SLEEP);
			try {
				Thread.sleep(mills);
			} catch (InterruptedException e) {
				e.printStackTrace(); // only one threat will change this sensor
										// so we do not expect catch exception
			}
		}
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}

	public static void main(String[] args) {
		TemperatureSensor ts = new TemperatureSensor();
		System.out.println(ts.toJSON());
	}

}
