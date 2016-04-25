/*
 * Initialize sensors while loading class
 */

package com.sa;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.NotFoundException;

@Path("/sensors")
public class RestApi {

	private static MemoryDB<TemperatureSensor> dbTempSensor = new MemoryDB<>();
	private static MemoryDB<HumiditySensor> dbHumSensor = new MemoryDB<>();
	
	static {
		initSensorsTemperature(10);
		initSensorHumidity(10);
	}

	@Path("{typeSensor}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDate(@PathParam("typeSensor") String typeSensor) {
		if (typeSensor.equals("temperature")) {
			return dbTempSensor.ToJSON().toJSONString();
		}
		if (typeSensor.equals("humidity")) {
			return dbHumSensor.ToJSON().toJSONString();
		}
		throw new NotFoundException("Sensor type NOT Found");
	}

	@Path("{typeSensor}/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDate(@PathParam("id") Integer id,
			@PathParam("typeSensor") String typeSensor) {
		if (typeSensor.equals("temperature")) {
			TemperatureSensor sensor = dbTempSensor.getSensor(id);
			if (sensor == null)
				throw new NotFoundException("Sensor not found");
			return sensor.toJSON().toJSONString();
		}
		if (typeSensor.equals("humidity")) {
			HumiditySensor sensor = dbHumSensor.getSensor(id);
			if (sensor == null)
				throw new NotFoundException("Sensor not found");
			return sensor.toJSON().toJSONString();
		}
		throw new NotFoundException("Sensor type NOT Found");
	}

	@Path("/add/{typeSensor}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addSensor(@PathParam("typeSensor") String typeSensor) {
		if (typeSensor.equals("temperature")) {
			TemperatureSensor sensor = new TemperatureSensor();
			return dbTempSensor.addSensor(sensor);
		}
		if (typeSensor.equals("humidity")) {
			HumiditySensor sensor = new HumiditySensor();
			return dbHumSensor.addSensor(sensor);
		}
		throw new NotFoundException("Sensor type NOT Found");
	}

	public static void initSensorsTemperature(int numSensors) {
		for (int i = 0; i < numSensors; i++) {
			TemperatureSensor sensor = new TemperatureSensor();
			dbTempSensor.addSensor(sensor);
		}
	}
	
	public static void initSensorHumidity(int numSensors) {
		for (int i = 0; i < numSensors; i++) {
			HumiditySensor sensor = new HumiditySensor();
			dbHumSensor.addSensor(sensor);
		}
	}
}
