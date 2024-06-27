import daysAndDates.DaysOfWeek;

public class TestDaysOfWeek {
    public static void main(String[] args) {
        System.out.println("Days Of week: ");
        for (int i = 1; i <= 7; i++) {
            System.out.println("Number: " + i + "\tDay Of Week: " + DaysOfWeek.dayOfWeekStr(i));
        }
    }
}
