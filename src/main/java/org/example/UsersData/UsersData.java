package org.example.UsersData;

import java.util.ArrayList;
import java.util.List;

public class UsersData {

    public static List<CardData> getDummyCardData() {
        List<CardData> cardDataList = new ArrayList<>();
        cardDataList.add(new CardData("User 1", "Subtitle 1", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("User 2", "Subtitle 2", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("User 3", "Subtitle 3", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("User 4", "Subtitle 4", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("User 5", "Subtitle 5", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("User 6", "Subtitle 6", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("User 7", "Subtitle 7", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("User 8", "Subtitle 8", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("User 9", "Subtitle 9", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("User 10", "Subtitle 10", "https://via.placeholder.com/200"));
        return cardDataList;
    }

}