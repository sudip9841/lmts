
package com.lmts.Dao;

import com.lmts.helpers.DBUtils;
import com.lmts.model.Ticket;
import com.lmts.model.TicketHistory;
import com.lmts.model.TicketHistoryAdmin;
import com.lmts.shared.AlertMessageDialogBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    
    private static final String SELECT_AVAILABLE_SEATS_SQL = "SELECT available_seat FROM show_time WHERE id = ?";
    private static final String UPDATE_AVAILABLE_SEATS_SQL = "UPDATE show_time SET available_seat = ? WHERE id = ?";
    
    public boolean saveTicketsAndAssociations(Ticket ticket, List<Integer> categoryIds) {

        try (Connection connection = this.con) {
            int availableSeats = getAvailableSeats(connection, ticket.getShowTimeId());
            if (availableSeats >= ticket.getQuantity()) {
                // Save ticket and associations
                int generatedTicketId = saveTicket(connection, ticket);
                saveAssociationsBatch(connection, generatedTicketId, categoryIds);

                // Deduct available seats
                int updatedAvailableSeats = availableSeats - ticket.getQuantity();
                updateAvailableSeats(connection, ticket.getShowTimeId(), updatedAvailableSeats);

                return true;
            } else {
                // Not enough available seats, show alert or handle as needed
                System.out.println("Not enough available seats.");
                AlertMessageDialogBox.showError("Not enough available seats.", "Failed");
                return false;
            }
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
    
    private int getAvailableSeats(Connection connection, int showTimeId) throws SQLException {
        int availableSeats = -1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AVAILABLE_SEATS_SQL)) {
            preparedStatement.setInt(1, showTimeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    availableSeats = resultSet.getInt("available_seat");
                }
            }
        }

        return availableSeats;
    }

    private void updateAvailableSeats(Connection connection, int showTimeId, int availableSeats) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AVAILABLE_SEATS_SQL)) {
            preparedStatement.setInt(1, availableSeats);
            preparedStatement.setInt(2, showTimeId);

            preparedStatement.executeUpdate();
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
    
    
    public List<TicketHistoryAdmin> getTicketHistoryAdminList() {
        List<TicketHistoryAdmin> ticketHistoryAdminList = new ArrayList<>();

        try (Statement statement = this.con.createStatement()) {
            String query = "SELECT t.id as ticketId, m.name as musicName, tt.type_name as ticketType, " +
                           "tt.price as totalPrice, st.`date` as date, st.`time` as time " +
                           "FROM ticket_category_association tca " +
                           "JOIN tickets t ON t.id = tca.ticket_id " +
                           "JOIN ticket_type tt ON tt.id = tca.category_id " +
                           "JOIN show_time st ON st.id = t.show_time_id " +
                           "JOIN music m ON m.id = st.music_id";

            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int ticketId = resultSet.getInt("ticketId");
                    String musicName = resultSet.getString("musicName");
                    String ticketType = resultSet.getString("ticketType");
                    double totalPrice = resultSet.getDouble("totalPrice");
                    String date = resultSet.getString("date");
                    String time = resultSet.getString("time");

                    TicketHistoryAdmin ticketHistoryAdmin = new TicketHistoryAdmin(ticketId, musicName, ticketType, totalPrice, date, time);
                    ticketHistoryAdminList.add(ticketHistoryAdmin);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return ticketHistoryAdminList;
    }
    
    
    
}
