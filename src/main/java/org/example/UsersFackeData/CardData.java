package org.example.UsersFackeData;

public class CardData {
    private String title;
    private String imageUrl;

    public CardData(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
