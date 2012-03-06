package net.n4th4.bukkit.nuxbank;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Item
{
  public static int getAmount(int id, int data)
  {
    try
    {
      Connection conn = ConnectionManager.getConnection();
      Statement state = conn.createStatement();
      ResultSet result = state.executeQuery("SELECT quantity FROM bank WHERE id=" + id + " AND data=" + data + ";");
      result.first();
      return result.getInt("quantity");
    } catch (SQLException e) {
      e.printStackTrace();
    }return 0;
  }

  public static void addAmount(int id, int data, int amount)
  {
    try {
      Connection conn = ConnectionManager.getConnection();
      Statement state = conn.createStatement();
      state.executeUpdate("UPDATE bank SET quantity=quantity+" + amount + " WHERE id=" + id + " AND data=" + data + ";");
      conn.commit();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}

/* Location:           /home/munrek/Téléchargements/nuxos/backup nuxos/bukkit/plugins/NuxBank.jar
 * Qualified Name:     net.n4th4.bukkit.nuxbank.Item
 * JD-Core Version:    0.6.0
 */