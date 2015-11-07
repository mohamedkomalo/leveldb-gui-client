package com.github.mohamedkomalo.leveldbgui

import java.io.File

import com.github.mohamedkomalo.util.event.Event
import com.github.mohamedkomalo.util.ManagedResource._
import org.fusesource.leveldbjni.JniDBFactory._
import org.iq80.leveldb.{Options, DB}
import scala.collection.JavaConversions._

/**
 * Created by Mohamed Kamal on 10/31/2015.
 */
class LeveldbModel {
  private var db: Option[DB] = None
  private var _path: Option[File] = None

  val onDbOpened: Event[Unit] = new Event[Unit]
  val onDbChanged: Event[Unit] = new Event[Unit]
  val onDbClosed: Event[Unit] = new Event[Unit]

  def path = _path

  def open(dbFolder: File) = {
    close()
    db = Option(factory.open(dbFolder, new Options()))
    _path = Option(dbFolder)

    onDbOpened.fire()
  }

  def close() = {
    db.foreach(_.close())
    db = None
    _path = None
    onDbClosed.fire()
  }

  def readAll(): Seq[(Array[Byte], Array[Byte])] = {
    var values = Seq.empty[(Array[Byte], Array[Byte])]
    db.foreach { db =>
      using(db.iterator()) { iterator =>
        iterator.seekToFirst()
        iterator foreach { case entry =>
          val key = entry.getKey
          val value = entry.getValue
          values = values :+ (key -> value)
        }
      }
    }
    values
  }

  def write(key: Array[Byte], value: Array[Byte]) = {
    db foreach (_.put(key, value))
    onDbChanged.fire()
  }
}