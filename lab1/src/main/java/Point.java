public class Point implements Comparable<Point> {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int compareTo(Point p) {
        return Double.compare(x, p.x);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Point) {
            Point p = (Point) o;
            return x == p.getX() && y == p.getY();
        }
        return false;
    }
}
