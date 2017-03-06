import static java.lang.System.exit;

/**
 * Created by Вадим on 06.03.2017.
 */
public class MainPr {
    public static void main(String[] args) {
        System.out.println("Hello World");
        // System.exit(2);
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        if (args[0].compareTo("login")==0) {
            if (args[1].compareTo("pass") ==0 ) {
                exit(0);
            } else exit(2);
        } else  System.exit(1);
    }
}
