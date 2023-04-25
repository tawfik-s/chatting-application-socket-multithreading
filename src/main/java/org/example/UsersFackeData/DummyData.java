package org.example.UsersFackeData;

import java.util.ArrayList;
import java.util.List;

public class DummyData {

    public static List<CardData> getDummyCardData() {
        List<CardData> cardDataList = new ArrayList<>();
        cardDataList.add(new CardData("Card 1", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("Card 2", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("Card 3", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("Card 4", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("Card 5", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("Card 6", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("Card 7", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("Card 8", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("Card 9", "https://via.placeholder.com/200"));
        cardDataList.add(new CardData("Card 10", "https://via.placeholder.com/200"));
        return cardDataList;
    }

}

