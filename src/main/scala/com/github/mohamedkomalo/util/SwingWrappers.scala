package com.github.mohamedkomalo.util

import java.awt.{ItemSelectable, Component}
import java.awt.event._
import javax.swing.AbstractButton

/**
 * Created by Mohamed Kamal on 10/30/2015.
 */
object SwingWrappers {
  implicit class AbstractButtonScala(abstractButton: AbstractButton) {
    def onAction(func: ActionEvent => Unit) = {
      abstractButton.addActionListener(new ActionListener {
        override def actionPerformed(e: ActionEvent): Unit = func(e)
      })
    }
  }

  implicit class ComponentScala(component: Component) {
    def onMousePressed(func: MouseEvent => Unit) = {
      component.addMouseListener(new MouseListener {
        override def mouseExited(e: MouseEvent): Unit = {}

        override def mouseClicked(e: MouseEvent): Unit = {}

        override def mouseEntered(e: MouseEvent): Unit = {}

        override def mousePressed(e: MouseEvent): Unit = func(e)

        override def mouseReleased(e: MouseEvent): Unit = {}
      })
    }
  }

  implicit class ItemSelectableScala(component: ItemSelectable) {
    def onSelectedItemChanged(func: ItemEvent => Unit) = {
      component.addItemListener(new ItemListener {
        override def itemStateChanged(e: ItemEvent): Unit = func(e)
      })
    }
  }

}
