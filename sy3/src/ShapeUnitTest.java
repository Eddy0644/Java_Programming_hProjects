public class ShapeUnitTest {
    public static void main(String[] args) {
        String outputTemplate = "%8s: S=%.2f L=%.2f \n";

        Circle circle = new Circle(0, 0, 1);
        System.out.printf(outputTemplate, "Circle", circle.getArea(), circle.getLength());

        Triangle triangle = new Triangle(0, 0, new Point(3, 0), new Point(3, 4));
        System.out.printf(outputTemplate, "Triangle", triangle.getArea(), triangle.getLength());

        Quadrant quadrant = new Quadrant(0, 0, new Point(3, 0), new Point(3, 4), new Point(0, 4));
        System.out.printf(outputTemplate, "Quadrant", quadrant.getArea(), quadrant.getLength());

        Pentagon pentagon = new Pentagon(0, 0, new Point(4, 0), new Point(4, 2), new Point(2, 4), new Point(0, 4));
        System.out.printf(outputTemplate, "Pentagon", pentagon.getArea(), pentagon.getLength());

        Elliptic elliptic = new Elliptic(0, 0, 3, 2);
        System.out.printf(outputTemplate, "Elliptic", elliptic.getArea(), elliptic.getLength());
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
    final int n = 3;
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

    public Triangle(Point p1, Point p2, Point p3) {
        super(p1.getX(), p1.getY());
        this.p1 = p1;
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

class Quadrant extends Polygon {
    final int n = 4;
    Point p1, p2, p3, p4;
    double a, b, c, d;

    public Quadrant(int x, int y, Point p2, Point p3, Point p4) {
        super(x, y);
        this.p1 = new Point(x, y);
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.a = new Line(p1, p2).getLength();
        this.b = new Line(p3, p2).getLength();
        this.c = new Line(p4, p3).getLength();
        this.d = new Line(p4, p1).getLength();
    }

    double getArea() {
//        double p = (a + b + c) / 2;
//        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
        return new Triangle(p1, p2, p3).getArea() + new Triangle(p1, p3, p4).getArea();
    }

    double getLength() {
        return a + b + c + d;
    }

    int getInnerAngles() {
        return 360;
    }
}

// 1 2 3; 1 3 5; 3 4 5
class Pentagon extends Polygon {
    final int n = 5;
    Point p1, p2, p3, p4, p5;
    double a, b, c, d, e;

    public Pentagon(int x, int y, Point p2, Point p3, Point p4,Point p5) {
        super(x, y);
        this.p1 = new Point(x, y);
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
        this.a = new Line(p1, p2).getLength();
        this.b = new Line(p3, p2).getLength();
        this.c = new Line(p4, p3).getLength();
        this.d = new Line(p4, p5).getLength();
        this.e = new Line(p1, p5).getLength();
    }

    double getArea() {
        return new Triangle(p1, p2, p3).getArea() + new Triangle(p1, p3, p5).getArea()+ new Triangle(p3, p4, p5).getArea();
    }
    double getLength() {
        return a + b + c + d+e;
    }

    int getInnerAngles() {
        return 540;
    }
}