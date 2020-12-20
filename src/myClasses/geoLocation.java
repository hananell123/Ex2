package myClasses;


import api.geo_location;

/**
 * represent location
 */
public class geoLocation implements geo_location {
    double x;
    double y;
    double z;

    /**
     * constructor of the location
     * @param x
     * @param y
     * @param z
     */
    public geoLocation(double x, double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public double distance(geo_location g) {
        double xDis=Math.pow( g.x()-this.x,2);
        double yDis=Math.pow( g.y()-this.y,2);


        return Math.sqrt(xDis+yDis);
    }
}
