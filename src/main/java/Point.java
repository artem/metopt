public class Point implements Comparable<Point> {
    private double x;
    private double y;

    public Point() {
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, AbstractFunction f) {
        this.x = x;
        y = f.eval(x);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
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
