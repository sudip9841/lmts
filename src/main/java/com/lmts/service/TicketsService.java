
package com.lmts.service;

import com.lmts.Dao.TicketsDao;
import com.lmts.model.Association;
import com.lmts.model.Ticket;
import java.util.List;

public class TicketsService {
    
    private TicketsDao ticketsDao;
    
    public TicketsService(){
        this.ticketsDao = new TicketsDao();
    }
    
    
    public boolean saveTicketsAndAssociations(List<Ticket> tickets, List<Association> associations){
        return this.ticketsDao.saveTicketsAndAssociations(tickets, associations);
    }
}
