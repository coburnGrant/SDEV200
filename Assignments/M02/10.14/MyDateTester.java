public class MyDateTester {
    public static void main(String[] args) {
        MyDate currentDate = new MyDate();

        MyDate timeDate = new MyDate(34355555133101L);

        MyDate[] myDates = {currentDate, timeDate};

        for(MyDate date : myDates) {
            System.out.println(date);
        }
    }
}