package youtubeObjects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "APP")
public class Song {

  @Id
  public String title;
  public Album album;
  public int startTime;
  public int endTime;
  public int number;

  public Song() {
  }

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  public Album getAlbum() {
    return album;
  }
}
