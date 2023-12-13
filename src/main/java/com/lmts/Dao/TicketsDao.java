
package com.lmts.Dao;

import com.lmts.helpers.DBUtils;
import com.lmts.model.Association;
import com.lmts.model.Ticket;
import com.lmts.model.TicketHistory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketsDao {
    
    private Connection con;
    
    public TicketsDao(){
        try{
            this.con = DBUtils.getDBConnection();
        }catch(SQLException throwables){
           throwables.printStackTrace();
        }
    }
    
    private static final String INSERT_TICKET_SQL = "INSERT INTO tickets (user_id, show_time_id, quantity, total_price) VALUES (?, ?, ?, ?)";
    private static final String INSERT_ASSOCIATION_SQL = "INSERT INTO ticket_category_association (ticket_id, category_id) VALUES (?, ?)";

    public boolean saveTicketsAndAssociations(Ticket ticket, List<Integer> categoryIds) {

        try (Connection connection = this.con) {
            int generatedTicketId = saveTicket(connection, ticket);
            saveAssociationsBatch(connection, generatedTicketId, categoryIds);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
            // Handle the exception appropriately in a real application
        }
    }

    private int saveTicket(Connection connection, Ticket ticket) throws SQLException {
        int generatedId = -1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TICKET_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, ticket.getUserId());
            preparedStatement.setInt(2, ticket.getShowTimeId());
            preparedStatement.setInt(3, ticket.getQuantity());
            preparedStatement.setDouble(4, ticket.getTotalPrice());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }
        }

        return generatedId;
    }

   

    private void saveAssociationsBatch(Connection connection, int ticketId, List<Integer> categoryIds) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ASSOCIATION_SQL)) {
            for (int i = 0; i < categoryIds.size(); i++) {
                preparedStatement.setInt(1, ticketId);
                preparedStatement.setInt(2, categoryIds.get(i));
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }
    }
    
    
    public List<TicketHistory> getTicketsByUserId(int userId) {
        List<TicketHistory> tickets = new ArrayList<>();

        String query = "SELECT t.id, m.name AS musicName, t.quantity, t.total_price AS totalPrice, st.date, st.time " +
                "FROM tickets t " +
                "JOIN show_time st ON t.show_time_id = st.id " +
                "JOIN music m ON m.id = st.music_id " +
                "JOIN user u ON u.id = t.user_id " +
                "WHERE u.id = ?";

        try (PreparedStatement preparedStatement = this.con.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TicketHistory ticket = new TicketHistory();
                    ticket.setId(resultSet.getInt("id"));
                    ticket.setMusicName(resultSet.getString("musicName"));
                    ticket.setQuantity(resultSet.getInt("quantity"));
                    ticket.setTotalPrice(resultSet.getDouble("totalPrice"));
                    ticket.setDate(resultSet.getString("date"));
                    ticket.setTime(resultSet.getString("time"));
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return tickets;
    }
    
    
    
}
