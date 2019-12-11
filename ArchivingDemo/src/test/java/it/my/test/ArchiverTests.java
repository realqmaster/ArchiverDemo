package it.my.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.javers.core.ChangesByObject;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.my.test.archiver.ArchiveException;
import it.my.test.archiver.Archiver;
import it.my.test.model.Artifact;
import it.my.test.model.Rune;
import it.my.test.model.enums.Element;
import it.my.test.model.enums.Type;
import it.my.test.repository.ArtifactHistoryRepository;
import it.my.test.repository.GlyphHistoryRepository;
import it.my.test.repository.RuneHistoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArchiverTests {

  @Autowired ArtifactHistoryRepository ahrepo;
  @Autowired RuneHistoryRepository rhrepo;
  @Autowired GlyphHistoryRepository ghrepo;
  @Autowired List<Archiver<?, ?>> archivers;

  public static Artifact a1;
  public static Artifact a2;
  public static Artifact a3;
  public static Artifact a4;

  @Before
  public void setup() {
    a1 = new Artifact(Type.WEAPON);
    a1.setId(1);
    Rune r1 = new Rune(Element.FIRE);
    Rune r2 = new Rune(Element.ICE);
    r1.setId(3);
    r2.setId(4);
    a1.getRunes().add(r1);
    a1.getRunes().add(r2);
  }

  @Test
  @Transactional
  public void rollback_with_forbidden()
      throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    Artifact a5 = mapper.readValue(mapper.writeValueAsString(a1), Artifact.class);
    a5.setType(Type.TRINKET);
    a5.getRunes().forEach(r -> r.setElement(Element.FORBIDDEN));

    alterAndCompare(a1, a5);

    assertEquals(0, ahrepo.count());
    assertEquals(0, rhrepo.count());
    assertEquals(0, ghrepo.count());
  }

  private void alterAndCompare(Artifact old, Artifact updated) {

    Javers javers = JaversBuilder.javers().build();
    Diff diff = javers.compare(old, updated);
    Set<Object> toBeArchived = new HashSet<>();

    for (ChangesByObject change : diff.groupByObject()) {
      if (change.getPropertyChanges().size() > 0) {
        change
            .getPropertyChanges()
            .forEach(p -> p.getAffectedObject().ifPresent(o -> toBeArchived.add(o)));
      }

      if (change.getObjectsRemoved().size() > 0) {
        change
            .getObjectsRemoved()
            .forEach(p -> p.getAffectedObject().ifPresent(o -> toBeArchived.add(o)));
      }
    }
    Map<String, List<Object>> groupedList =
        toBeArchived.stream().collect(Collectors.groupingBy(o -> o.getClass().getSimpleName()));

    for (Entry<String, List<Object>> sublist : groupedList.entrySet()) {
      System.out.println(sublist.getValue().size() + " " + sublist.getKey() + " must be archived");
    }

    try {

      for (Object object : toBeArchived) {
        for (Archiver<?, ?> archiver : archivers) {
          if (object.getClass().equals(archiver.getSupportedType())) {
            archiver.archive(object);
          }
        }
      }
    } catch (ArchiveException e) {
      System.out.println("An exception occurred in the archivers! Rollbacking..");
    }
  }

  @Test
  @Transactional
  public void no_rollback_without_forbidden()
      throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    Artifact a5 = mapper.readValue(mapper.writeValueAsString(a1), Artifact.class);
    a5.setType(Type.TRINKET);
    a5.getRunes().forEach(r -> r.setElement(Element.FIRE));

    Javers javers = JaversBuilder.javers().build();
    Diff diff = javers.compare(a1, a5);
    Set<Object> toBeArchived = new HashSet<>();

    for (ChangesByObject change : diff.groupByObject()) {
      if (change.getPropertyChanges().size() > 0) {
        change
            .getPropertyChanges()
            .forEach(p -> p.getAffectedObject().ifPresent(o -> toBeArchived.add(o)));
      }

      if (change.getObjectsRemoved().size() > 0) {
        change
            .getObjectsRemoved()
            .forEach(p -> p.getAffectedObject().ifPresent(o -> toBeArchived.add(o)));
      }
    }

    Map<String, List<Object>> groupedList =
        toBeArchived.stream().collect(Collectors.groupingBy(o -> o.getClass().getSimpleName()));

    for (Entry<String, List<Object>> sublist : groupedList.entrySet()) {
      System.out.println(sublist.getValue().size() + " " + sublist.getKey() + " must be archived");
    }

    try {

      for (Object object : toBeArchived) {
        for (Archiver<?, ?> archiver : archivers) {
          if (object.getClass().equals(archiver.getSupportedType())) {
            archiver.archive(object);
          }
        }
      }
    } catch (ArchiveException e) {
      System.out.println("An exception occurred in the archivers! Rollbacking..");
    }

    assertNotEquals(0, ahrepo.count());
    assertNotEquals(0, rhrepo.count());
    assertEquals(0, ghrepo.count());
  }
}
