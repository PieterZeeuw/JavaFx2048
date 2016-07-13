/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx2048.game;

import java.util.Optional;
import java.util.Random;

/**
 *
 * @author pczee
 */
public class GameTile {

    private Integer value;
    private Location location;
    private Boolean merged;

    public static GameTile newRandomTile() {
        int value = new Random().nextDouble() < 0.9 ? 2 : 4;
        return new GameTile(value);
    }

    public static GameTile newTile(int value) {
        return new GameTile(value);
    }

    public GameTile(int value) {
        this.value = value;
        this.merged = false;
    }

    public void merge(GameTile another) {
        this.value += another.getValue();
        merged = true;
    }

    public Integer getValue() {
        return value;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Tile{" + "value=" + value + ", location=" + location + '}';
    }

    public boolean isMerged() {
        return merged;
    }

    public void clearMerge() {
        merged = false;
    }

    public boolean isMergeable(Optional<GameTile> anotherTile) {
        return anotherTile.filter(t -> t.getValue().equals(getValue())).isPresent();
    }
}
