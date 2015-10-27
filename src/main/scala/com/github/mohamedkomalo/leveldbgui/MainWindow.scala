package com.github.mohamedkomalo.leveldbgui

import java.awt.event.{ActionEvent, ActionListener, MouseEvent, MouseListener}
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
  var keyValues = Seq.empty[(Array[Byte], Array[Byte])]

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

  dbTable.setModel(new AbstractTableModel() {
    val columnNames = Array("Key", "Value")

    override def getColumnName(column: Int) = columnNames(column)

    override def getColumnCount = columnNames.length

    override def isCellEditable(rowIndex: Int, columnIndex: Int) = false

    override def getRowCount: Int = keyValues.size

    override def getValueAt(rowIndex: Int, columnIndex: Int): AnyRef = columnIndex match {
      case 0 => DatatypeConverter.printHexBinary(keyValues(rowIndex)._1)
      case 1 => new String(keyValues(rowIndex)._2, "UTF-8")
    }
  })

  def reloadDB(dbPath: String) = {
    dbPathField.setText(dbPath)
    keyValues = new LevelDbLoader(dbPath).load
    dbTable.revalidate()
    dbTable.repaint()
  }

  dbTable.addMouseListener(new MouseListener {
    override def mouseExited(e: MouseEvent): Unit = {}
    override def mouseClicked(e: MouseEvent): Unit = {}
    override def mouseEntered(e: MouseEvent): Unit = {}
    override def mousePressed(e: MouseEvent): Unit = {
      if(e.getClickCount == 2) {
        val selectedRow = dbTable.rowAtPoint(e.getPoint)
        val keyValue = keyValues(selectedRow)
        new RowWindow(keyValue._1, keyValue._2).setVisible(true)
      }
    }
    override def mouseReleased(e: MouseEvent): Unit = {}
  })
}

class RowWindow(keyBytes: Array[Byte], valueBytes: Array[Byte]) extends GeneratedRowWindow {
  val key = new String(keyBytes, "UTF-8")
  val value = new String(valueBytes, "UTF-8")

  keyTextArea.setText(key)
  valueTextArea.setText(value)
}