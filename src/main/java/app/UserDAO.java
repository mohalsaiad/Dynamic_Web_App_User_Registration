package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    private static Map<Integer, User> userMap = new HashMap<>();
    private static int idCounter = 1;

    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    public void addUser(User user) {
        user.setId(idCounter++);
        userMap.put(user.getId(), user);
    }

    public User getUser(int id) {
        return userMap.get(id);
    }

    public void updateUser(User user) {
        userMap.put(user.getId(), user);
    }

    public void deleteUser(int id) {
        userMap.remove(id);
    }
}
