package frc.robot.util;

class Waypoint {
    private double x, y, heading;

    public Waypoint(Point point) {
        this(point, 0.);
    }    

    public Waypoint(Point point, double heading) {
        this.x = point.getX();
        this.y = point.getY();
        this.heading = heading;
    }

    public double getX() {return this.x;}
    public double getY() {return this.y;}
    public Point getPoint() {return new Point(this.x, this.y);}
    public double getHeading() {return this.heading;}

    public void setX(double newX) {this.x = newX;}
    public void setY(double newY) {this.y = newY;}
    public void setPoint(Point point) {this.x = point.getX(); this.y = point.getY();}
    public void setHeading(double newHeading) {this.heading = newHeading;}

    
}