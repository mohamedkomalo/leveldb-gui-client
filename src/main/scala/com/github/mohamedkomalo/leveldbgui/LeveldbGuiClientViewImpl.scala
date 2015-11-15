package com.github.mohamedkomalo.leveldbgui

import java.io.File
import javax.swing._
import javax.swing.table.AbstractTableModel

import com.github.mohamedkomalo.util.SwingWrappers._

/**
 * Created by Mohamed Kamal on 11/6/2015.
 */
class LeveldbGuiClientViewImpl extends LeveldbGuiClientWindowGenerated with LeveldbGuiClientView {
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
  SwingUtilities.updateComponentTreeUI(this)

  browseButton onAction { event => onOpenDbRequested.fire() }

  override def showKeyValueDialog(initialKeyValue: (String, String), keyEditable: Boolean = true) = {
    new KeyValueDialog(initialKeyValue, keyEditable).showDialog
  }

  override def showChooseFolderDialog(currentFolder: File) = {
    val fileChooser = new JFileChooser()
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
    fileChooser.setMultiSelectionEnabled(false)
    fileChooser.setCurrentDirectory(currentFolder)

    val dialogResult = fileChooser.showOpenDialog(this)
    
    if (dialogResult == JFileChooser.APPROVE_OPTION) Option(fileChooser.getSelectedFile) else None
  }

  override def showErrorMessage(message: String): Unit = {
    val textArea = new JTextArea(20, 80)
    textArea.setEditable(false)
    textArea.setText(message)
    textArea.setFont(UIManager.getFont("TextField.font"))
    textArea.setCaretPosition(0)
    JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Error", JOptionPane.ERROR_MESSAGE)
  }

  override def showClient(): Unit = {
    SwingUtilities invokeLater new Runnable() {
      def run(): Unit = setVisible(true)
    }
  }

  override def leveldbView: LeveldbView = new LeveldbView {

    keyEncodingBox onSelectedItemChanged { e => onSelectedKeyCodecChanged.fire()}

    valueEncodingBox onSelectedItemChanged { e => onSelectedValueCodecChanged.fire() }

    dbTable onMousePressed { event =>
      if (event.getClickCount == 2) onEditSelectedKeyValueRequested.fire()
    }

    addKeyValueButton onAction { event => onAddKeyValueRequested.fire() }
    deleteButton onAction { event => onDeletedSelectedKeyValueRequested.fire() }

    override def setCodecs(encodings: Seq[String]): Unit = {
      encodings.foreach { codecName =>
        keyEncodingBox.addItem(codecName)
        valueEncodingBox.addItem(codecName)
      }
    }

    override def selectedKeyValueIndex = if(dbTable.getSelectedRow == -1) None else Option(dbTable.getSelectedRow)

    override def selectedKeyCodec: String = keyEncodingBox.getSelectedItem.asInstanceOf[String]

    override def selectedValueCodec: String = valueEncodingBox.getSelectedItem.asInstanceOf[String]

    override def setDbFolder(dbFolder: String): Unit =  dbPathField.setText(dbFolder)

    override def setKeysValues(keysValues: Seq[(String, String)]): Unit = {
      dbTable.setModel {
        new AbstractTableModel {
          val columnNames = Array("Key", "Value")
          override def getColumnName(column: Int) = columnNames(column)
          override def getColumnCount = columnNames.length
          override def isCellEditable(rowIndex: Int, columnIndex: Int) = false
          override def getRowCount: Int = keysValues.size
          override def getValueAt(rowIndex: Int, columnIndex: Int): AnyRef = columnIndex match {
            case 0 => keysValues(rowIndex)._1
            case 1 => keysValues(rowIndex)._2
          }
        }
      }
    }

    override def update(): Unit = {
      dbTable.revalidate()
      dbTable.repaint()
    }

    override def keySearchText: String = keySearchTextField.getText

    keySearchTextField onTextChanged { event => onKeySearchChanged.fire() }

    override def setSelectedKeyValueIndex(indexOp: Option[Int]): Unit = indexOp match {
      case Some(index) => dbTable.changeSelection(index, 0, false, false)
      case None => dbTable.clearSelection()
    }
  }

  class KeyValueDialog(keyValueIn: (String, String) = ("", ""), keyEditable: Boolean) extends KeyValueDialogGenerated(LeveldbGuiClientViewImpl.this) {
    private var approved = false

    keyTextArea.setEditable(keyEditable)

    keyValueIn match {
      case (key, value) =>
        keyTextArea.setText(key)
        valueTextArea.setText(value)
    }

    def keyValue = (keyTextArea.getText, valueTextArea.getText)

    okButton onAction { event => closeWithApproval(true) }
    cancelButton onAction { event => closeWithApproval(false ) }

    private def closeWithApproval(approval: Boolean) =  {
      this.approved = approval
      setVisible(false)
    }

    def showDialog: Option[(String, String)] = {
      setVisible(true)
      if(approved) Option(keyValue) else None
    }
  }
}