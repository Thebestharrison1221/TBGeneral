package org.TBCreates.TBGeneral.data;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

import com.google.gson.Gson;
import org.TBCreates.TBGeneral.TBGeneral;
import org.bukkit.plugin.java.JavaPlugin;


public class SQLite extends Database{
    String dbname;
    String trophiesTable;

    ArrayList<String> defaultTrophies;

    Gson gson;

    public SQLite(TBGeneral instance){
        super(instance);
        dbname = plugin.getConfig().getString("db.sqlite.filename");
        trophiesTable = plugin.getConfig().getString("db.tables.trophies");
        this.table = trophiesTable;
        SQLiteCreateTrophiesTable = "CREATE TABLE IF NOT EXISTS " + trophiesTable + " (" +
                "`player` varchar(32) NOT NULL," +
                "`advancementListJson` int(11) NOT NULL," +
                "PRIMARY KEY (`player`)" +
                ");";
        gson = new Gson();
        defaultTrophies = new ArrayList<>();
    }

    public final String SQLiteCreateTrophiesTable;


    public Connection getSQLConnection() {
        File dataFolder = new File(plugin.getDataFolder(), dbname+".db");
        if (!dataFolder.exists()){
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: "+dbname+".db");
            }
        }
        try {
            if(connection!=null&&!connection.isClosed()){
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE,"SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }

    public ArrayList<String> getTrophiesRecord(UUID player)
    {

        boolean exists = false;
        ArrayList<String> ret = null;
        connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            s.execute("SELECT * FROM " + trophiesTable + " WHERE player = '" + player + "';");
            var res = s.getResultSet();
            exists = res.next();
            if(exists)
            {
                ret = gson.fromJson(res.getString("advancementListJson"), ArrayList.class);
            }
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        // Create if doesn't exist
        if(!exists)
        {
            try {
                Statement s = connection.createStatement();
                s.executeUpdate("INSERT INTO " + trophiesTable + " (player, advancementListJson) VALUES ('" + player + "', '" + gson.toJson(defaultTrophies) + "');");
                s.close();
                ret = defaultTrophies;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        return ret;
    }

    public void updateTrophiesRecord(UUID player, ArrayList<String> newRecord)
    {
        try {
            Statement s = connection.createStatement();
            s.executeUpdate("UPDATE " + trophiesTable + " SET advancementListJson = '" + gson.toJson(newRecord) + "' WHERE player = '" + player + "';");
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create() {
        connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(SQLiteCreateTrophiesTable);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }
}