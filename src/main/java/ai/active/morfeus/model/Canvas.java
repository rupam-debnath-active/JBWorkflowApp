package ai.active.morfeus.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Canvas {
  @JsonProperty("x_pos")
  private int xPos;
  @JsonProperty("y_pos")
  private int yPos;

  @JsonProperty("x_pos")
  public int getXPos() { return xPos; }
  @JsonProperty("x_pos")
  public void setXPos(int value) { this.xPos = value; }
  @JsonProperty("y_pos")
  public int getYPos() { return yPos; }
  @JsonProperty("y_pos")
  public void setYPos(int value) { this.yPos = value; }
}
