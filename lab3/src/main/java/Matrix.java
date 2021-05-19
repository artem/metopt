public abstract class Matrix {
    abstract int getSize();
    abstract double get(int i, int j);
    abstract void set(int i, int j, double value);

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(String.format("Matrix %dx%d\n", getSize(), getSize()));
        for (int i = 0; i < getSize(); ++i) {
            for (int j = 0; j < getSize(); j++) {
                result.append(get(i, j)).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
