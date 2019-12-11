package it.my.test.archiver;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.my.test.model.Rune;
import it.my.test.model.enums.Element;
import it.my.test.model.history.RuneHistory;
import it.my.test.repository.RuneHistoryRepository;

@Component
public class RuneArchiver extends Archiver<Rune, RuneHistory> {

  @Autowired private RuneHistoryRepository repository;

  @Override
  public RuneHistory storicize(Rune original) {
    RuneHistory target = new RuneHistory();
    BeanUtils.copyProperties(original, target);
    return target;
  }

  @Override
  public RuneHistory archive(Object toBeArchived) throws ArchiveException {
    if (toBeArchived.getClass().equals(getSupportedType())) {
      Rune actual = (Rune) toBeArchived;

      if (actual.getElement().equals(Element.FORBIDDEN)) {
        throw new ArchiveException("Forbidden Type");
      }

      return persist(storicize(actual));
    } else {
      throw new ArchiveException("Unsupported Type");
    }
  }

  @Override
  public RuneHistory persist(RuneHistory toBePersisted) {
    return repository.save(toBePersisted);
  }

  public RuneArchiver() {
    supportedType = Rune.class;
  }
}
