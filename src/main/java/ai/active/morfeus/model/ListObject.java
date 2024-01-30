package ai.active.morfeus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListObject {

  @JsonProperty("item_title")
  private String itemTitle;

  @JsonProperty("item_subtitle")
  private String itemSubtitle;

  @JsonProperty("item_value")
  private String itemValue;

  @JsonProperty("item_title")
  public String getItemTitle() {
    return itemTitle;
  }

  @JsonProperty("item_title")
  public void setItemTitle(String itemTitle) {
    this.itemTitle = itemTitle;
  }

  @JsonProperty("item_subtitle")
  public String getItemSubtitle() {
    return itemSubtitle;
  }
  @JsonProperty("item_subtitle")
  public void setItemSubtitle(String itemSubtitle) {
    this.itemSubtitle = itemSubtitle;
  }

  @JsonProperty("item_value")
  public String getItemValue() {
    return itemValue;
  }

  @JsonProperty("item_value")
  public void setItemValue(String itemValue) {
    this.itemValue = itemValue;
  }

  @Override
  public String toString() {
    return "ListObject{" + "itemTitle='" + itemTitle + '\'' + ", itemSubtitle='" + itemSubtitle + '\'' + ", itemValue='" + itemValue + '\'' + '}';
  }
}
