package youtubeObjects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Song {

  @Id
  public String title;

  // Known bug: SQLite and JPA can't use foreign keys with each other (at least, not without some hackish stuff).
  // http://stackoverflow.com/questions/12481700/jpa-2-0-eclipselink-and-sqlite-3-7-2-cant-create-the-tables

  /*
   * It really sucks, as I've spent an hour dissecting the issue. SQLite doesn't have full ALTER support (specifically for when foreign keys are added). That shouldn't be a problem if EclipseLink had
   * the right DB Platform, but it doesn't. So now I'm back to the drawing board. One would think that either SQLite would begin supporting full ALTERs or that EclipseLink would have a dialect
   * workaround by now. This problem has supposedly existed for 6 years.
   * 
   * ALTER TABLE SONG ADD CONSTRAINT FK_SONG_TITLE FOREIGN KEY (TITLE) REFERENCES ALBUM (ALBUMNAME)
   */

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "title", insertable = false, updatable = false)
  public Album album;
  public int start;
  public int end;
  public int number;

  public Song() {
  }
}
