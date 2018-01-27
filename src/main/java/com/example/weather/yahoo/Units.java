package com.example.weather.yahoo;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"distance",
"pressure",
"speed",
"temperature"
})
public class Units {

@JsonProperty("distance")
private String distance;
@JsonProperty("pressure")
private String pressure;
@JsonProperty("speed")
private String speed;
@JsonProperty("temperature")
private String temperature;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("distance")
public String getDistance() {
return distance;
}

@JsonProperty("distance")
public void setDistance(String distance) {
this.distance = distance;
}

@JsonProperty("pressure")
public String getPressure() {
return pressure;
}

@JsonProperty("pressure")
public void setPressure(String pressure) {
this.pressure = pressure;
}

@JsonProperty("speed")
public String getSpeed() {
return speed;
}

@JsonProperty("speed")
public void setSpeed(String speed) {
this.speed = speed;
}

@JsonProperty("temperature")
public String getTemperature() {
return temperature;
}

@JsonProperty("temperature")
public void setTemperature(String temperature) {
this.temperature = temperature;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}