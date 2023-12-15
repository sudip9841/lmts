

package com.lmts;

import javax.swing.SwingUtilities;

public class Lmts {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainJFrame().setVisible(true);
        });
    }
}
