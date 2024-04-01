// https://www.postgresqltutorial.com/postgresql-jdbc/
// https://www.tutorialspoint.com/postgresql/postgresql_java.htm
// https://www.sohamkamani.com/java/jdbc-postgresql/
// https://jdbc.postgresql.org/documentation/publicapi/index.html?org/postgresql/util/PGInterval.html

import org.postgresql.util.PGInterval;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;

public class Andmebaas implements AutoCloseable {
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

    public Kasutaja looUusKasutaja(String kasutajaNimi) {
        int kasutajaID = lisaUusKasutaja(kasutajaNimi);
        if (kasutajaID == -1) return null;

        return new Kasutaja(kasutajaID, kasutajaNimi);
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

    public Ulesanne looUusUlesanne(String ulesandeNimi, Kasutaja ulesandeOmanik) {
        int ulesandeID = lisaUusUlesanne(ulesandeNimi, ulesandeOmanik.getKasutajaID());
        if (ulesandeID == -1) return null;

        Ulesanne ulesanne = new Ulesanne(ulesandeID, ulesandeNimi);
        ulesandeOmanik.lisaUlesanneNimekirja(ulesanne);
        return ulesanne;
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

    public Pomodoro looUusPomodoro(Duration produktiivneAeg, Duration puhkeAeg, Ulesanne kuulubUlesandesse) {
        int pomodoroID = lisaUusPomodoro(produktiivneAeg, puhkeAeg, kuulubUlesandesse.getUlesandeID());
        if (pomodoroID == -1) return null;

        Pomodoro pomodoro = new Pomodoro(pomodoroID, produktiivneAeg, puhkeAeg);
        kuulubUlesandesse.lisaPomodoro(pomodoro);
        return pomodoro;
    }

    private static PGInterval aegPostgreFromaadis(Duration aeg) {
        long sekundid = aeg.getSeconds();

        int tunnid = (int) (sekundid / 3600);
        sekundid %= 3600;

        int minutid = (int) (sekundid / 60);
        sekundid %= 60;


        PGInterval pgIntervall = new PGInterval(0, 0, 0, tunnid, minutid, sekundid);
        return pgIntervall;
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
        final String tagastaKasutajateOlemid = "SELECT kasutaja_id, nimi FROM kasutajad";

        try (PreparedStatement tagastaKasutajateOlemidLause = andmebaas.prepareStatement(tagastaKasutajateOlemid)) {
            try (ResultSet tagastaOlemidLauseTulem = tagastaKasutajateOlemidLause.executeQuery()) {
                while (tagastaOlemidLauseTulem.next()) {
                    int kasutajaID = tagastaOlemidLauseTulem.getInt("kasutaja_id");
                    String kasutajaNimi = tagastaOlemidLauseTulem.getString("nimi");

                    kasutajad.add(new Kasutaja(kasutajaID, kasutajaNimi));
                }
            } catch (SQLException viga) {
                System.out.println("Kasutajate olemite tagastamisel tekkis viga: " + viga.getMessage());
            }
        } catch (SQLException viga) {
            System.out.println("Kasutajate olemite tagastamise lause käitamisel tekkis viga: " + viga.getMessage());
        }

        return kasutajad;
    }

    public ArrayList<Ulesanne> tagastaUlesanneteOlemid(int kasutajaID){
        ArrayList<Ulesanne> ulesanded = new ArrayList<Ulesanne>();
        final String tagastaUlesanneteOlemid = "Select ulesanne_id, ulesanne_nimi from ulesanded WHERE kasutaja_id = ?";

        try (PreparedStatement tagastaUlesanneteOlemidLause = andmebaas.prepareStatement(tagastaUlesanneteOlemid)) {
            tagastaUlesanneteOlemidLause.setInt(1, kasutajaID);

            try (ResultSet tagastaOlemidLauseTulem = tagastaUlesanneteOlemidLause.executeQuery()) {
                while (tagastaOlemidLauseTulem.next()) {
                    int ulesandeID = tagastaOlemidLauseTulem.getInt("ulesanne_id");
                    String ulesandeNimi = tagastaOlemidLauseTulem.getString("ulesanne_nimi");

                    ulesanded.add(new Ulesanne(ulesandeID, ulesandeNimi));
                }
            } catch (SQLException viga) {
                System.out.println("Ülesannete olemite tagastamisel tekkis viga: " + viga.getMessage());
            }
        } catch (SQLException viga) {
            System.out.println("Ülesannete olemite tagastamise lause käitamisel tekkis viga: " + viga.getMessage());
        }

        return ulesanded;
    }

    public ArrayList<Pomodoro> tagastaPomodorodeOlemid(int ulesanneID) {
        ArrayList<Pomodoro> pomodorod = new ArrayList<>();
        final String tagastaPomodorodeOlemid = "Select pomodoro_id, produktiivne_aeg, puhke_aeg, kordused, " +
                "produktiivne_aeg_kokku from pomodorod WHERE ulesanne_ID = ?";

        try (PreparedStatement tagastaPomodorodeOlemidLause = andmebaas.prepareStatement(tagastaPomodorodeOlemid)) {
            tagastaPomodorodeOlemidLause.setInt(1, ulesanneID);

            try (ResultSet tagastaOlemidLauseTulem = tagastaPomodorodeOlemidLause.executeQuery()) {
                while (tagastaOlemidLauseTulem.next()) {
                    int pomodoroID = tagastaOlemidLauseTulem.getInt("pomodoro_id");

                    String produktiivneAegString = tagastaOlemidLauseTulem.getString("produktiivne_aeg");
                    Duration produktiivneAeg  = postgreFormaadiTeisendus(produktiivneAegString);

                    String puhkeAegString = tagastaOlemidLauseTulem.getString("puhke_aeg");
                    Duration puhkeAeg = postgreFormaadiTeisendus(puhkeAegString);

                    int kordused = tagastaOlemidLauseTulem.getInt("kordused");

                    String produktiivneAegKokkuString =
                            tagastaOlemidLauseTulem.getString("produktiivne_aeg_kokku");
                    Duration produktiivneAegKokku = postgreFormaadiTeisendus(produktiivneAegKokkuString);

                    pomodorod.add(new Pomodoro(pomodoroID, produktiivneAeg, puhkeAeg, kordused, produktiivneAegKokku));
                }
            } catch (SQLException viga) {
                System.out.println("Pomodorode olemite tagastamisel tekkis viga: " + viga.getMessage());
            }
        } catch (SQLException viga) {
            System.out.println("Pomodorode olemite tagastamise lause käitamisel tekkis viga: " + viga.getMessage());
        }

        return pomodorod;
    }

    private static Duration postgreFormaadiTeisendus(String pgIntervall) {
        if (pgIntervall == null) return null;
        // formaat [...] HH:MM:SS
        String[] andmed = pgIntervall.split(" ");
        if (andmed.length != 1) {
            System.out.println("Viga postgre formaadist teisendusel, programmiga jätkamine võimalik");
        }

        pgIntervall = andmed[andmed.length - 1];  // Vaja ainult kõige viimast osa (tglt peakski ainult üks osa olema)
        String[] ajaosad = pgIntervall.split(":");
        int tunnid = Integer.parseInt(ajaosad[0]);
        int minutid = Integer.parseInt(ajaosad[1]);
        int sekundid = Integer.parseInt(ajaosad[2]);
        long sekundidKokku = (long) tunnid * 3600 + (long) minutid * 60 + (long) sekundid;

        return Duration.ofSeconds(sekundidKokku);
    }

    @Override
    public void close() {
        katkestaUhendus();
    }
}