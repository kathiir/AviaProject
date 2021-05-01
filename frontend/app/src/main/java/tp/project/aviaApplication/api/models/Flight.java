package tp.project.aviaApplication.api.models;

public class Flight {
    private Integer id;

    private String src;
    private String dest;
    private Float price;
    private String date;

    public Flight(){}

    public Flight(String src, String dest, Float price, String date) {
        this.src = src;
        this.dest = dest;
        this.price = price;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public String getSrc() {
        return src;
    }

    public String getDest() {
        return dest;
    }

    public Float getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}
