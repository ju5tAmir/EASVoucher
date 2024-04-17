package dk.easvoucher.be.ticket;

public class Item {
    private int id;
    private String title;
    private boolean isClaimed;

    public Item(int id, String title, boolean isClaimed) {
        this.id = id;
        this.title = title;
        this.isClaimed = isClaimed;
    }

    public Item(String title, boolean isClaimed) {
        this.title = title;
        this.isClaimed = isClaimed;
    }
    public Item(){

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

    public boolean isClaimed() {
        return isClaimed;
    }

    public void setClaimed(boolean claimed) {
        isClaimed = claimed;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isClaimed=" + isClaimed +
                '}';
    }
}
