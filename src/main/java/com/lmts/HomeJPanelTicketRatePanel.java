
package com.lmts;

import com.lmts.model.Category;
import com.lmts.service.CategoryService;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class HomeJPanelTicketRatePanel extends javax.swing.JPanel {
    private CategoryService categoryService;
    private JTable ticketTable;
    private DefaultTableModel tableModel;
    /**
     * Creates new form HomeJPanelTicketRatePanel
     */
    public HomeJPanelTicketRatePanel() {
//        initComponents();
        this.categoryService = new CategoryService();
        setLayout(new BorderLayout());

        // Create a non-editable DefaultTableModel
        this.tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Add columns to the model
        tableModel.addColumn("Category");
        tableModel.addColumn("Price");

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
        List<Category> categoryList = this.categoryService.getAllCategory();
        this.tableModel.setRowCount(0);
        for(Category category:categoryList){
            Object[] rowData = {category.getCategoryName(), category.getPrice()};
            this.tableModel.addRow(rowData);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1020, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
