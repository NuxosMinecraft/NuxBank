package net.n4th4.bukkit.nuxbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;

public class ConnectionManager
{
  private static Connection conn;
  private static NuxBank plugin;

  public static void init(NuxBank instance)
  {
    plugin = instance;
  }

  public static Connection initialize() {
    try {
      conn = DriverManager.getConnection("jdbc:" + plugin.config.getString("url"), plugin.config.getString("user"), plugin.config.getString("passwd"));
      conn.setAutoCommit(false);
      return conn;
    }
    catch (SQLException ex) {
      plugin.log.severe("SQL exception on initialize");
    }
    return conn;
  }

  public static Connection getConnection() {
    if (conn == null)
      conn = initialize();
    try {
      if (!conn.isValid(10))
        conn = initialize();
    } catch (SQLException ex) {
      plugin.log.severe("Failed to check SQL status");
    }

    return conn;
  }

  public static void closeConnection() {
    if (conn != null)
      try {
        if (conn.isValid(10)) {
          conn.close();
        }
        conn = null;
      } catch (SQLException ex) {
        plugin.log.severe("Error on Connection close");
      }
  }
}

/* Location:           /home/munrek/Téléchargements/nuxos/backup nuxos/bukkit/plugins/NuxBank.jar
 * Qualified Name:     net.n4th4.bukkit.nuxbank.ConnectionManager
 * JD-Core Version:    0.6.0
 */