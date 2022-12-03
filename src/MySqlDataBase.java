import java.io.BufferedReader;
import java.io.FileReader;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class MySqlDataBase { // 数据库功能实现类
    private static final String Db_username = "root";
    private static final String Db_password = "root";
    private static final String Db_jdbcDriver = "com.mysql.cj.jdbc.Driver";  // mysql8.0 以上链接方法
    private static final String Db_connectionUrl = "jdbc:mysql://localhost:3306/jdbc?useSSL=false&serverTimezone=UTC"; // 问号前面是数据库名字
    private Connection mConnection;
    private static final String fileName = "user.txt";
    private MD5Util md5Util = new MD5Util();
    // 链接数据库
    public MySqlDataBase() throws Exception {
        try {
            Class.forName(Db_jdbcDriver);
            mConnection = DriverManager.getConnection(Db_connectionUrl, Db_username, Db_password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CreateTable();
        if (checkTableEmpty() == 0) {
            System.out.println("0!!!");
            ReadFileByTxt();
        }
    }
    // 若不存在表则创建新表
    public void CreateTable() {
        String createTableSql = "create table if not exists user(id int not null auto_increment primary key ,uname varchar(20) not null ,upwd varchar(255) not null ,major varchar(20) not null )  ";
        try {
            PreparedStatement statement = mConnection.prepareStatement(createTableSql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // 检查表是否为空，返回行数
    public int checkTableEmpty() {
        String queryEmpty = "select count(*) from user";
        try {
            PreparedStatement statement = mConnection.prepareStatement(queryEmpty);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int totalNum = resultSet.getInt(1);
                return totalNum;
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    // 检查用户是否存在
    public boolean checkUserExist(String uname) {
        String queryUserExist = "select count(*) from user where uname = ?";
        try {
            PreparedStatement statement = mConnection.prepareStatement(queryUserExist);
            statement.setString(1, uname);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int totalNum = resultSet.getInt(1);
                return totalNum == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // 增加用户
    public boolean insertUser(String uname, String upwd, String major) throws NoSuchAlgorithmException {
        // 检查用户是否已经存在
        if (!checkUserExist(uname)) {
            return false;
        }
        String insertSql = "insert into user (uname,upwd,major) values (?,?,?)";
        try {
            PreparedStatement statement = mConnection.prepareStatement(insertSql);
            statement.setString(1, uname);
            statement.setString(2, md5Util.getMD5(upwd));
            statement.setString(3, major);
            int affectNum = statement.executeUpdate(); //execute返回boolean类型，true表示执行的是查询语句，false表示执行的是insert,delete,update等等
            return affectNum == 1;                       //executeUpdate返回的是int，表示有多少条数据受到了影响，为0表示无法插入
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // 删除用户
    public boolean deleteUser(String uname) {
        // 若用户不存在
        if (checkUserExist(uname)) {
            return false;
        }
        String deleteSql = "delete from user where uname = ?";
        // 删除用户之后重新对主键id进行排序，mysql8.0以上找到的方法都不适用
        try {
            PreparedStatement statement = mConnection.prepareStatement(deleteSql);
            statement.setString(1, uname);
            int deleteNum = statement.executeUpdate();
            return deleteNum == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // 修改用户信息，若想要修改大部分信息，应该选取一个与信息无关的列，应该根据id来更改
    // 修改多个重复调用即可
    public boolean updateUser(String ColumnName, int id, String updateData) {
        String sqlUpdate = "update user set " + ColumnName + "= ? where id = ?";
        try {
            PreparedStatement statement = mConnection.prepareStatement(sqlUpdate);
            statement.setString(1, updateData);
            statement.setInt(2, id);
            int updateNum = statement.executeUpdate();
            return updateNum == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // 准确查询，可根据uname与major进行查询
    public ArrayList<String[]> AccurateQuery(String ColumnName, String queryData) {
        String sqlQuery = "select * from user where " + ColumnName + " = ?";
        ArrayList<String[]> resStr = new ArrayList<>();
        try {
            PreparedStatement statement = mConnection.prepareStatement(sqlQuery);
            statement.setString(1, queryData);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String[] queryStr = new String[3];
                queryStr[0] = String.valueOf(resultSet.getInt(1));
                queryStr[1] = resultSet.getString(2);
                queryStr[2] = resultSet.getString(4);
                resStr.add(queryStr);
            }
            resultSet.close();
            return resStr;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 模糊查询，可根据uname与major进行查询
    public ArrayList<String[]> FuzzyQuery(String ColumnName, String queryData) {
        String sqlQuery = "select * from user where " + ColumnName + " like ?";
        ArrayList<String[]> resStr = new ArrayList<>();
        try {
            PreparedStatement statement = mConnection.prepareStatement(sqlQuery);
            statement.setString(1, "%" + queryData + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String[] queryStr = new String[3];
                queryStr[0] = String.valueOf(resultSet.getInt(1));
                queryStr[1] = resultSet.getString(2);
                queryStr[2] = resultSet.getString(4);
                resStr.add(queryStr);
            }
            resultSet.close();
            return resStr;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 查询所有用户到table
    public ArrayList<String[]> QueryAll() {
        String sqlQueryAll = "select * from user";
        ArrayList<String[]> resStr = new ArrayList<>();
        try {
            PreparedStatement statement = mConnection.prepareStatement(sqlQueryAll);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String[] queryStr = new String[3];
                queryStr[0] = String.valueOf(resultSet.getInt(1));
                queryStr[1] = resultSet.getString(2);
                queryStr[2] = resultSet.getString(4);
                resStr.add(queryStr);
            }
            resultSet.close();
            return resStr;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 查询所有用户导出到文件中
    public ArrayList<String[]> QueryAllToFile() {
        String sqlQueryAll = "select * from user";
        ArrayList<String[]> resStr = new ArrayList<>();
        try {
            PreparedStatement statement = mConnection.prepareStatement(sqlQueryAll);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String[] queryStr = new String[4];
                queryStr[0] = String.valueOf(resultSet.getInt(1));
                queryStr[1] = resultSet.getString(2);
                queryStr[2] = resultSet.getString(3);
                queryStr[3] = resultSet.getString(4);
                resStr.add(queryStr);
            }
            resultSet.close();
            return resStr;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 验证用户
    public int VerifyUser(String uname, String upwd) {
        // 用户不存在，-1表示用户不存在，-2表示密码不正确，0表示发生错误，1表示验证成功
        if (checkUserExist(uname)) {
            return -1;
        }
        String sqlVerify = "select count(*) from user where uname = ? and upwd = ?";
        try {
            PreparedStatement statement = mConnection.prepareStatement(sqlVerify);
            statement.setString(1, uname);
            statement.setString(2, md5Util.getMD5(upwd));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) == 1 ? 1 : -2;
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return 0;
    }
    // 若表为空，则从txt中读取数据
    public void ReadFileByTxt() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String str = null;
            bufferedReader.readLine(); // 先读一行
            while ((str = bufferedReader.readLine()) != null) {
                String[] splitStr = str.split(",");
                // 逐条插入到表中
                insertUser(splitStr[1], splitStr[2], splitStr[3]);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
