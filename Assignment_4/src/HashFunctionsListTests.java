public class HashFunctionsListTests {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "/hash_functions.txt";
        HashFunctionsList list = new HashFunctionsList(path);
        test(list);
    }

    private static void test(HashFunctionsList list){
        int m1 = 1000001;
        int k = 1087152;
        int p = 15486907;
        long[] arr = new long[4];
        arr[0] = ((5*k+3)%p)%m1;
        arr[1] = ((3*k+10)%p)%m1;
        arr[2] = ((9*k+2)%p)%m1;
        arr[3] = ((13*k+5)%p)%m1;

        int index = 3;
        for (HashFunction function: list) {
            System.out.println(function.runFunction(k, m1)+ " " + arr[index]+ " " + (function.runFunction(k, m1) == arr[index]));
            index--;
        }
    }
}
