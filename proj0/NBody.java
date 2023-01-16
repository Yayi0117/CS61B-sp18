public class NBody {
    public static double readRadius(String str){
        In in = new In(str);
        int N = in.readInt();
        double R = in.readDouble();
    return R;
    }

    public static Planet[] readPlanets(String str){
        In in = new In(str);
        int N = in.readInt();
        double R = in.readDouble();
        Planet[] array = new Planet[N];
        for (int i = 0; i < N; i++) { 
            Planet p = new Planet(in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readString());
            array[i] = p;
        }
        return array;
    }

    public static void main(String[] arg){
        double T = Double.parseDouble(arg[0]);
        double dt = Double.parseDouble(arg[1]);
        String filename = arg[2];
        Planet[] planets = readPlanets(filename);
        double radius = readRadius(filename);

        //StdDraw.setXscal(-radius, radius);
        //StdDraw.setYscal(-radius, radius);
        StdDraw.setScale(-radius, radius);
        StdDraw.enableDoubleBuffering();
        double t = 0;
        while (t<T){
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for(int i = 0; i<planets.length; i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);  
            }
            for (int i=0; i<planets.length; i++){
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for(Planet p: planets){
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            t += dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                        planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
    }
}