
class Resource {
    String path;
    Integer usersId;
    String role;
    int id;

    public Resource() {
    }

    Resource(String path, Integer userId, String role, int id) {
        this.path = path;
        this.usersId = userId;
        this.role = role;
        this.id = id;

    }
}
