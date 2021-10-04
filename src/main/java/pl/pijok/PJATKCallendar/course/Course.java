package pl.pijok.PJATKCallendar.course;


public class Course {

    private String start;
    private String end;
    private String room;
    private String name;
    private boolean exercises;

    public Course(String start, String end, String room, String name, boolean exercises) {
        this.start = start;
        this.end = end;
        this.room = room;
        this.name = name;
        this.exercises = exercises;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getRoom() {
        return room;
    }

    public String getName() {
        return name;
    }

    public boolean isExercises() {
        return exercises;
    }
}
