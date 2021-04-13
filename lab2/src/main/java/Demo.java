public class Demo{
    public static void main(String[] args) {
        GradientDescent gd = new GradientDescent(new Function1(), 1e-3);
        try {
            gd.process(1, false);
        } catch (MatrixException e) {
            System.err.println(e.getMessage());
        }
    }
}