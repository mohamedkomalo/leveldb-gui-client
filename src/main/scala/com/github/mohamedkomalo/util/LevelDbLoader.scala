package com.github.mohamedkomalo.util

import java.io.File

import com.github.mohamedkomalo.util.ManagedResource._
import org.fusesource.leveldbjni.JniDBFactory._
import org.iq80.leveldb.Options

import scala.collection.JavaConversions._

/**
 * Created by Mohamed Kamal on 10/24/2015.
 */
class LevelDbLoader(dbPath: String) {
   def load: Seq[(Array[Byte], Array[Byte])] = {
     var list = Seq.empty[(Array[Byte], Array[Byte])]
     using(factory.open(new File(dbPath), new Options())) { db =>
       using(db.iterator()) { iterator =>
         iterator.seekToFirst()
         iterator foreach { case entry =>
           val key = entry.getKey
           val value = entry.getValue
           list = list :+ (key -> value)
         }
       }
     }
     list
   }
 }
