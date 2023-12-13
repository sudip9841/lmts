/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.lmts;

import com.lmts.model.Category;
import com.lmts.model.TicketHistory;
import com.lmts.service.CategoryService;
import com.lmts.service.TicketsService;
import com.lmts.shared.PdfGenerator;
import com.lmts.shared.UserDetailsSingleton;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sudip
 */
public class HomeJPanelMyTicketPanel extends javax.swing.JPanel {
    
    private TicketsService ticketsService;
    private JTable ticketTable;
    private DefaultTableModel tableModel;

    /**
     * Creates new form HomeJPanelMyTicketPanel
     */
    public HomeJPanelMyTicketPanel() {
//        initComponents();

        this.ticketsService = new TicketsService();
        setLayout(new BorderLayout());

        // Create a non-editable DefaultTableModel
        this.tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Add columns to the model
        tableModel.addColumn("Id");
        tableModel.addColumn("Music Name");
        tableModel.addColumn("Ticket Count");
        tableModel.addColumn("Total Price");
        tableModel.addColumn("Date");
        tableModel.addColumn("Time");

//        this.setTableData();

        // Create a JTable with the non-editable model
        ticketTable = new JTable(this.tableModel);
        

        // Add the table to a JScrollPane for scrolling
        JScrollPane tableScrollPane = new JScrollPane(ticketTable);

        // Set the preferred width for the JScrollPane
        int preferredWidth = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.5);
        tableScrollPane.setPreferredSize(new Dimension(preferredWidth, tableScrollPane.getPreferredSize().height));

        // Create a container with FlowLayout to hold the JScrollPane
        JPanel tableContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tableContainer.add(tableScrollPane);

        // Add the container to the layout
        add(tableContainer, BorderLayout.CENTER);
    }

     public void setTableData(){
        UserDetailsSingleton userDetails = UserDetailsSingleton.getInstance();
             
        int userId = userDetails.getUserId();
        List<TicketHistory> ticketHistory = this.ticketsService.getTicketsByUserId(userId);
        this.tableModel.setRowCount(0);
        for(TicketHistory ticket:ticketHistory){
            System.out.println(ticket.getMusicName());
            Object[] rowData = {ticket.getId(), ticket.getMusicName(), ticket.getQuantity(), ticket.getTotalPrice(),ticket.getDate(),ticket.getTime()};
            this.tableModel.addRow(rowData);
        }
        
          // Add a ListSelectionListener to detect row selection changes
        ticketTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = ticketTable.getSelectedRow();
                    if (selectedRow != -1) {
                        TicketHistory selectedTicket = ticketHistory.get(selectedRow);

                        // Generate and print the PDF for the selected ticket
//                        PdfGenerator.generateTicketPDF(selectedTicket);

                        File pdfFile = PdfGenerator.generateTicketPDF(selectedTicket);
                        openPdfInBrowser(pdfFile);
                    }
                }
            }
        });
    }
     
     private void openPdfInBrowser(File pdfFile) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                JOptionPane.showMessageDialog(getParent(), "Desktop is not supported, cannot open the PDF.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(getParent(), "Error opening the PDF file: " + ex.getMessage());
        }
    }
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        jLabel1.setText("My Ticket Panel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(495, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(443, 443, 443))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(jLabel1)
                .addContainerGap(328, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
