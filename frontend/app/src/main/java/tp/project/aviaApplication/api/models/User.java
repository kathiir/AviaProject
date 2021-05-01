package tp.project.aviaApplication.api.models;

public class User {
    private Integer id;

    private String name;

    public User(String name){
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
