package com.github.mohamedkomalo.leveldbgui

import javax.swing.{SwingUtilities, UIManager}

/**
 * Created by Mohamed Kamal on 11/6/2015.
 */
object Main extends App {
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  SwingUtilities.invokeLater(new Runnable() {
    def run() {
      try {
        val presenter = new LeveldbGuiClientPresenter(new LeveldbGuiClientViewImpl)
        presenter.start()
      }
      catch { case e: Throwable => e.printStackTrace() }
    }
  })
}