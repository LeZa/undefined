package Entity;

import com.build.config.JsonKey;

import java.io.Serializable;
import java.util.List;

public class Book extends Base
    implements Serializable{

    private static final long serialVersionUID = 5283739637161029477L;


    private Integer id;

    private String name;

    private List<Author> authorList;

    @JsonKey(value="id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonKey(value="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonKey( value = "author",isCollection = true)
    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }
}
