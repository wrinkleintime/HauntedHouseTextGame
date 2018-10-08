import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *  This class is the main class of the "Exciting Campus" application.
 *  "Exciting Campus" is a very simple, text based adventure game.  Users
 *  can walk around some locations. That's all. It should really be extended
 *  to make it more interesting!  The original version was "World of Zuul".
 *
 *  This main class creates and initialises all the others: it creates all
 *  rooms, a Game, and plays the Game.  The Game evaluates and
 *  executes the commands that the parser returns.
 *
 *  This version is modified by Andrew Harrington to use the UI and FileUtil
 *  utility classes.  Room data comes from a text file rather than being
 *  hard-coded into the program.  This implementation also allows all rooms
 *  to be accessed by name using the HashMap rooms in the Game class.
 *
 *  A main method is also provided in the Game class.
 *  All commands are executed by classes satisfying the Response interface,
 *  so the main class does not have the code for the commands.
 *  The CommandMapper class maps command words to Responses.
 *  The Parser and CommandMapper classes are static.
 *
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

public class Game
{
    private Room currentRoom;
    private HashMap<String, Room> rooms; //allows all rooms to be found by name
//    private String welcomeString;

    /** Make a Game playable from the command line.
     * @param args No commandline arguments needed
     */
    public static void main(String[] args) {
        Game game = new Game("level_01.txt");
//        game.play();
    }

    /** Return the current room. */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Return the Room associated with a name.
     * @param roomName The name of the desired Room.
     * @return the associated Room.
     */
    public Room getNamedRoom(String roomName) {
        return rooms.get(roomName);
    }

    /**
     * Set the current room.
     * @param newRoom The new current Room.
     */
    public void setCurrentRoom(Room newRoom) {
        currentRoom = newRoom;
    }

    public ArrayList<ArrayList<Room>> buildMap(HashMap<String, Room> rooms){
        ArrayList<ArrayList<Room>> map = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            map.add(new ArrayList<>(Arrays.asList(null, null, null, null, null)));
        }

        for(Map.Entry x: rooms.entrySet()){
            Room newRoom = (Room)x.getValue();
            int row = newRoom.getXCoord();
            int col = newRoom.getYCoord();

            map.get(row).set(col, newRoom);
        }

        return map;
    }

    public void viewMap(ArrayList<ArrayList<Room>> map){
        String lineString = "*************************************************";
        String dashString = "|               |               |               |";
        String hallString = "| Hallway                                       | Stairs";

        for(int i = 0; i < 11; i++){
            if(i == 0 || i == 4 || i == 6 || i == 10){
                System.out.println(lineString);
            }
            else if(i == 2){
                System.out.println(roomsForMap(map, 1));
            }
            else if(i == 5){
                System.out.println(hallString);
            }
            else if(i == 8){
                System.out.println(roomsForMap(map, 3));
            }
            else{
                System.out.println(dashString);
            }
        }

        System.out.println();
        System.out.println("You are " + currentRoom.getDescription());
    }

    public String roomsForMap(ArrayList<ArrayList<Room>> map, int row){
        String roomLine = "| ";
        for(int i = 1; i < 4; i++){
            String roomName = map.get(row).get(i).getName();
            while(roomName.length() < 14){
                roomName += " ";
            }
            roomLine += roomName + "| ";
        }
        return roomLine;
    }

    /**
     * Create the game and initialise its internal map
     * @param worldData World initialization data file name.
     */
    public Game(String worldData)
    {   // world data: starting room line,
        // welcome and help intro paragraphs, then Room data
        Scanner dataIn = ResourceUtil.openFileScanner(worldData);
//        String startingRoom = FileUtil.getNonCommentLine(dataIn).trim();
//        welcomeString = FileUtil.readParagraph(dataIn);
//        String helpIntro = FileUtil.readParagraph(dataIn);
        rooms = Room.createRooms(dataIn);
//        currentRoom = rooms.get(startingRoom);

//        CommandMapper.init(this, helpIntro); // data for some Responses
        setCurrentRoom(rooms.get("Hallway1"));
        ArrayList<ArrayList<Room>> map = buildMap(rooms);
        viewMap(map);

        for(Map.Entry<String, Room> entry: rooms.entrySet()){
            if(entry.getValue().getLocked()){
                System.out.println(entry.getValue().getName() + " is locked.");
            }
        }
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
//    public void play()
//    {
//        System.out.println(welcomeString);
//        System.out.println(currentRoom.getLongDescription());
//
//        // Enter the main command loop.  Here we repeatedly read commands and
//        // execute them until the game is over.
//
//        while (! processCommand())
//            ;  // convention with isolated semicolon for an empty loop
//        System.out.println("Thank you for playing.  Good bye.");
//    }

    /**
     * Main loop actions: Get user input ... execute the command.
     * @return true if the command ends the game, false otherwise.
     */
//    private boolean processCommand()
//    {
//        String line = UI.promptLine("> ");
//
//        String[] tokens = line.trim().split("\\s+");
//
//        if ((tokens.length == 0) || !CommandMapper.isCommand(tokens[0])) {
//            System.out.println("I don't know what you mean...");
//            return false;
//        }
//
//        Response response = CommandMapper.getResponse(tokens[0]);
//        return response.execute(tokens);
//    }

}