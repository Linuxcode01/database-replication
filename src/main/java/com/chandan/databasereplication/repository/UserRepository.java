package com.chandan.databasereplication.repository;

import com.chandan.databasereplication.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {

    private final JdbcTemplate primary;
    private final List<JdbcTemplate> replicas;
    private final AtomicInteger counter = new AtomicInteger();

    public UserRepository(
            @Qualifier("primaryJdbc") JdbcTemplate primary,
            @Qualifier("replica1Jdbc") JdbcTemplate replica1,
            @Qualifier("replica2Jdbc") JdbcTemplate replica2) {

        this.primary = primary;
        this.replicas = List.of(replica1, replica2);
    }

    public User save(String name, String email) {

        String sql = """
                INSERT INTO users(name, email)
                VALUES (?, ?)
                """;

        primary.update(sql, name, email);

        Long id = primary.queryForObject(
                "SELECT id FROM users WHERE email = ?",
                Long.class,
                email
        );

        return new User(id, name, email);
    }

    public Optional<User> findById(Long id) {

        int index = Math.floorMod(
                counter.getAndIncrement(),
                replicas.size()
        );

        JdbcTemplate replica = replicas.get(index);

        List<User> users = replica.query(
                "SELECT id, name, email FROM users WHERE id = ?",
                (rs, rowNum) -> new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email")
                ),
                id
        );

        return users.stream().findFirst();
    }
}
