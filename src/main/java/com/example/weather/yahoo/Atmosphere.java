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
"humidity",
"pressure",
"rising",
"visibility"
})
public class Atmosphere {

@JsonProperty("humidity")
private String humidity;
@JsonProperty("pressure")
private String pressure;
@JsonProperty("rising")
private String rising;
@JsonProperty("visibility")
private String visibility;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("humidity")
public String getHumidity() {
return humidity;
}

@JsonProperty("humidity")
public void setHumidity(String humidity) {
this.humidity = humidity;
}

@JsonProperty("pressure")
public String getPressure() {
return pressure;
}

@JsonProperty("pressure")
public void setPressure(String pressure) {
this.pressure = pressure;
}

@JsonProperty("rising")
public String getRising() {
return rising;
}

@JsonProperty("rising")
public void setRising(String rising) {
this.rising = rising;
}

@JsonProperty("visibility")
public String getVisibility() {
return visibility;
}

@JsonProperty("visibility")
public void setVisibility(String visibility) {
this.visibility = visibility;
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