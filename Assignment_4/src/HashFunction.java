public class HashFunction{
    /**
     * A prime number used in the calculation of the function.
     */
    private final int prime = 15486907;
    /**
     * Constants used in the calculation of the function.
     */
    private final int ALPHA;
    private final int BETA;

    /**
     * Constructs a HashFunction using 2 input constants.
     * @param alpha a constant used in the calculation.
     * @param beta a constant used in the calculation.
     */
    public HashFunction(int alpha, int beta) {
        this.ALPHA = alpha;
        this.BETA = beta;
    }

    /**
     * Runs the function on given input.
     * @param key the number we want to calculate the hash of.
     * @param m1 a parameter used for the calculation.
     * @return an int that is the hashed key.
     */
    public int runFunction(long key, int m1) {
        return (int) (((ALPHA *key + BETA) % getPrime()) % m1);
    }

    public int getPrime() {
        return prime;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof HashFunction){
            return ALPHA == ((HashFunction) other).ALPHA && BETA == ((HashFunction) other).BETA;
        }
        return false;
    }

    @Override
    public String toString() {
        return "H(k,m1) = (" + ALPHA + "*k + " + BETA + ") mod "+ prime +  ")mod m1";
    }
}
