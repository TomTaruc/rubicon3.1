package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.drawfx.ActionCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class DrawingToolBar extends JToolBar {
    public DrawingToolBar(ActionListener actionListener){
        JButton button = new JButton();
        String imgLocation = "images/"
                + "chrome48"
                + ".png";

        URL imageURL = DrawingToolBar.class.getResource(imgLocation);

        button.setActionCommand(ActionCommand.SETCOLOR);
        button.setToolTipText("set color");
        button.addActionListener(actionListener);
        button.setIcon(new ImageIcon(imageURL, "Undo"));
        add(button);


        button = new JButton();
        imgLocation = "images/"
                + "cloud"
                + ".png";

        imageURL = DrawingToolBar.class.getResource(imgLocation);

        button.setActionCommand(ActionCommand.UNDO);
        button.setToolTipText("undo");
        button.addActionListener(actionListener);
        button.setIcon(new ImageIcon(imageURL, "Undo"));
        add(button);

    }
}
