// https://www.postgresqltutorial.com/postgresql-jdbc/
// https://www.tutorialspoint.com/postgresql/postgresql_java.htm
// https://www.sohamkamani.com/java/jdbc-postgresql/
// https://jdbc.postgresql.org/documentation/publicapi/index.html?org/postgresql/util/PGInterval.html

import org.postgresql.util.PGInterval;

import java.sql.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class Andmebaas {
    private final String nimi;

    private Connection andmebaas;

    public Andmebaas(String kasutaja, String parool, String nimi) {
        this.nimi = nimi;
        andmebaas = looUhendus(kasutaja, parool, "postgres");
        looAndmebaas();
        katkestaUhendus();
        andmebaas = looUhendus(kasutaja, parool, nimi);
        looKasutajadOlem();
        looUlesandedOlem();
        looPomodorodOlem();
    }

    public Andmebaas() {
        this("postgres", "sql", "pomodoro");
    }

    public Connection looUhendus(String kasutaja, String parool, String nimi) {
        try {
            Class.forName("org.postgresql.Driver");
            String url = String.format("jdbc:postgresql://localhost:5432/%s", nimi);
            Connection andmebaas = DriverManager.getConnection(url, kasutaja, parool);
            System.out.println("Ühendus loodud");
            return andmebaas;
        } catch (ClassNotFoundException viga) {
            System.out.println("PostgreSQL JDBC draiverit ei leitud: " + viga.getMessage());
        } catch (SQLException viga) {
            System.out.println("Andmebaasiga ühendamisel tekkis viga: " + viga.getMessage());
        }
        return null;
    }

    public void katkestaUhendus() {
        try {
            andmebaas.close();
            System.out.println("Ühendus andmebaasiga suletud");
        } catch (SQLException viga) {
            System.out.println("Andmebaasi sulgemisel tekkis viga: " + viga.getMessage());
        }
    }

    public boolean kontrolliAndmebaas() {
        boolean tagastus = false;

        final String kontrolliDB = "SELECT 1 FROM pg_database WHERE datname= ?";
        try (PreparedStatement kontrolliDBLause = andmebaas.prepareStatement(kontrolliDB)) {
            kontrolliDBLause.setString(1, nimi);
            try (ResultSet kasDBOlemas = kontrolliDBLause.executeQuery()) {
                tagastus = kasDBOlemas.next();
            }
        } catch (SQLException viga) {
            System.out.println("Andmebaasi kontrollimisel tekkis viga: " + viga.getMessage());
        }

        return tagastus;
    }

    public void looAndmebaas() {
        if (kontrolliAndmebaas()) {
            System.out.println("Andmebaas juba olemas!");
            return;
        }
        try (Statement looDBLause = andmebaas.createStatement()) {
            String looDB = String.format("CREATE DATABASE %s", nimi);
            looDBLause.executeUpdate(looDB);
            System.out.println("DB loodud");
        } catch (SQLException viga) {
            System.out.println("Andmebaasi loomisel tekkis viga: " + viga.getMessage());
        }
    }

    public void looKasutajadOlem() {
        final String tabeliNimi = "kasutajad";

        if (kasOlemOlemas(tabeliNimi)) {
            System.out.printf("%s olem juba olemas\n", tabeliNimi);
            return;
        }
        final String looKasutajadOlem = "CREATE TABLE " + tabeliNimi + " (" +
                "kasutaja_id SERIAL PRIMARY KEY NOT NULL UNIQUE," +
                "nimi VARCHAR(100) NOT NULL UNIQUE" +
                ");";

        try (Statement looKasutajadOlemLause = andmebaas.createStatement()){
            looKasutajadOlemLause.executeUpdate(looKasutajadOlem);
            System.out.printf("%s olem loodud\n", tabeliNimi);
        } catch (SQLException viga) {
            System.out.printf("%s olemi loomisel tekkis viga: %s\n", tabeliNimi, viga.getMessage());
        }
    }

    public void looUlesandedOlem() {
        final String tabeliNimi = "ulesanded";

        if (kasOlemOlemas(tabeliNimi)) {
            System.out.printf("%s olem juba olemas\n", tabeliNimi);
            return;
        }

        final String looUlesandedOlem = "CREATE TABLE " + tabeliNimi + " (" +
                "ulesanne_id SERIAL PRIMARY KEY NOT NULL UNIQUE," +
                "ulesanne_nimi VARCHAR(100) NOT NULL," +
                "kasutaja_id INT NOT NULL," +
                "FOREIGN KEY (kasutaja_id) REFERENCES kasutajad(kasutaja_id)," +
                "CONSTRAINT kasutajal_ainulaadsed_ulesanded UNIQUE (ulesanne_nimi, kasutaja_id)" +
                ");";

        try (Statement looUlesandedOlemLause = andmebaas.createStatement()) {
            looUlesandedOlemLause.executeUpdate(looUlesandedOlem);
            System.out.printf("%s olem loodud\n", tabeliNimi);
        } catch (SQLException viga) {
            System.out.printf("%s olemi loomisel tekkis viga: %s\n", tabeliNimi, viga.getMessage());
        }
    }

    public void looPomodorodOlem() {
        final String tabeliNimi = "pomodorod";

        if (kasOlemOlemas(tabeliNimi)) {
            System.out.printf("%s olem juba olemas\n", tabeliNimi);
            return;
        }

        final String looPomodorodOlem = "CREATE TABLE " + tabeliNimi + " (" +
                "pomodoro_id SERIAL PRIMARY KEY NOT NULL UNIQUE," +
                "produktiivne_aeg INTERVAL NOT NULL," +
                "puhke_aeg INTERVAL NOT NULL," +
                "kordused INT," +
                "produktiivne_aeg_kokku INTERVAL," +
                "ulesanne_id INT NOT NULL," +
                "FOREIGN KEY (ulesanne_id) REFERENCES ulesanded(ulesanne_id)" +
                ");";

        try (Statement looUlesandedOlemLause = andmebaas.createStatement()) {
            looUlesandedOlemLause.executeUpdate(looPomodorodOlem);
            System.out.printf("%s olem loodud\n", tabeliNimi);
        } catch (SQLException viga) {
            System.out.printf("%s olemi loomisel tekkis viga: %s\n", tabeliNimi, viga.getMessage());
        }
    }

    public boolean kasOlemOlemas(String olemiNimi) {
        try {
            DatabaseMetaData metaAndmed = andmebaas.getMetaData();
            try (ResultSet kasOlemOlemas = metaAndmed.getTables(nimi, "public", olemiNimi, new String[]{"TABLE"})) {
                return kasOlemOlemas.next();
            }
        } catch (SQLException viga) {
            System.out.printf("Viga %s olemi olemasolu kontrollimisel: %s\n", olemiNimi, viga.getMessage());
            return false;
        }
    }

    public int lisaUusKasutaja(String kasutajaNimi) {
        final String lisaUusKasutaja = "INSERT INTO kasutajad (nimi) VALUES (?)";
        int kasutajaID = -1;  // Näitab kasutaja loomise ebaõnnestumist

        try (PreparedStatement lisaUusKasutajaLause =
                     andmebaas.prepareStatement(lisaUusKasutaja, PreparedStatement.RETURN_GENERATED_KEYS)) {
            lisaUusKasutajaLause.setString(1, kasutajaNimi);
            kasutajaID = kontrolliLisatudOlemit(lisaUusKasutajaLause, "Kasutaja");
        } catch (SQLException viga) {
            System.out.println("Kasutaja olemi loomisel tekkis viga: " + viga.getMessage());
        }

        return kasutajaID;
    }

    public int lisaUusUlesanne(String ulesandeNimi, int kasutajaID) {
        final String lisaUusUlesanne = "INSERT INTO ulesanded (ulesanne_nimi, kasutaja_id) VALUES (?, ?)";
        int ulesandeID = -1;  // Näitab ülesande loomise ebaõnnestumist

        try (PreparedStatement lisaUusUlesanneLause =
                     andmebaas.prepareStatement(lisaUusUlesanne, PreparedStatement.RETURN_GENERATED_KEYS)) {
            lisaUusUlesanneLause.setString(1, ulesandeNimi);
            lisaUusUlesanneLause.setInt(2, kasutajaID);

            ulesandeID = kontrolliLisatudOlemit(lisaUusUlesanneLause, "Ülesanne");
        } catch (SQLException viga) {
            System.out.println("Ülesande olemi loomisel tekkis viga: " + viga.getMessage());
        }

        return ulesandeID;
    }

    public int lisaUusPomodoro(Duration produktiivneAeg, Duration puhkeAeg, int ulesanneID) {
        final String lisaUusPomodoro = "INSERT INTO pomodorod (produktiivne_aeg, puhke_aeg, ulesanne_id) " +
                "VALUES (?, ?, ?)";
        int pomodoroID = -1;

        try (PreparedStatement lisaUusPomodoroLause =
                     andmebaas.prepareStatement(lisaUusPomodoro, PreparedStatement.RETURN_GENERATED_KEYS)) {
            lisaUusPomodoroLause.setObject(1, aegPostgreFromaadis(produktiivneAeg));
            lisaUusPomodoroLause.setObject(2, aegPostgreFromaadis(puhkeAeg));
            lisaUusPomodoroLause.setInt(3, ulesanneID);

            pomodoroID = kontrolliLisatudOlemit(lisaUusPomodoroLause, "Pomodoro");
        } catch (SQLException viga) {
            System.out.println("Pomodoro olemi loomisel tekkis viga: " + viga.getMessage());
        }

        return pomodoroID;
    }

    private PGInterval aegPostgreFromaadis(Duration aeg) {
        long sekundidKokku = Math.abs(aeg.getSeconds());
        int tunnid =  (int) (sekundidKokku / 3600);
        int minutid = (int) ((sekundidKokku % 3600) / 60);
        double sekundid = (double) (sekundidKokku % 60);
        return new PGInterval(0, 0, 0, tunnid, minutid, sekundid);
    }

    public int kontrolliLisatudOlemit(PreparedStatement uueOlemiLause, String olemiTuup) throws SQLException {
        int olemiID = -1;
        int lisatudOlemeid = uueOlemiLause.executeUpdate();
        if (lisatudOlemeid != 1)  {
            System.out.printf("%s olemi loomisel tekkis viga: VEATEATETA\n", olemiTuup);
            return olemiID;
        }

        try (ResultSet uueOlemiID = uueOlemiLause.getGeneratedKeys()) {
            if (!uueOlemiID.next()) {
                System.out.printf("%s olemi võtme id genereerimisel tekkis viga: VEATEATETA\n", olemiTuup);
                return olemiID;
            }

            olemiID = uueOlemiID.getInt(1);
            System.out.printf("%s lisamine edukas\n", olemiTuup);

        } catch (SQLException viga) {
            System.out.printf("%s võtme id tagastamisel tekkis viga: %s\n", olemiTuup, viga.getMessage());
        }
        return olemiID;
    }

    public ArrayList<Kasutaja> tagastaKasutajateOlemid() {
        ArrayList<Kasutaja> kasutajad = new ArrayList<Kasutaja>();
        final String tagastaKasutajateOlemid = "SELECT nimi FROM kasutajad";

        try (PreparedStatement tagastaKasutajateOlemidLause = andmebaas.prepareStatement(tagastaKasutajateOlemid)) {
            try (ResultSet tagastaOlemidLauseTulem = tagastaKasutajateOlemidLause.executeQuery()) {
                while (tagastaOlemidLauseTulem.next()) {
                    kasutajad.add(new Kasutaja(tagastaOlemidLauseTulem.getString("nimi")));
                }
            } catch (SQLException viga) {
                System.out.println("Kasutajate olemite tagastamisel tekkis viga: " + viga.getMessage());
            }
        } catch (SQLException viga) {
            System.out.println("Kasutajate olemite tagastamise lause käitamisel tekkis viga: " + viga.getMessage());
        }

        return kasutajad;
    }

    public ArrayList<Ulesanne> tagastaUlesanneteOlemid() throws SQLException {
        ArrayList<Ulesanne> ulesanded = new ArrayList<>();
        final String tagastaUlesanneteOlemid = "Select ulesanne_nimi from ulesanded";

        try (PreparedStatement tagastaUlesanneteOlemidLause = andmebaas.prepareStatement(tagastaUlesanneteOlemid)) {
            try (ResultSet tagastaOlemidLauseTulem = tagastaUlesanneteOlemidLause.executeQuery()) {
                while (tagastaOlemidLauseTulem.next()) {
                    ulesanded.add(new Ulesanne(tagastaOlemidLauseTulem.getString("ulesanne_nimi")));
                }
            }
        }

        return ulesanded;
    }

    public ArrayList<Pomodoro> tagastaPomodorodeOlemid() {
        ArrayList<Pomodoro> pomodorod = new ArrayList<>();
        final String tagastaPomodorodeOlemid = "Select produktiivne_aeg, puhke_aeg from pomodorod";

        try (PreparedStatement tagastaPomodorodeOlemidLause = andmebaas.prepareStatement(tagastaPomodorodeOlemid)) {
            try (ResultSet tagastaOlemidLauseTulem = tagastaPomodorodeOlemidLause.executeQuery()) {
                while (tagastaOlemidLauseTulem.next()) {
                    pomodorod.add(new Pomodoro(LocalTime.parse(tagastaOlemidLauseTulem.getString("produktiivne_aeg")), LocalTime.parse(tagastaOlemidLauseTulem.getString("puhke_aeg")), false));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pomodorod;
    }

}