package com.github.mohamedkomalo.leveldbgui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mohamed Kamal on 10/23/2015.
 */
public class GeneratedMainWindow extends JFrame {

    protected JPanel content;
    protected JButton browseButton;
    protected JTextField dbPathField;
    protected JTable dbTable;
    protected JPanel header;
    protected JPanel body;
    private JPanel panel;
    protected JButton addRowButton;

    public GeneratedMainWindow() {
        setTitle("Leveldb GUI Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);

        content = new JPanel();
        content.setLayout(new BorderLayout(0, 10));
        content.setOpaque(true);
        content.setPreferredSize(new Dimension(600, 500));
        content.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null));

        setContentPane(content);

        header = new JPanel();
        header.setLayout(new BorderLayout(10, 0));
        header.setPreferredSize(new Dimension(179, 32));
        content.add(header, BorderLayout.NORTH);
        browseButton = new JButton();
        browseButton.setText("Browse Database Folder");
        header.add(browseButton, BorderLayout.EAST);
        dbPathField = new JTextField();
        dbPathField.setEditable(false);
        header.add(dbPathField, BorderLayout.CENTER);
        body = new JPanel();
        body.setLayout(new BorderLayout(0, 0));
        content.add(body, BorderLayout.CENTER);
        body.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));

        panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        body.add(panel, BorderLayout.NORTH);

        addRowButton = new JButton("Add");
        panel.add(addRowButton);
        JScrollPane scrollPane1 = new JScrollPane();
        body.add(scrollPane1, BorderLayout.CENTER);
        dbTable = new JTable();
        dbTable.setEnabled(true);
        scrollPane1.setViewportView(dbTable);
        setLocationRelativeTo(null);
    }

}
