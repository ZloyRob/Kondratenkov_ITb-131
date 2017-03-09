import java.util.ArrayList;
import java.util.List;

public class Resource {
    String path;
    List<Integer> usersId = new ArrayList();
    List<String> role = new ArrayList();

    public Resource(String path, List<Integer> userId, List<String> role) {
        this.path = path;
        this.usersId = userId;
        this.role = role;
    }
}
