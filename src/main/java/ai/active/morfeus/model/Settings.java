package ai.active.morfeus.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Settings {
  @JsonProperty("switch_model")
  private SwitchModel switchModel;
  @JsonProperty("sticky_journey")
  private boolean stickyJourney;
  @JsonProperty("ai_workspace_id")
  private String aiWorkspaceID;

  public SwitchModel getSwitchModel() { return switchModel; }
  public void setSwitchModel(SwitchModel value) { this.switchModel = value; }

  public boolean getStickyJourney() { return stickyJourney; }
  public void setStickyJourney(boolean value) { this.stickyJourney = value; }

  public String getAIWorkspaceID() { return aiWorkspaceID; }
  public void setAIWorkspaceID(String value) { this.aiWorkspaceID = value; }
}
