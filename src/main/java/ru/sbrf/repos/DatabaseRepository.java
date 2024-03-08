package ru.sbrf.repos;

import org.springframework.stereotype.Repository;
import ru.sbrf.exceptions.DbException;

import java.sql.*;

@Repository
public class DatabaseRepository {

    private Connection connection;
    private final String url = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    public DatabaseRepository() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url);
            createTableIfNotExists();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DbException("Возникла ошибка при инициализации", e);
        }
    }

    void createTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS cache (" +
                "  id SERIAL PRIMARY KEY, " +
                "  number integer NOT NULL UNIQUE," +
                "  result integer[] NOT NULL)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }

    public Integer[] selectWhereNumber(int number) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from cache where number = ?");
            statement.setInt(1, number);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Array result = resultSet.getArray("result");
                return (Integer[]) result.getArray();
            }
            return new Integer[]{};
        } catch (Exception exception) {
            throw new DbException("Не удалось выполнить селект", exception);
        }
    }

    public void insert(int number, Integer[] result) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into cache(number, result) " +
                    "VALUES (?, ?)");

            Array array = connection.createArrayOf("INTEGER", result);
            statement.setInt(1, number);
            statement.setArray(2, array);
            int inserted = statement.executeUpdate();
            System.out.println("Rows inserted: " + inserted);
        } catch (Exception exception) {
            throw new DbException("Не удалось вставить данные", exception);
        }
    }
}
