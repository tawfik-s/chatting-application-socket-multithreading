package org.example.UsersFackeData;

import java.util.ArrayList;
import java.util.List;

public class DummyData {
    public static List<CardData> getDummyData() {
        List<CardData> dataList = new ArrayList<>();

        dataList.add(new CardData("user 1", "data"));
        dataList.add(new CardData("user 2", "data"));
        dataList.add(new CardData("user 3", "data"));
        dataList.add(new CardData("user 4", "data"));
        dataList.add(new CardData("user 5", "data"));
        dataList.add(new CardData("user 6", "data"));

        return dataList;
    }
}