package ai.active.morfeus.model;

import java.util.StringJoiner;

public class Journey {
  private Content content;

  public Content getContent() { return content; }
  public void setContent(Content value) { this.content = value; }

  @Override
  public String toString() {
    return new StringJoiner(", ", Journey.class.getSimpleName() + "[", "]")
            .add("content=" + content)
            .toString();
  }
}
