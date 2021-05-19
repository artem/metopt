public class ProfileMatrix extends Matrix {
    double[] diag, inRows, inCols;
    int[] profile;
    int n;

    ProfileMatrix(Matrix other) {
        n = other.getSize();
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

    @Override
    public int getSize() {
        return n;
    }

    @Override
    public double get(int i, int j) {
        return getOrSet(i, j, null);
    }

    @Override
    void set(int i, int j, double value) {
        getOrSet(i, j, value);
    }

    private int getLocalIndex(int i, int j, boolean check) {
        int start = profile[i];
        int end = profile[i+1];
        int len = end - start;
        if (len < i-j) {
            if (!check)
                return -1;
            else
                throw new MatrixException("Unable to set this element");
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
