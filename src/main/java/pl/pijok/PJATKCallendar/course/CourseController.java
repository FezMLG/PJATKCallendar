package pl.pijok.PJATKCallendar.course;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pijok.PJATKCallendar.utils.Debug;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping
public class CourseController {

    private final SimpleDateFormat SDF;
    //private final HashMap<Date, Course> courses;
    private LinkedList<Course> courses;


    @Autowired
    public CourseController() throws IOException, ParserException, ParseException {
        SDF = new SimpleDateFormat("yyyyMMdd");
        courses = new LinkedList<>();

        this.load();
    }

    public void load() throws IOException, ParserException, ParseException {

        CalendarBuilder builder = new CalendarBuilder();

        net.fortuna.ical4j.model.Calendar calendar = builder.build(getFileFromResourceAsStream("Plan30292.ics"));

        for (Object o : calendar.getComponents()) {
            Component component = (Component) o;

            Course course = convertComponentToCourse(component);
            Date startDate = SDF.parse(course.getStart());
            Debug.log(startDate);
            //courses.put(startDate, course);
            courses.add(course);
        }
    }

    @GetMapping("/date")
    public LinkedList<Course> getCourses(@RequestParam(value = "date", defaultValue = "20010731;20010831") String stringDate){
        Date start;
        Date end;

        try {
            String[] parts = stringDate.split(";");
            start = SDF.parse(parts[0]);
            end = SDF.parse(parts[1]);

            LinkedList<Course> allCourses;
            allCourses = getCoursesBetween(start, end);
            return allCourses;

        } catch (ParseException e) {
            return null;
        }
    }

    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    private Course convertComponentToCourse(Component component){
        String start = component.getProperty("DTSTART").getValue();
        String end = component.getProperty("DTEND").getValue();
        String description = component.getProperty("SUMMARY").getValue();

        String name = description.split(" ")[0];
        boolean exercise = description.contains("ćwiczenia");
        String room = description.split("\\.")[1].substring(1);

        //Debug.log(description);

        return new Course(start, end, room, name, exercise);
    }

    private LinkedList<Course> getCoursesBetween(Date start, Date end) throws ParseException {
        LinkedList<Course> tempCourses = new LinkedList<>();

        for(Course course : courses){
            Date tempStart = SDF.parse(course.getStart());
            if(tempStart.before(end) && tempStart.after(start)){
                tempCourses.add(course);
            }
        }

        return tempCourses;
    }

    /*private LinkedList<Course> getCoursers(Date date){
        LinkedList<Course> tempCourses = new LinkedList<>();

        for(Date tempDate : courses.keySet()){
            Debug.log("Looking for " + date);
            Debug.log("Found " + tempDate);
            if(date.equals(tempDate)){
                tempCourses.add(courses.get(tempDate));
            }
        }

        return tempCourses;
    }*/

}
