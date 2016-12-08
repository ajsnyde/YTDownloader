package youtubeObjects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Album {
  @Id
  public String albumName;
  public int length;
  @OneToMany(mappedBy = "album")
  public List<Song> songs = new ArrayList<Song>();

  public Album() {

  }
}
