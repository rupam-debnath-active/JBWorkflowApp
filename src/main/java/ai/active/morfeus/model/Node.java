package ai.active.morfeus.model;

import java.util.List;
import java.util.Map;

public class Node {

  private String type;
  private Canvas canvas;
  private Map<String, Object> payload;
  private List<String> next;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Canvas getCanvas() {
    return canvas;
  }

  public void setCanvas(Canvas canvas) {
    this.canvas = canvas;
  }

  public Map<String, Object> getPayload() {
    return payload;
  }

  public void setPayload(Map<String, Object> payload) {
    this.payload = payload;
  }

  public List<String> getNext() {
    return next;
  }

  public void setNext(List<String> next) {
    this.next = next;
  }
}
