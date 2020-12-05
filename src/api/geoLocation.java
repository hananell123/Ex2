package api;


public class geoLocation implements geo_location{
    double x;
    double y;
    double z;

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
        return 0;
    }
}
