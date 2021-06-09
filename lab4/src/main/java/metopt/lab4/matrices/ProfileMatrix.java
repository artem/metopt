package metopt.lab4.matrices;

public class ProfileMatrix extends Matrix {
    double[] diag, inRows, inCols;
    int[] profile;
    int n;

    public ProfileMatrix(Matrix other) {
        n = other.size();
        diag = new double[n];
        profile = new int[n+1];
        profile[0] = profile[1] = 1;

        for (int i = 0; i < n; i++) {
            diag[i] = other.get(i, i);
        }
        int counter = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                if (i == j) {
                    profile[i+1] = profile[i];
                } else if (other.get(i, j) != 0 || other.get(j, i) != 0) {
                    counter += i - j;
                    profile[i+1] = profile[i] + i - j;
                    break;
                }
            }
        }
        inRows = new double[counter];
        inCols = new double[counter];
        int k = 0;
        for (int i = 1; i < n; i++) {
            for (int j = i - profile[i+1] + profile[i]; j < i; j++) {
                inRows[k] = other.get(i, j);
                inCols[k] = other.get(j, i);
                k++;
            }
        }
    }

    public static ProfileMatrix getFullSizeMatrix(int size) {
        Matrix pattern = new FullMatrix(size, size);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                pattern.set(i, j, 1);
        ProfileMatrix result = new ProfileMatrix(pattern);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                result.set(i, j, 1);
        return result;
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public double get(int i, int j) {
        return getOrSet(i, j, null);
    }

    @Override
    public void set(int i, int j, double value) {
        if (value != 0) {
            getOrSet(i, j, value);
        }
    }

    @Override
    public Matrix transpose() { //todo
        double[] tmp = inCols;
        inCols = inRows;
        inRows = tmp;
        return this;
    }

    private int getLocalIndex(int i, int j, boolean check) {
        int start = profile[i];
        int end = profile[i+1];
        int len = end - start;
        if (len < i-j) {
            if (!check)
                return -1;
            else {
                throw new MatrixException(String.format("Unable to set element [%d][%d]", i, j));
            }
        }
        return start + len - i + j - 1;
    }

    private double getOrSet(int i, int j, Double value) {
        if (i >= n || j >= n)
            throw new IndexOutOfBoundsException(String.format("i(%d) or j(%d) > n(%d)", i, j, n));
        if (i == j) {
            if (value != null)
                diag[i] = value;
            return diag[i];
        } else if (j < i) {
            int ind = getLocalIndex(i, j, value != null);
            if (ind == -1)
                return 0;
            if (value != null)
                inRows[ind] = value;
            return inRows[ind];
        } else {
            int ind = getLocalIndex(j, i, value != null);
            if (ind == -1)
                return 0;
            if (value != null)
                inCols[ind] = value;
            return inCols[ind];
        }
    }
}
