/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmts.shared;

import javax.swing.*;
import java.awt.*;

public class AlertMessageDialogBox {
    public static void showAlert(String message, String title, int messageType) {
        JOptionPane optionPane = new JOptionPane(message, messageType);
        JDialog dialog = optionPane.createDialog(null, title);
        dialog.setLocationRelativeTo(null); // Center the dialog
        dialog.setVisible(true);
    }

    public static void showInfo(String message, String title) {
        showAlert(message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showWarning(String message, String title) {
        showAlert(message, title, JOptionPane.WARNING_MESSAGE);
    }

    public static void showError(String message, String title) {
        showAlert(message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showCustom(String message, String title, int messageType) {
        showAlert(message, title, messageType);
    }

    
}

