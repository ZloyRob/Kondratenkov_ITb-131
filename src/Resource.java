import java.util.ArrayList;

/**
 * Created by Вадим on 08.03.2017.
 */
public class Resource {
    String path;
    ArrayList<Integer> usersId = new ArrayList();
    ArrayList<Roles> role = new ArrayList();

    public Resource(String path, ArrayList<Integer> userId, ArrayList<Roles> role) {
        this.path = path;
        this.usersId = userId;
        this.role = role;
    }
}
