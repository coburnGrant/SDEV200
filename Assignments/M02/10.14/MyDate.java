
import java.util.GregorianCalendar;

public class MyDate {
    /** Day of date */
    private int day;

    /** Month of the date. 0 based. (0 is January) */
    private int month;

    /** Year of the date */
    private int year;

    /** No arg contructor. Creates MyDate with current date */
    public MyDate() {
        GregorianCalendar calendar = new GregorianCalendar();
        initializeFromCalendar(calendar);
    }

    /** Constructor for creating my date object with an elapsed time since 01/01/1970 12:00AM */
    public MyDate(long millis) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(millis);
        initializeFromCalendar(calendar);
    }

    /** Contructor for creating a MyDate object with specified day, month, and year */
    public MyDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /** Initializes based on a GregorianCalendar object */
    private void initializeFromCalendar(GregorianCalendar calendar) {
        this.day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        this.month = calendar.get(GregorianCalendar.MONTH);
        this.year = calendar.get(GregorianCalendar.YEAR);
    }

    /** Getter for day */
    public int getDay() {
        return day;
    }

    /** Getter for month */
    public int getMonth() {
        return month;
    }

    /** Getter for year */
    public int getYear() {
        return year;
    }

    /** Sets new date to elapsed time */
    public void setDate(long elapsedTime) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(elapsedTime);

        initializeFromCalendar(calendar);
    }

    /** Returns a the date in the format mm/dd/yyyy */
    @Override
    public String toString() {
        String date = (month + 1) + "/" + day + "/" + year;
        return date;
    }
}
