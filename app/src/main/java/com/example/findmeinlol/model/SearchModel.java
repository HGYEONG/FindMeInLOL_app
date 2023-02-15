package com.example.findmeinlol.model;

import java.util.ArrayList;

public class SearchModel {
    private static ArrayList<String> nameList = new ArrayList<>();

    public boolean findName(String s) {
        // ToDo: 서버로부터 소환사 조회
        return true;
    }

    public void addName(String s) {
        nameList.add(s);
    }

    public String getName(int idx) {
        return nameList.get(idx);
    }

    public boolean duplicateName(String s) {
        return nameList.contains(s);
    }

    public int getSize() {
        return nameList.size();
    }

    public void clearList() {
        nameList.clear();
    }

    public ArrayList<String> getNameList() {
        return nameList;
    }
}
