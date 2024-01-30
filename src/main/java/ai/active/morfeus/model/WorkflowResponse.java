package ai.active.morfeus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class WorkflowResponse {
  private List<Journey> journeys;

  private List<String> ai = new ArrayList<>();

  public List<Journey> getJourneys() { return journeys; }
  public void setJourneys(List<Journey> value) { this.journeys = value; }

  public List<String> getAi() {
    return ai;
  }

  public void setAi(List<String> ai) {
    this.ai = ai;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", WorkflowResponse.class.getSimpleName() + "[", "]")
            .add("journeys=" + journeys)
            .add("ai=" + ai)
            .toString();
  }
}
