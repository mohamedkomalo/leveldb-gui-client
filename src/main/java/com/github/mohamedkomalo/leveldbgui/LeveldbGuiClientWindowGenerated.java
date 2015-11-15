package com.github.mohamedkomalo.leveldbgui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Mohamed Kamal on 10/23/2015.
 */
public class LeveldbGuiClientWindowGenerated extends JFrame {

    public JPanel content;
    protected JButton browseButton;
    protected JTextField dbPathField;
    protected JTable dbTable;
    protected JPanel header;
    protected JPanel body;
    private JPanel panel;
    protected JButton addKeyValueButton;
    private JPanel panel_1;
    private JLabel lblKeyEncoding;
    private JLabel label;
    protected JComboBox<String> keyEncodingBox;
    protected JComboBox<String> valueEncodingBox;
    protected JButton deleteButton;
    private JPanel panel_2;
    private JPanel panel_3;
    private JPanel panel_4;
    protected JTextField keySearchTextField;
    private JLabel lblNewLabel;

    public LeveldbGuiClientWindowGenerated() {
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

        panel_3 = new JPanel();
        body.add(panel_3, BorderLayout.NORTH);
        panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));

        panel = new JPanel();
        panel_3.add(panel);
        panel.setLayout(new BorderLayout(0, 0));

        panel_2 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
        panel.add(panel_2, BorderLayout.WEST);

        addKeyValueButton = new JButton("Add");
        panel_2.add(addKeyValueButton);

        deleteButton = new JButton("Delete");
        panel_2.add(deleteButton);

        panel_1 = new JPanel();
        panel.add(panel_1, BorderLayout.EAST);

        label = new JLabel("key encoding");
        panel_1.add(label);

        keyEncodingBox = new JComboBox();
        keyEncodingBox.setPreferredSize(new Dimension(100, 20));
        panel_1.add(keyEncodingBox);

        lblKeyEncoding = new JLabel("value encoding");
        panel_1.add(lblKeyEncoding);

        valueEncodingBox = new JComboBox();
        valueEncodingBox.setPreferredSize(new Dimension(100, 20));
        panel_1.add(valueEncodingBox);

        panel_4 = new JPanel();
        panel_4.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel_3.add(panel_4);
        panel_4.setLayout(new BorderLayout(10, 10));

        keySearchTextField = new JTextField();
        panel_4.add(keySearchTextField, BorderLayout.CENTER);
        keySearchTextField.setColumns(10);

        lblNewLabel = new JLabel("Search key");
        panel_4.add(lblNewLabel, BorderLayout.WEST);
        JScrollPane scrollPane1 = new JScrollPane();
        body.add(scrollPane1, BorderLayout.CENTER);
        dbTable = new JTable();
        dbTable.setEnabled(true);
        scrollPane1.setViewportView(dbTable);
        setLocationRelativeTo(null);
    }

}
