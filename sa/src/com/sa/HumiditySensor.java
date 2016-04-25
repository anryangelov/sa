package com.sa;

import org.json.simple.JSONObject;

public class HumiditySensor extends TemperatureSensor {

	private final static double MIN_HUM = 0.0;
	private final static double MAX_HUM = 100.0;
	private final static double DELTA_HUM = 4.0;

	private double humidity;

	public HumiditySensor() {
		super();
		humidity = SensorHelper.getValueSensor(MIN_HUM, MAX_HUM);
	}

	public String toString() {
		return String.format("HumiditySensor(%.2fC,%.2f%%)", temperature,
				humidity);
	}

	public double getHumidity() {
		return humidity;
	}

	public JSONObject toJSON() {
		JSONObject obj = super.toJSON();
		obj.put("humidity", humidity);
		return obj;
	}

	protected void updateValues() {
		super.updateValues();
		humidity = SensorHelper.changeValuesSensor(humidity, MIN_HUM, MAX_HUM,
				DELTA_HUM);
	}

	public static void main(String[] args) {
		HumiditySensor hs = new HumiditySensor();
		System.out.println(hs);
		System.out.println(hs.toJSON());
	}

}
