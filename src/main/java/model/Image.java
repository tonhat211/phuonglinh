package model;

public class Image {
    public int id;
    public String title;
    public String url;
    public int parentID;
    public Image() {}

    public Image(int id, String title, String url, int parentID) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.parentID = parentID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", parentID=" + parentID +
                '}';
    }
}
