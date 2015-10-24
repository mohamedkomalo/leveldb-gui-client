package com.github.mohamedkomalo.util

import java.io.Closeable

/**
 * Created by Mohamed Kamal on 10/24/2015.
 */
object ManagedResource {
   def using[T <: Closeable](resource: T)(func: T => Unit) = {
     try { func(resource) }
     finally {
       try { resource.close() }
       finally { }
     }
   }
 }
