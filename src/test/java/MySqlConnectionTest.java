import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/sim_plane_db?serverTimezone=Asia/Seoul";
        String user = "sim_plane";
        String password = "1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 드라이버 로딩
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ 연결 성공: " + conn);
            conn.close();
        } catch (Exception e) {
            System.out.println("❌ 연결 실패:");
            e.printStackTrace();
        }
    }
}
