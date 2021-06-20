package com.ciklum.game.model;

public class GameStorageFactory {

    public static GameStorage getGameStorage(String type) {
        switch (type) {
            case "in_memory":
                return new GameInMemoryStorage();
            //case "jpa":...
        }
        throw new IllegalArgumentException();
    }
}


