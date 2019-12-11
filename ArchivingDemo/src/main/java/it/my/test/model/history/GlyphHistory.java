package it.my.test.model.history;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GlyphHistory {

  @Id
  private Integer id;

  private String word;
  private Integer value;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public GlyphHistory(String word, int value) {
    this.word = word;
    this.value = value;
  }

  public GlyphHistory() {}

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }
}
