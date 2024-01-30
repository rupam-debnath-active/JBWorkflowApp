package ai.active.morfeus.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetaInfo {
  @JsonProperty("journey_id")
  private String journeyID;
  @JsonProperty("journey_name")
  private String journeyName;
  @JsonProperty("journey_type")
  private String journeyType;
  @JsonProperty("journey_subtype")
  private Object journeySubtype;
  @JsonProperty("tag")
  private Object[] tag;

  public String getJourneyID() { return journeyID; }
  public void setJourneyID(String value) { this.journeyID = value; }

  public String getJourneyName() { return journeyName; }
  public void setJourneyName(String value) { this.journeyName = value; }

  public String getJourneyType() { return journeyType; }
  public void setJourneyType(String value) { this.journeyType = value; }

  public Object getJourneySubtype() { return journeySubtype; }
  public void setJourneySubtype(Object value) { this.journeySubtype = value; }

  public Object[] getTag() { return tag; }
  public void setTag(Object[] value) { this.tag = value; }
}
