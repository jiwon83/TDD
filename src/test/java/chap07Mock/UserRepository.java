package chap07Mock;

import chap07Mock.entity.User;

public interface UserRepository {
    void save(User user);
    User findById(String id);
}
