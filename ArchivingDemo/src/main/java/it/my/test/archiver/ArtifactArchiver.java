package it.my.test.archiver;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.my.test.model.Artifact;
import it.my.test.model.history.ArtifactHistory;
import it.my.test.repository.ArtifactHistoryRepository;

@Component
public class ArtifactArchiver extends Archiver<Artifact, ArtifactHistory> {

  @Autowired private ArtifactHistoryRepository repository;

  @Override
  public ArtifactHistory storicize(Artifact original) {
    ArtifactHistory target = new ArtifactHistory();
    BeanUtils.copyProperties(original, target);
    return target;
  }

  @Override
  public ArtifactHistory archive(Object toBeArchived) throws ArchiveException {
    if (toBeArchived.getClass().equals(getSupportedType())) {
      Artifact actual = (Artifact) toBeArchived;
      return persist(storicize(actual));
    } else {
      throw new ArchiveException("Unsupported Type");
    }
  }

  @Override
  public ArtifactHistory persist(ArtifactHistory toBePersisted) {
    System.out.println("Archiving Artifact " + toBePersisted.getId());
    return repository.save(toBePersisted);
  }
  
  public ArtifactArchiver() {
	  this.supportedType = Artifact.class;
  }
}
