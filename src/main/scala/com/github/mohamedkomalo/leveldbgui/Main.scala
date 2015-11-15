package com.github.mohamedkomalo.leveldbgui

/**
 * Created by Mohamed Kamal on 11/6/2015.
 */
object Main extends App {
  val presenter = new LeveldbGuiClientPresenter(new LeveldbGuiClientViewImpl)
  presenter.start()
}