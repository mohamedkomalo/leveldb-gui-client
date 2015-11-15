package com.github.mohamedkomalo.leveldbgui

import java.io._
import java.lang.Thread.UncaughtExceptionHandler

import com.github.mohamedkomalo.util.ManagedResource._
import com.github.mohamedkomalo.util.event.EventListener._

/**
 * Created by Mohamed Kamal on 11/6/2015.
 */
class LeveldbGuiClientPresenter(view: LeveldbGuiClientView) {
  private val leveldbPresenter = new LeveldbViewPresenter(view.leveldbView, new LeveldbModel)

  def start() = {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler {
      override def uncaughtException(t: Thread, e: Throwable): Unit = {
        try {
          val writer = new StringWriter()
          using(new PrintWriter(writer))(e.printStackTrace)

          view.showErrorMessage("An error occured, please report the following stacktrace to github:\n\n" +
            writer.getBuffer.toString)
        }
        catch {
          case throwable: Throwable => throwable.printStackTrace()
        }
      }
    })

    view.showClient()
  }

  listenTo(view.onOpenDbRequested) { event =>
    val currentFolder = leveldbPresenter.model.path.getOrElse(new File(""))
    view.showChooseFolderDialog(currentFolder).foreach(leveldbPresenter.model.open(_))
  }

  private case class LeveldbViewPresenter(levelDbView: view.LeveldbView, model: LeveldbModel) {
    private var values = Seq.empty: Seq[(Array[Byte], Array[Byte])]

    levelDbView.setCodecs(Codec.availableCodecs.map(_.codecName))

    listenTo(model.onDbOpened) { event =>
      view.leveldbView.setDbFolder(model.path.get.getAbsolutePath)
      reloadDb()
    }

    listenTo(model.onDbChanged) { event => reloadDb() }

    listenTo(levelDbView.onCloseDbRequested) { event => model.close() }

    listenTo(levelDbView.onSelectedKeyCodecChanged) { event => levelDbView.update }

    listenTo(levelDbView.onSelectedValueCodecChanged) { event => levelDbView.update }

    listenTo(levelDbView.onEditSelectedKeyValueRequested) { event =>
      levelDbView.selectedKeyValueIndex foreach { index =>
        val (key, value) = values(index)
        view
          .showKeyValueDialog((keyCodec.decode(key), valueCodec.decode(value)), false)
          .foreach { case (key, value) => model.write(keyCodec.encode(key), valueCodec.encode(value)) }
      }
    }

    listenTo(levelDbView.onDeletedSelectedKeyValueRequested) { event =>
      levelDbView.selectedKeyValueIndex foreach { index =>
        val (key, _) = values(index)
        model.delete(key)
      }
    }

    listenTo(levelDbView.onAddKeyValueRequested) { event =>
      view.showKeyValueDialog().foreach { case (key, value) =>
        model.write(keyCodec.encode(key), valueCodec.encode(value))
      }
    }

    private def reloadDb() = {
      values = model.readAll
      val valuesDecoded = values.view map { case (key, value) => (keyCodec.decode(key), valueCodec.decode(value)) }
      view.leveldbView.setKeysValues(valuesDecoded)
    }

    private def keyCodec: Codec = Codec(levelDbView.selectedKeyCodec)

    private def valueCodec: Codec = Codec(levelDbView.selectedValueCodec)
  }

}