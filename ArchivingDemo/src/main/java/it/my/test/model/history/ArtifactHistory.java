package it.my.test.model.history;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import it.my.test.model.enums.Type;

@Entity
public class ArtifactHistory {

	@Id
	private Integer id;
	private Type type;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "artifact_id")
	private List<RuneHistory> runes = new ArrayList<>();
	
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
	public List<RuneHistory> getRunes() {
		return runes;
	}
	public void setRunes(List<RuneHistory> runes) {
		this.runes = runes;
	}
	public ArtifactHistory(Type type) {
		super();
		this.type = type;
	}
	public ArtifactHistory() {
	}
}
