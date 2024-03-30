import java.sql.*;

public class TestPostgreSQL {
    public static void main(String[] args) {
        // Ühenda PostgreSQL-iga
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String kasutaja = "postgres";
        String parool = "sql";

        String DBNimi = "test";

        try {
            Class.forName("org.postgresql.Driver");
            Connection uhendus = DriverManager.getConnection(url, kasutaja, parool);
            System.out.println("Ühendus loodud");

            String kontrolliDBOlemasolu = "SELECT 1 FROM pg_database WHERE datname= ?";
            PreparedStatement valmisSQLParing = uhendus.prepareStatement(kontrolliDBOlemasolu);
            valmisSQLParing.setString(1, DBNimi);
            ResultSet tulem = valmisSQLParing.executeQuery();

            if (!tulem.next()) {
                String looDB = String.format("CREATE DATABASE %s", DBNimi);
                Statement looDBParing = uhendus.createStatement();
                looDBParing.executeUpdate(looDB);
                System.out.println("DB loodud");
                looDBParing.close();
            } else {
                System.out.println("DB juba olemas");
            }

            valmisSQLParing.close();
            uhendus.close();
            System.out.println("Ühendus suletud");
        } catch (ClassNotFoundException viga) {
            System.out.println("PostgreSQL JDBC draiverit ei leitud: " + viga.getMessage());
        } catch (SQLException viga) {
            System.out.println("Viga: " + viga.getMessage());
        }
    }
}
