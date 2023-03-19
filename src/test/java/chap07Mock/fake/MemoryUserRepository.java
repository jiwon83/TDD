package chap07Mock.fake;

import chap07Mock.UserRepository;
import chap07Mock.entity.User;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserRepository implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public User findById(String id) {
        return users.get(id);
    }
}
