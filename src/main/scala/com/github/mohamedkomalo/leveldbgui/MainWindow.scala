package com.github.mohamedkomalo.leveldbgui

import java.awt.event.{ActionEvent, ActionListener}
import java.io.File
import javax.swing.table.AbstractTableModel
import javax.swing.{JFileChooser, SwingUtilities, UIManager}
import javax.xml.bind.DatatypeConverter

import com.github.mohamedkomalo.util.LevelDbLoader


/**
 * Created by Mohamed Kamal on 10/23/2015.
 */
object MainWindow extends App {
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  SwingUtilities.invokeLater(new Runnable() {
    def run() {
      try { new MainWindow().setVisible(true) }
      catch { case e: Throwable => e.printStackTrace() }
    }
  })
}

class MainWindow extends GeneratedMainWindow {
  browseButton.addActionListener(new ActionListener {
    override def actionPerformed(e: ActionEvent): Unit = {
      val fileChooser = new JFileChooser()
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
      fileChooser.setMultiSelectionEnabled(false)

      if(dbPathField.getText.nonEmpty) {
        fileChooser.setCurrentDirectory(new File(dbPathField.getText).getParentFile)
      }

      if(fileChooser.showOpenDialog(content) == JFileChooser.APPROVE_OPTION) {
        reloadDB(fileChooser.getSelectedFile.getAbsolutePath)
      }
    }
  })

  def reloadDB(dbPath: String) = {
    dbPathField.setText(dbPath)

    val newKeyValues = new LevelDbLoader(dbPath).load

    val model = new AbstractTableModel() {
      val columnNames = Array("Key", "Value")
      override def getColumnName(column: Int) = columnNames(column)
      override def getColumnCount = columnNames.length
      override def isCellEditable(rowIndex: Int, columnIndex: Int) = false

      override def getRowCount: Int = newKeyValues.size
      override def getValueAt(rowIndex: Int, columnIndex: Int): AnyRef = columnIndex match {
        case 0 => DatatypeConverter.printHexBinary(newKeyValues(rowIndex)._1)
        case 1 => new String(newKeyValues(rowIndex)._2, "UTF-8")
      }
    }

    dbTable.setModel(model)
  }
}
