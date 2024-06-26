package daysAndDates;

public class DaysOfWeek {
	public static String dayOfWeekStr(int NumberOfDay) {
		String dayStr = "";
		switch (NumberOfDay) {
			case 1:
				dayStr = "Sunday";
				break;
			case 2:
				dayStr = "Monday";
				break;
			case 3:
				dayStr = "Tuesday";
				break;
			case 4:
				dayStr = "Wednesday";
				break;
			case 5:
				dayStr = "Thursday";
				break;
			case 6:
				dayStr = "Thursday";
				break;
			case 7:
				dayStr = "Saturday";
				break;
		}
		return dayStr;
	}
}
