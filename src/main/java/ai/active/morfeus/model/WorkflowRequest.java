package ai.active.morfeus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkflowRequest {
  private String node_name;
  private String node_type;
  private String node_description;
  private String next_node;
  private String text;
  private String failure_text;
  private String variable_name;
  private String false_node;
  private String header;
  private List<ListObject> list;
  private String option_1_text;
  private String option_2_text;


  public String getNode_name() {
    return node_name;
  }

  public void setNode_name(String node_name) {
    this.node_name = node_name;
  }

  public String getNode_type() {
    return node_type;
  }

  public void setNode_type(String node_type) {
    this.node_type = node_type;
  }

  public String getNode_description() {
    return node_description;
  }

  public void setNode_description(String node_description) {
    this.node_description = node_description;
  }

  public String getNext_node() {
    return next_node;
  }

  public void setNext_node(String next_node) {
    this.next_node = next_node;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getFailure_text() {
    return failure_text;
  }

  public void setFailure_text(String failure_text) {
    this.failure_text = failure_text;
  }

  public String getVariable_name() {
    return variable_name;
  }

  public void setVariable_name(String variable_name) {
    this.variable_name = variable_name;
  }

  public String getFalse_node() {
    return false_node;
  }

  public void setFalse_node(String false_node) {
    this.false_node = false_node;
  }

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public List<ListObject> getList() {
    return list;
  }

  public void setList(List<ListObject> list) {
    this.list = list;
  }

  public String getOption_1_text() {
    return option_1_text;
  }

  public void setOption_1_text(String option_1_text) {
    this.option_1_text = option_1_text;
  }

  public String getOption_2_text() {
    return option_2_text;
  }

  public void setOption_2_text(String option_2_text) {
    this.option_2_text = option_2_text;
  }

  @Override
  public String toString() {
    return "WorkflowRequest{" + "node_name='" + node_name + '\'' + ", node_type='" + node_type + '\'' + ", node_description='" + node_description + '\'' + ", next_node='" + next_node + '\'' + ", text='" + text + '\'' + ", failure_text='" + failure_text + '\'' + ", variable_name='" + variable_name + '\'' + ", false_node='" + false_node + '\'' + ", header='" + header + '\'' + ", list=" + list + ", option_1_text='" + option_1_text + '\'' + ", option_2_text='" + option_2_text + '\'' + '}';
  }
}
