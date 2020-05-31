package wenda.model;

public class PostImage {

    private String name;
    private String imageBase64;

    public PostImage() {}

    public PostImage(String name, String imageBase64) {
        super();
        this.name = name;
        this.imageBase64 = imageBase64;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

}

