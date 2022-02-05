import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 模拟数据
 */
public class MockDataTest {

    /**
     * 生成随机字符串
     */
    private static String randomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        //模拟多少条数据
        int mockSize = 1000;

        for (int i = 0; i < mockSize; i++) {
            int isActive = 1;
            int birthDay = (int) (System.currentTimeMillis() / 1000);
            String email = randomString(8) + "@gmail.com";
            String firstName = randomString(4);
            String lastName = randomString(6);
            String lastUpdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String updateBy = randomString(5);
            int version = new Random().nextInt(100) + 1;
            final String sql = String.format("INSERT INTO customer(is_active, data_of_birth, email, first_name, last_name, last_updated, last_updated_by, `version`)" +
                            " VALUES(%d, %d, '%s', '%s', '%s', '%s', '%s',%d);",
                    isActive, birthDay, email, firstName, lastName, lastUpdate, updateBy, version);
            System.out.println(sql);
        }
    }

}
