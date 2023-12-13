
package com.lmts;

import com.lmts.model.Category;
import com.lmts.model.Ticket;
import com.lmts.service.CategoryService;
import com.lmts.service.TicketsService;
import com.lmts.shared.AlertMessageDialogBox;
import com.lmts.shared.UserDetailsSingleton;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


class TicketDialog extends JDialog {
   private JLabel titleLabel;
    private JLabel dateLabel;
    private JComboBox<String> ticketCategoryComboBox;
    private JTextField quantityTextField;
    private JButton buyButton;
    private JButton addTicketButton;
    private JTable ticketTable;
    private DefaultTableModel tableModel;
    private int totalTickets = 0;
    private double totalPrice = 0;
    private CategoryService categoryService;
    private TicketsService ticketsService;
    private int musicId = 0;
    private int showTimeId = 0;

    public TicketDialog(String title, String date, int musicId, int showTimeId) {
        this.musicId = musicId;
        this.showTimeId = showTimeId;
        this.categoryService = new CategoryService();
        this.ticketsService = new TicketsService();
        
        setTitle("Buy Ticket");
        setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        titleLabel = new JLabel("Title: " + title);
        dateLabel = new JLabel("Date: " + date);
        String[] categoryNames = this.categoryService.getAllCategory().stream()
                                     .map(Category::getCategoryName)
                                     .toArray(String[]::new);
        
        ticketCategoryComboBox = new JComboBox<>(categoryNames);
        quantityTextField = new JTextField();
        addTicketButton = new JButton("Add Ticket");
        buyButton = new JButton("Buy Tickets");
        
         // Increase width for quantityTextField and ticketCategoryComboBox
        quantityTextField.setPreferredSize(new Dimension(150, quantityTextField.getPreferredSize().height));
        ticketCategoryComboBox.setPreferredSize(new Dimension(150, ticketCategoryComboBox.getPreferredSize().height));

        infoPanel.add(new JLabel("Ticket Category:"), gbc);
        gbc.gridx++;
        infoPanel.add(ticketCategoryComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx++;
        infoPanel.add(quantityTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(titleLabel, gbc);
        gbc.gridx++;
        infoPanel.add(dateLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        infoPanel.add(addTicketButton, gbc);
        gbc.gridx++;
        infoPanel.add(buyButton, gbc);

        add(infoPanel, BorderLayout.NORTH);

        // Table setup
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Title");
        tableModel.addColumn("Date");
        tableModel.addColumn("Category Id");
        tableModel.addColumn("Category");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Price");

        ticketTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(ticketTable);
        add(tableScrollPane, BorderLayout.CENTER);
        
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL); 
        
         // Set the default close operation to DISPOSE_ON_CLOSE
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Override the processWindowEvent method
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Otherwise, do nothing
            }
        });

        addTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddTicketButtonAction(title, date);
            }
        });

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBuyButtonAction();
            }
        });

        setSize(new Dimension(1020, 600));
        setLocationRelativeTo(null);
    
    }

    private void handleAddTicketButtonAction(String title, String date) {
        int quantity = Integer.parseInt(quantityTextField.getText());
        String category = (String) ticketCategoryComboBox.getSelectedItem();
        double price = calculateTicketPrice(category, quantity);
        int categoryId = getCategoryId(category);
        
        // Check if the category already exists in the table
        int rowIndex = findRowIndexByCategory(category);
     

        if (rowIndex != -1) {
        // Category already exists, update the existing row
        updateExistingRow(rowIndex, quantity, price);
        } else {
            // Category does not exist, add a new row
            tableModel.addRow(new Object[]{title, date, categoryId, category, quantity, price});
        }

        // Update total tickets and total price
        totalTickets += quantity;
        totalPrice += price;

        // Clear input fields
        quantityTextField.setText("");

        // You can update a total price label or display it in any way you prefer
        System.out.println("Total Tickets: " + totalTickets + ", Total Price: $" + totalPrice);
    }

    private void handleBuyButtonAction() {
        if(this.totalPrice==0){
            AlertMessageDialogBox.showInfo("Please add music ticket", "Empty Music Ticket");
            return;
        }
        
         int result = JOptionPane.showConfirmDialog(
                        TicketDialog.this, // Parent component
                        "Are you sure you want to buy the tickets?"+ "Total Tickets: " + totalTickets + ", Total Price: £" + totalPrice, // Message
                        "Confirmation", // Dialog title
                        JOptionPane.YES_NO_OPTION // Option type
                );

        // Check the user's choice
        if (result == JOptionPane.YES_OPTION) {
            
             int rowCount = tableModel.getRowCount();
             int columnCount = tableModel.getColumnCount();
             
             
             UserDetailsSingleton userDetails = UserDetailsSingleton.getInstance();
             
             int userId = userDetails.getUserId();
             int showTimeId = this.showTimeId;
             int totalTicket = this.totalTickets;
             double totalPrice = this.totalPrice;
             
            Ticket ticketObj = new Ticket(userId,showTimeId,totalTicket,totalPrice);
             
            List<Integer> categoryIds = new ArrayList<>();

            for (int row = 0; row < tableModel.getRowCount(); row++) {
                int categoryValue = (int) tableModel.getValueAt(row, 2); // "Category Id" column index is 2 
                categoryIds.add((Integer) categoryValue);  
            }
            
           boolean save = this.ticketsService.saveTicketsAndAssociations(ticketObj, categoryIds);
           if(save){
               AlertMessageDialogBox.showInfo("Ticket Purched Successfully.", "Success");
           }
            
            System.out.println("Purchase finalized!");
            dispose();
        }
        
    }

    private double calculateTicketPrice(String category, int quantity) {
        // Find the selected category in the categoryList
        Optional<Category> selectedCategoryObj = this.categoryService.getAllCategory().stream()
                                                             .filter(Category -> Category.getCategoryName().equals(category))
                                                             .findFirst();

        // Calculate ticket price based on the selected category and quantity
        if (selectedCategoryObj.isPresent()) {
            Category categoryObj = selectedCategoryObj.get();
            return categoryObj.getPrice() * quantity;
        } else {
            // Handle the case where the selected category is not found
            System.err.println("Selected category not found: " + category);
            return 0.0; // or throw an exception, return a default value, etc.
        }
    }
    
    private int getCategoryId(String categoryName) {
        Optional<Category> selectedCategoryObj = this.categoryService.getAllCategory().stream()
                .filter(category -> category.getCategoryName().equals(categoryName))
                .findFirst();

        return selectedCategoryObj.map(Category::getCategoryId).orElse(-1);
    }
    
    private int findRowIndexByCategory(String category) {
    // Iterate through the table to find the row index with the given category
    for (int i = 0; i < tableModel.getRowCount(); i++) {
        String existingCategory = (String) tableModel.getValueAt(i, 3); // Assuming category is in the third column
        if (existingCategory.equals(category)) {
            return i; // Return the row index if category exists
        }
    }
    return -1; // Return -1 if category is not found
    }

    private void updateExistingRow(int rowIndex, int quantity, double price) {
        // Update the existing row with the new quantity and price
        tableModel.setValueAt((int) tableModel.getValueAt(rowIndex, 3) + quantity, rowIndex, 3); // Update quantity
        tableModel.setValueAt((double) tableModel.getValueAt(rowIndex, 4) + price, rowIndex, 4); // Update price
    }

}




public class MusicListCardJPanel extends javax.swing.JPanel {
    private int musicId;
    private String musicName; 
    private String showTime; 
    private String date; 
    private int availableTicket; 
    private String description;
    private int showTimeId;

    /**
     * Creates new form MusicListCardJPanel
     */
    public MusicListCardJPanel(String musicName, String showTime, String date, int availableTicket, String description, int musicId, int showTimeId) {
        this.musicId = musicId;
        this.musicName = musicName;
        this.showTime = showTime;
        this.date = date;
        this.availableTicket = availableTicket;
        this.description = description;
        this.showTimeId = showTimeId;
        
        initComponents();
        this.jLabel1.setText(musicName);
        this.jLabel3.setText(showTime);
        this.jLabel5.setText(date);
        this.jLabel7.setText( String.valueOf(availableTicket));
        this.jTextArea1.setText(description);
        this.jTextArea1.setLineWrap(true);
        this.jTextArea1.setWrapStyleWord(true);
        this.jTextArea1.setColumns(400); // Set the number of columns (adjust as needed)
        this.jTextArea1.setRows(4);  
        this.jTextArea1.setBorder(null);
        this.jTextArea1.setEditable(false);
        
        // Add a mouse listener to handle click events
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                AlertMessageDialogBox.showInfo(date,"Info");
//            }
//        });
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("The Lion King");

        jLabel2.setText("Show Time: ");

        jLabel3.setText("2 hrs 30 min");

        jLabel4.setText("Date:");

        jLabel5.setText("2022-12-12");

        jLabel6.setText("Available Ticket:");

        jLabel7.setText("50");

        jLabel8.setText("Description");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setBackground(new java.awt.Color(51, 153, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Book Ticket");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(128, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jButton1)
                .addContainerGap(15, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//        AlertMessageDialogBox.showInfo(this.musicName,"Info");
        this.showTicketDialog();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void showTicketDialog() {
        TicketDialog ticketDialog = new TicketDialog(this.musicName, this.date, this.musicId,this.showTimeId);
        ticketDialog.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
