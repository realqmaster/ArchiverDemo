package it.my.test.archiver;

public abstract class Archiver<E, H> {

  public Class<E> supportedType;

  public abstract H storicize(E original);

  public abstract H archive(Object toBeArchived) throws ArchiveException;

  public abstract H persist(H toBePersisted);

  public Class<E> getSupportedType() {
    return supportedType;
  }

  public void setSupportedType(Class<E> supportedType) {
    this.supportedType = supportedType;
  }
}
