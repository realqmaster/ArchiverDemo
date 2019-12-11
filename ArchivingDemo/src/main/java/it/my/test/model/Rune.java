package it.my.test.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import it.my.test.model.enums.Element;

@Entity
public class Rune {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private Element element;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "rune_id")
  private List<Glyph> glyphs;

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

  public Rune(Element element) {
    super();
    this.element = element;
  }

  public Rune() {}

  public List<Glyph> getGlyphs() {
    return glyphs;
  }

  public void setGlyphs(List<Glyph> glyphs) {
    this.glyphs = glyphs;
  }
}
