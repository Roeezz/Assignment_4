public class UsefulFunctionsTest {
    public static void main(String[] args) {
        System.out.println(UsefulFunctions.getKeysList(System.getProperty("user.dir")
                + "/requested_passwords.txt"));
    }
}
