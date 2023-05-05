public class ShapeUnitTest {
    public static void main(String[] args) {
        Circle circle = new Circle(0, 0, 1);
        System.out.printf("%8s: S=%.2f L=%.2f \n", "Circle", circle.getArea(), circle.getLength());
        Triangle triangle = new Triangle(0, 0, new Point(3, 0), new Point(3, 4));
        System.out.printf("%8s: S=%.2f L=%.2f \n", "Triangle", triangle.getArea(), triangle.getLength());
    }
}

abstract class Shape {
    protected int x, y;

    Shape(int x, int y) {
        setX(x);
        setY(y);
    }

    abstract double getArea();

    abstract double getLength();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

abstract class Polygon extends Shape {
    int n;

    public Polygon(int x, int y) {
        super(x, y);
    }

    abstract int getInnerAngles();

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}

class Point extends Shape {
    public Point(int x, int y) {
        super(x, y);
    }

    double getArea() {
        return 0.0;
    }

    double getLength() {
        return 0.0;
    }
}

class Circle extends Shape {
    int r;

    public Circle(int x, int y, int r) {
        super(x, y);
        setR(r);
    }

    double getArea() {
        return Math.PI * getR() * getR();
    }

    double getLength() {
        return Math.PI * 2 * getR();
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }
}

class Line extends Shape {
    Point begin, end;

    public Line(int x, int y, int x2, int y2) {
        super(x, y);
        begin = new Point(x, y);
        end = new Point(x2, y2);
    }

    public Line(Point begin, Point end) {
        super(begin.getX(), begin.getY());
        this.begin = begin;
        this.end = end;
    }

    double getArea() {
        return getLength();
    }

    double getLength() {
        return Math.sqrt(Math.pow(getX() - end.getX(), 2.0) + Math.pow(getY() - end.getY(), 2.0));
    }
}

class Elliptic extends Circle {
    int a, b;

    public Elliptic(int x, int y, int a, int b) {
        super(x, y, 0);
        setA(a);
        setB(b);
    }

    double getArea() {
        return Math.PI * getA() * getB();
    }

    double getLength() {
        return Math.PI * 2 * getB() + 4 * (getA() - getB());
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}

class Triangle extends Polygon {
    int n = 3;
    Point p1, p2, p3;
    double a, b, c;

    public Triangle(int x, int y, Point p2, Point p3) {
        super(x, y);
        this.p1 = new Point(x, y);
        this.p2 = p2;
        this.p3 = p3;
        this.a = new Line(p1, p2).getLength();
        this.b = new Line(p3, p2).getLength();
        this.c = new Line(p1, p3).getLength();
    }

    double getArea() {
        double p = (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    double getLength() {
        return a + b + c;
    }

    int getInnerAngles() {
        return 180;
    }
}