package tech.makers.demo.player;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<String> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void addItem(String item) {
        items.add(item);
    }

    public boolean hasItem(String item) {
        return items.contains(item);
    }

    public List<String> getItems() {
        return new ArrayList<>(items); // Return a copy to avoid external modification
    }

    public void removeItem(String item) {
        items.remove(item);
    }


}