package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;
    private Statement statement;
    private User user;
    private PreparedStatement ps;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTable = "CREATE TABLE MyTableUsers ("
                + "UserId       BIGINT(19)  NOT NULL    AUTO_INCREMENT    PRIMARY KEY, "
                + "UserName     VARCHAR(50) NOT NULL, "
                + "UserLastname VARCHAR(50) NOT NULL, "
                + "Age          INT NOT NULL )";
        try {
            connection = Util.connectJDBC();
            DatabaseMetaData dm = connection.getMetaData();
            ResultSet rs = dm.getTables(null, "new_schema",
                    "MyTableUsers",
                    null);
            if (!rs.next()) {
                statement = connection.createStatement();
                statement.executeUpdate(createTable);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try {
            connection = Util.connectJDBC();
            DatabaseMetaData dm = connection.getMetaData();
            ResultSet rs = dm.getTables(null, "new_schema", "MyTableUsers", null);
            if (rs.next()) {
                statement = connection.createStatement();
                int res = statement.executeUpdate("DROP TABLE MyTableUsers");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null){
                statement.close();}
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        user = new User(name, lastName, age);
        String insertData = "INSERT into MyTableUsers(UserName , UserLastName , Age) VALUES (?, ?, ?)";
        try {
            connection = Util.connectJDBC();
            ps = connection.prepareStatement(insertData, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getAge());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getLong(1));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("User ?? ???????????? - %s ???????????????? ?? ???????? ????????????\n", user.getName());
    }

    public void removeUserById(long id) {
        String removeUser = " DELETE FROM MyTableUsers WHERE UserId= ?";
        try {
            connection = Util.connectJDBC();
            ps = connection.prepareStatement(removeUser);
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public List<User> getAllUsers() {

        ArrayList<User> listUsers = new ArrayList<>();

        try {
            connection = Util.connectJDBC();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM MyTableUsers");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("UserId"));
                user.setName(rs.getString("UserName"));
                user.setLastName(rs.getString("UserLastName"));
                user.setAge((byte) rs.getLong("Age"));
                listUsers.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                statement.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return listUsers;
    }

    public void cleanUsersTable() {
        try {
            connection = Util.connectJDBC();
            statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE MyTableUsers");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }
}
