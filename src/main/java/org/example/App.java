package org.example;

import org.example.Repository.DBRepo;
import org.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        // 1. Set up Hibernate session factory
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        // 2. Create DBRepo instance
        DBRepo<User> userRepo = new DBRepo<>(sessionFactory, User.class);

        // 3. Perform CRUD operations
        // Create new users
        User user1 = new User("John Doe", "john@example.com");
        User user2 = new User("Jane Smith", "jane@example.com");

        // Create users in the database
        userRepo.create(user1);
        userRepo.create(user2);

        // Read a user by ID
        User user = userRepo.read(user1.getId());
        System.out.println("Read User: " + user);

        // Update user info
        user.setName("John Updated");
        userRepo.update(user.getId(), user);

        // Read all users
        Map<Integer, User> allUsers = userRepo.getAll();
        System.out.println("All Users:");
        allUsers.forEach((id, u) -> System.out.println(u));

        // Delete a user by ID
        userRepo.delete(user2.getId());
        System.out.println("Deleted user with ID: " + user2.getId());

        // Close session factory
        sessionFactory.close();
    }
}
