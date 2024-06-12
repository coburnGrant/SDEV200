public class TestRegularPolygon {
    public static void main(String[] args) {
        RegularPolygon noArgPolygon = new RegularPolygon();
        RegularPolygon sideAndLengthPolygon = new RegularPolygon(6, 4);
        RegularPolygon sideLengthXYPolygon = new RegularPolygon(10, 4, 5.6, 7.8);

        RegularPolygon[] regularPolygons = {noArgPolygon, sideAndLengthPolygon, sideLengthXYPolygon};

        for (RegularPolygon polygon : regularPolygons) {
            double perimeter = polygon.getPerimeter();
            double area = polygon.getArea();

            System.out.println("Polygon perimeter: " + perimeter + ", area: " + area);
        }
    }
}
