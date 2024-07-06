package lab14;
import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private double factor;
    private int state;
    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
    }
    public double next() {
        state = (state + 1) % period;
        double output = normalize(state);
        if (output == -1) {
            period = (int) (period * factor);
        }
        return output;
    }
    private double normalize(double val) {
        return (val / period - 0.5) * 2;
    }
}
