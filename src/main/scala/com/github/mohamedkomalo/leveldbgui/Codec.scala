package com.github.mohamedkomalo.leveldbgui

import javax.xml.bind.DatatypeConverter

/**
 * Created by Mohamed Kamal on 10/31/2015.
 */
trait Codec {
  def codecName: String

  def decode(value: Array[Byte]): String

  def encode(value: String): Array[Byte]
}

object Codec {
  private val codecs = Seq(UTF8String, Hex).map(codec => codec.codecName -> codec).toMap

  def availableCodecs: Seq[Codec] = codecs.values.toSeq

  def apply(codecName: String): Codec = codecs(codecName)

  object UTF8String extends Codec {
    def codecName = "String (UTF-8)"

    override def decode(value: Array[Byte]): String = new String(value, "UTF-8")

    override def encode(value: String): Array[Byte] = value.getBytes("UTF-8")
  }

  object Hex extends Codec {
    def codecName = "Hex"

    def decode(value: Array[Byte]): String = DatatypeConverter.printHexBinary(value)

    def encode(value: String): Array[Byte] = DatatypeConverter.parseHexBinary(value)
  }

}