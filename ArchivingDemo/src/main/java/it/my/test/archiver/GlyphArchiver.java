package it.my.test.archiver;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.my.test.model.Glyph;
import it.my.test.model.history.GlyphHistory;
import it.my.test.repository.GlyphHistoryRepository;

@Component
public class GlyphArchiver extends Archiver<Glyph, GlyphHistory> {

  @Autowired private GlyphHistoryRepository repository;

  @Override
  public GlyphHistory storicize(Glyph original) {
    GlyphHistory target = new GlyphHistory();
    BeanUtils.copyProperties(original, target);
    return target;
  }

  @Override
  public GlyphHistory archive(Object toBeArchived) throws ArchiveException {
    if (toBeArchived.getClass().equals(getSupportedType())) {
      Glyph actual = (Glyph) toBeArchived;
      return persist(storicize(actual));
    } else {
      throw new ArchiveException("Unsupported Type");
    }
  }

  @Override
  public GlyphHistory persist(GlyphHistory toBePersisted) {
    System.out.println("Archiving Glyph " + toBePersisted.getId());
    return repository.save(toBePersisted);
  }
  
  public GlyphArchiver() {
	  supportedType = Glyph.class;
  }
}
