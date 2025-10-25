package POJOs;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AuthorsPojo {
    public String name;
    public Bio bio;
    public String personal_name;
    public String death_date;
    public ArrayList<String> alternate_names;
    public String birth_date;
    public Type type;
    public RemoteIds remote_ids;
    public ArrayList<Integer> photos;
    public String key;
    public int latest_revision;
    public int revision;
    public Created created;
    public LastModified last_modified;
}
