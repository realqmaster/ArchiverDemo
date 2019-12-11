package it.my.test.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import it.my.test.model.enums.Type;

@Entity
public class Artifact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Type type;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "artifact_id")
	private List<Rune> runes = new ArrayList<>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public List<Rune> getRunes() {
		return runes;
	}
	public void setRunes(List<Rune> runes) {
		this.runes = runes;
	}
	public Artifact(Type type) {
		super();
		this.type = type;
	}
	public Artifact() {
	}
}
