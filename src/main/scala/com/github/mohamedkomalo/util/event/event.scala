package com.github.mohamedkomalo.util.event

import scala.ref.WeakReference

class Event[EventType] {
  private var handlers: Set[(EventType) => Unit] = Set.empty

  def fire(eventObj: EventType) = handlers foreach (handler => handler(eventObj))

  private[event] def subscribe(handler: (EventType) => Unit): Unit = handlers = handlers + handler

  private[event] def unsubscribe(handler: (EventType) => Unit): Unit = handlers = handlers - handler
}

object EventListener {
  def listenTo[T](event: Event[T])(handler: T => Unit) = new EventListener(WeakReference(event), WeakReference(handler))
}

class EventListener[T] private(eventRef: WeakReference[Event[T]], handlerRef: WeakReference[T => Unit]) {

  eventRef.get.get subscribe handlerRef.get.get

  def stopListening() = {
    for {
      event <- eventRef.get
      handler <- handlerRef.get
    } yield event unsubscribe handler
  }
}