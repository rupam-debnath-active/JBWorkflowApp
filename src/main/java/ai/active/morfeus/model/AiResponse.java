package ai.active.morfeus.model;

import java.util.List;

public class AiResponse {

  private List<WorkflowRequest> journey;

  public List<WorkflowRequest> getJourney() {
    return journey;
  }

  public void setJourney(List<WorkflowRequest> journey) {
    this.journey = journey;
  }
}
