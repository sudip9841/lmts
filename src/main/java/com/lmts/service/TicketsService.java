
package com.lmts.service;

import com.lmts.Dao.TicketsDao;
import com.lmts.model.Association;
import com.lmts.model.Ticket;
import com.lmts.model.TicketHistory;
import com.lmts.model.TicketHistoryAdmin;
import java.util.List;

public class TicketsService {
    
    private TicketsDao ticketsDao;
    
    public TicketsService(){
        this.ticketsDao = new TicketsDao();
    }
    
    
    public boolean saveTicketsAndAssociations(Ticket ticket, List<Integer> categoryId){
        return this.ticketsDao.saveTicketsAndAssociations(ticket, categoryId);
    }
    
    public List<TicketHistory> getTicketsByUserId(int userId){
        return this.ticketsDao.getTicketsByUserId(userId);
    }
    
    public List<TicketHistoryAdmin> getTicketHistoryAdminList(){
         return this.ticketsDao.getTicketHistoryAdminList();
    }
}
