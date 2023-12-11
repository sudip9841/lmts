
package com.lmts.Dao;

import com.lmts.helpers.DBUtils;
import com.lmts.model.Association;
import com.lmts.model.Ticket;
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

    public boolean saveTicketsAndAssociations(List<Ticket> tickets, List<Association> associations) {

        try (Connection connection = this.con) {
            List<Integer> generatedTicketIds = saveTicketsBatch(connection, tickets);
            saveAssociationsBatch(connection, generatedTicketIds, associations);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
            // Handle the exception appropriately in a real application
        }
    }

    
    private List<Integer> saveTicketsBatch(Connection connection, List<Ticket> tickets) throws SQLException {
        List<Integer> generatedIds = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TICKET_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            for (Ticket ticket : tickets) {
                preparedStatement.setInt(1, ticket.getUserId());
                preparedStatement.setInt(2, ticket.getShowTimeId());
                preparedStatement.setInt(3, ticket.getQuantity());
                preparedStatement.setDouble(4, ticket.getTotalPrice());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    generatedIds.add(generatedKeys.getInt(1));
                }
            }
        }

        return generatedIds;
    }

    private void saveAssociationsBatch(Connection connection, List<Integer> ticketIds, List<Association> associations) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ASSOCIATION_SQL)) {
            for (int i = 0; i < ticketIds.size(); i++) {
                preparedStatement.setInt(1, ticketIds.get(i));
                preparedStatement.setInt(2, associations.get(i).getCategoryId());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }
    }
    
    
    
}
