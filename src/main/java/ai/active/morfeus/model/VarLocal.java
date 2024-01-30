package ai.active.morfeus.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VarLocal {
  private String key;
  private String name;
  @JsonProperty("data_type")
  private String dataType;
  @JsonProperty("default_value")
  private String defaultValue;

  public String getKey() { return key; }
  public void setKey(String value) { this.key = value; }

  public String getName() { return name; }
  public void setName(String value) { this.name = value; }

  public String getDataType() { return dataType; }
  public void setDataType(String value) { this.dataType = value; }

  public String getDefaultValue() { return defaultValue; }
  public void setDefaultValue(String value) { this.defaultValue = value; }
}
