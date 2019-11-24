package service;

import model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserService {

    private static UserService INSTANCE;

    /* хранилище данных */
    private Map<Long, User> dataBase = Collections.synchronizedMap(new HashMap<>());
    /* счетчик id */
    private AtomicLong maxId = new AtomicLong(0);
    /* список авторизованных пользователей */
    private Map<Long, User> authMap = Collections.synchronizedMap(new HashMap<>());

    private UserService() {

    }

    public static UserService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserService();
        }

        return INSTANCE;
    }

    public List<User> getAllUsers() {
        return getListByStorage(dataBase);
    }

    public User getUserById(Long id) {
        User user = dataBase.get(id);
        user.setId(id);
        return user;
    }

    public void addUser(User user) {
        if (isUserExists(user)) {
            return;
        }

        dataBase.put(maxId.incrementAndGet(), user);
    }

    public void deleteAllUser() {
        dataBase.clear();
    }

    public List<User> getAllAuth() {
        return getListByStorage(authMap);
    }

    public void authUser(User user) {
        for (Map.Entry<Long, User> entry : dataBase.entrySet()) {
            if (entry.getValue().getEmail().equals(user.getEmail())
                    && entry.getValue().getPassword().equals(user.getPassword())) {
                authMap.put(entry.getKey(), user);
            }
        }
    }

    public void logoutAllUsers() {
        authMap.clear();
    }

    public boolean isUserAuthById(Long id) {
        return authMap.containsKey(id);
    }

    private boolean isUserExists(User user) {
        boolean hasFound = false;

        for (Map.Entry<Long, User> entry : dataBase.entrySet()) {
            if (entry.getValue().getEmail().equals(user.getEmail())
                    && entry.getValue().getPassword().equals(user.getPassword())) {
                hasFound = true;
                break;
            }
        }

        return hasFound;
    }

    private List<User> getListByStorage(Map<Long, User> storage) {
        List<User> list = new ArrayList<>();

        for (Map.Entry<Long, User> entry : storage.entrySet()) {
            User user = new User(
                    entry.getKey(),
                    entry.getValue().getEmail(),
                    entry.getValue().getPassword()
            );

            list.add(user);
        }

        return list;
    }
}