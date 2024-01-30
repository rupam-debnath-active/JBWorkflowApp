package ai.active.morfeus.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventTab {
  private Object[] id;
  @JsonProperty("event_type")
  private String eventType;
  @JsonProperty("tab_id")
  private String tabID;

  public Object[] getID() { return id; }
  public void setID(Object[] value) { this.id = value; }

  public String getEventType() { return eventType; }
  public void setEventType(String value) { this.eventType = value; }

  public String getTabID() { return tabID; }
  public void setTabID(String value) { this.tabID = value; }
}
