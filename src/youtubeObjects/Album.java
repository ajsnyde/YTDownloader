package youtubeObjects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "APP")
public class Album {
  @Id
  public String albumName;
  public int length;
  @OneToMany(mappedBy = "album", cascade = CascadeType.PERSIST)
  public List<Song> songs = new ArrayList<Song>();

  public Album() {

  }
}
