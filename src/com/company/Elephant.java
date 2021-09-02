package com.company;

import java.io.File;
import java.sql.SQLOutput;
import java.util.*;

public class Elephant {

    //way to off load code as reuseable chunks
    // capstone made a public void run different way of coding:
    // youll see where there is a capital M main and then creat an obj of the class we're ins ide

    private static final String MAIN_MENU_OPTION_RSVP = "RSVP for the White Elephant Party!";
    private static final String MAIN_MENU_OPTION_VIEW= "View the party guests.";
    private static final String MAIN_MENU_OPTION_WHO_DO_I_HAVE= "View my assigned Elephant.";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit the Program";
    private static final String MAIN_MENU_OPTION_HOST_ONLY= "HOST ONLY: Generate the assignments.";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_RSVP, MAIN_MENU_OPTION_VIEW,MAIN_MENU_OPTION_WHO_DO_I_HAVE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_HOST_ONLY };

    private Menu menu;
    private List<Guest> partyList = new ArrayList<>();
    private List<Presents> wishList = new ArrayList<>();
    public Elephant(Menu menu){ this.menu=menu;}
    public Map<Integer,String> guestMap= new HashMap<>();


    public void run() {

        System.out.println("=========================================");
        System.out.println("       Family Party- White Elephant      ");
        System.out.println("=========================================");

        boolean run= true;

        while (run) {
            String choice= (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            System.out.println(choice);

            if (choice.equals(MAIN_MENU_OPTION_RSVP)){
                guestInput();
            } else if (choice.equals(MAIN_MENU_OPTION_VIEW)) { //need a way to load the new data without having to rerun program.
                loadData(); // how to only load the newly added guests once not duplicate?
                printList(partyList);
            } else if (choice.equals(MAIN_MENU_OPTION_WHO_DO_I_HAVE)) {
                viewMyAssignment();
            } else if (choice.equals(MAIN_MENU_OPTION_HOST_ONLY)){
                shuffleElephants();
                assignedElephants();
            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)){
                System.out.println("We hope to see you at the party, thanks for using the White Elephant App!");
                System.exit(1);
            }
        }

    }

    public void guestInput() {
        System.out.println("------------------------------------------");
        Logwriter logger = new Logwriter("elephantsecrets.txt");
        Logwriter giftLogger= new Logwriter("elephantpresents.txt");

        Scanner input = new Scanner(System.in);

        System.out.println("Please enter your name full name:");
        String name = input.nextLine();

        System.out.println("Please provide a list of gifts you'd like to receive that is within the $25 budget. Use a comma(,) to separate the items");
        String wish = input.nextLine();

        System.out.println("It is important that you are able to 100% make the party. Please confirm by typing (Y) for Yes. Otherwise Exit the program(E).");
        String attendance = input.nextLine();
        if (attendance.equalsIgnoreCase("E")){
            System.out.println("You're going to miss one heck of a party, make sure to RSVP if plans change!");
            System.exit(1);
        }

        System.out.println("Please enter your e-mail address for us to send one time reminder of who your white elephant is!");
        String email = input.nextLine();

        System.out.println("Please enter a unique password that you can use to retrieve your elephant recipient once assigned.");
        String password= input.nextLine();

        // using this to when others add others down the line.
        logger.writeToFile(name +"|" +wish+ "|" + attendance + "|" + email+ "|" +password);
        giftLogger.writeToFile(name+ "|" +wish);

    }

    public void loadData() {

        //way to read and write from the same file from java
        File file = new File("elephantsecrets.txt"); //saying file not found.
        try {
            Scanner party = new Scanner(file);

            while (party.hasNextLine()) {
                String line = party.nextLine();
                String[] lineArr = line.split("\\|");
                String lineName = lineArr[0];
                String lineWish = lineArr[1];
                String lineAttendance = lineArr[2];
                String lineEmail = lineArr[3];
                String password= lineArr[4];

                Guest newGuest = new Guest(lineName, lineAttendance, lineEmail, password);
                partyList.add(newGuest);

                Presents newPresent = new Presents(lineName, lineWish);
                wishList.add(newPresent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("File Not Found.");
        }
    }

    public void printList(List<Guest> list) {
        System.out.println("Here's who's coming to the party:");
        for (Guest guest : list) {
            System.out.println(guest);
        }
    }

    public void shuffleElephants() {
        loadData();
        Collections.shuffle(partyList);
        int i=1;
        for (Guest person: partyList){
            guestMap.put(i,person.getName());
            i++;
        }
    }

    public void assignedElephants(){
    Set<Integer> guestVal= guestMap.keySet();
    Map<String,String> assignedList= new HashMap<>();

        for (Integer val: guestVal){
            String currentPerson = guestMap.get(val);
            String assignedPerson = guestMap.get(val + 1);
            assignedList.put(currentPerson, assignedPerson);

            if (guestMap.containsKey(guestMap.size()-1)) { //exception for last two people then everyone else
                    assignedList.put(guestMap.get(guestMap.size()),guestMap.get(1));
            }

        }

        Logwriter assignments= new Logwriter("assignments.txt");
        assignments.writeToFile(assignedList.toString());
    }

    public void viewMyAssignment(){

        Scanner scanner= new Scanner(System.in);
        System.out.println("Please enter your name.");
        String name= scanner.nextLine();

        File assignmentFile = new File("assignments.txt"); //saying file not found.
        try {
            Scanner viewAssignment = new Scanner(assignmentFile);

            while (viewAssignment.hasNextLine()) {
                String line = viewAssignment.nextLine();
                String[] lineArr = line.split(",");
                lineArr[0]= lineArr[0].substring(1);
// need to work on this for loop the subtring and the { is throwing me.
                for (int i=0;i<lineArr.length;i++) {
                    String myAssignment = lineArr[i];
                    int index= myAssignment.indexOf("=");
                    String myNameSnip = myAssignment.substring(0, index).trim();

                    if (name.equals(myNameSnip)){
                        System.out.println(myAssignment);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("File Not Found.");
        }




    }


    //guest key and assign value in a hashmap that way there are no overlaps. easy way to assign them to another person.
    // could potentially use against

    //wrap while loop so that multiple people can enter information in


    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        Elephant whiteEl = new Elephant(menu);// create obj of this class that we are in
        whiteEl.run(); // we can call the methods inside the class.
    }

}

