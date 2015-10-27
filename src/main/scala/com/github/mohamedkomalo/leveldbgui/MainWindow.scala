package com.github.mohamedkomalo.leveldbgui

import java.awt.event._
import java.io.File
import javax.swing.table.AbstractTableModel
import javax.swing.{JFileChooser, SwingUtilities, UIManager}
import javax.xml.bind.DatatypeConverter

import com.github.mohamedkomalo.util.LevelDbLoader
import com.github.mohamedkomalo.util.ManagedResource._
import org.fusesource.leveldbjni.JniDBFactory._
import org.iq80.leveldb.Options


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

  val STRING = "String (UTF-8)"
  val HEX = "Hex"

  keyEncodingBox.addItem(STRING)
  keyEncodingBox.addItem(HEX)

  valueEncodingBox.addItem(STRING)
  valueEncodingBox.addItem(HEX)

  private val listener = new ItemListener {
    override def itemStateChanged(e: ItemEvent): Unit = updateTable
  }

  keyEncodingBox.addItemListener(listener)
  valueEncodingBox.addItemListener(listener)

  def updateTable: Unit = {
    dbTable.revalidate()
    dbTable.repaint()
  }

  case class Codec(encoding: String) {
    def decode(value: Array[Byte]): String = encoding match {
      case STRING => new String(value)
      case HEX => DatatypeConverter.printHexBinary(value)
    }

    def encode(value: String): Array[Byte] = encoding match {
      case STRING => bytes(value)
      case HEX => DatatypeConverter.parseHexBinary(value)
    }
  }

  def keyCodec: Codec = Codec(keyEncodingBox.getSelectedItem.asInstanceOf[String])
  def valueCodec: Codec = Codec(valueEncodingBox.getSelectedItem.asInstanceOf[String])

  def keyEncoding: String = {
    keyEncodingBox.getSelectedItem.asInstanceOf[String]
  }

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
      case 0 => keyCodec.decode(keyValues(rowIndex)._1)
      case 1 => valueCodec.decode(keyValues(rowIndex)._2)
    }
  })

  def reloadDB(dbPath: String) = {
    dbPathField.setText(dbPath)
    keyValues = new LevelDbLoader(dbPath).load
    updateTable
  }

  dbTable.addMouseListener(new MouseListener {
    override def mouseExited(e: MouseEvent): Unit = {}
    override def mouseClicked(e: MouseEvent): Unit = {}
    override def mouseEntered(e: MouseEvent): Unit = {}
    override def mousePressed(e: MouseEvent): Unit = {
      if(e.getClickCount == 2) {
        val selectedRow = dbTable.rowAtPoint(e.getPoint)
        val keyValue = keyValues(selectedRow)
        val rowWindow = new RowWindow(keyValue._1, keyValue._2)
        rowWindow.setVisible(true)
        if(rowWindow.approved){
          using(factory.open(new File(dbPathField.getText()), new Options())) { db =>
            db.put(keyCodec.encode(rowWindow.key), valueCodec.encode(rowWindow.value))
          }
          reloadDB(dbPathField.getText())
        }
      }
    }
    override def mouseReleased(e: MouseEvent): Unit = {}
  })

  addRowButton.addActionListener(new ActionListener {
    override def actionPerformed(e: ActionEvent): Unit = {
      val rowWindow = new RowWindow()
      rowWindow.setVisible(true)
      if(rowWindow.approved){
        using(factory.open(new File(dbPathField.getText()), new Options())) { db =>
          db.put(keyCodec.encode(rowWindow.key), valueCodec.encode(rowWindow.value))
        }
        reloadDB(dbPathField.getText())
      }
    }
  })

  class RowWindow(keyBytes: Array[Byte] = Array(), valueBytes: Array[Byte] = Array()) extends GeneratedRowWindow(MainWindow.this) {
    var approved = false

    keyTextArea.setText(keyCodec.decode(keyBytes))
    valueTextArea.setText(valueCodec.decode(valueBytes))

    def key = keyTextArea.getText
    def value = valueTextArea.getText

    okButton.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        approved = true
        setVisible(false)
      }
    })

    cancelButton.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        approved = false
        setVisible(false)
      }
    })
  }
}