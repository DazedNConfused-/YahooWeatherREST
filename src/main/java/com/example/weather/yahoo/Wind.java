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
"chill",
"direction",
"speed"
})
public class Wind {

@JsonProperty("chill")
private String chill;
@JsonProperty("direction")
private String direction;
@JsonProperty("speed")
private String speed;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("chill")
public String getChill() {
return chill;
}

@JsonProperty("chill")
public void setChill(String chill) {
this.chill = chill;
}

@JsonProperty("direction")
public String getDirection() {
return direction;
}

@JsonProperty("direction")
public void setDirection(String direction) {
this.direction = direction;
}

@JsonProperty("speed")
public String getSpeed() {
return speed;
}

@JsonProperty("speed")
public void setSpeed(String speed) {
this.speed = speed;
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
