package ROOM;

public class Room {
    private String roomCode;
    private String roomDescription;
    private int floorNumber;
    private String buildingCode;

    Room(String roomCode, String roomDescription, int floorNumber, String buildingCode) {
        this.roomCode = roomCode;
        this.roomDescription = roomDescription;
        this.floorNumber = floorNumber;
        this.buildingCode = buildingCode;
    }
}
