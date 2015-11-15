package com.github.mohamedkomalo.leveldbgui

import java.io.File

import com.github.mohamedkomalo.util.event.Event

/**
 * Created by Mohamed Kamal on 10/23/2015.
 */
trait LeveldbGuiClientView {
  type SelectedFolder = Option[File]
  type KeyValue = (String, String)

  def showChooseFolderDialog(currentFolder: File): SelectedFolder
  def showKeyValueDialog(initialKeyValue: KeyValue = ("", ""), keyEditable: Boolean = true): Option[KeyValue]

  val onOpenDbRequested = new Event[Unit]

  def leveldbView: LeveldbView
  def showClient(): Unit

  trait LeveldbView {
    def update(): Unit

    def setCodecs(encodings: Seq[String])
    def selectedKeyCodec: String
    def selectedValueCodec: String

    def setDbFolder(dbFolder: String)
    def setKeysValues(keysValues: Seq[KeyValue])
    def selectedKeyValueIndex: Option[Int]

    val onAddKeyValueRequested = new Event[Unit]
    val onEditSelectedKeyValueRequested = new Event[Unit]
    val onDeletedSelectedKeyValueRequested = new Event[Unit]
    val onCloseDbRequested = new Event[Unit]

    val onSelectedKeyCodecChanged = new Event[Unit]
    val onSelectedValueCodecChanged = new Event[Unit]
  }
}

