package it.my.test.model.history;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import it.my.test.model.enums.Element;

@Entity
public class RuneHistory {

  @Id
  private Integer id;

  private Element element;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "rune_id")
  private List<GlyphHistory> glyphs;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Element getElement() {
    return element;
  }

  public void setElement(Element element) {
    this.element = element;
  }

  public RuneHistory(Element element) {
    super();
    this.element = element;
  }

  public RuneHistory() {}

  public List<GlyphHistory> getGlyphs() {
    return glyphs;
  }

  public void setGlyphs(List<GlyphHistory> glyphs) {
    this.glyphs = glyphs;
  }
}
