public class Conversions {

    public static void main(String[] args) {
        System.out.println("Feet  |  Meters");
        System.out.println("----------------");

        for(int i = 1; i <= 10; i++) {
            double result = footToMeter(i);

            System.out.println(i + "  |  " + result);
        }

        System.out.println("----------------");
        System.out.println("Meters  |  Feet");
        System.out.println("----------------");

        for(int i = 20; i <= 65; i+=5) {
            double result = meterToFoot(i);

            System.out.println(i + "  |  " + result);
        }
    }

    /** Convert from feet to meters */
    public static double footToMeter(double foot) {
        return foot * 0.305;
    }

    /** Convert from meters to feet */
    public static double meterToFoot(double meter) {
        return meter * 3.279;
    }
}