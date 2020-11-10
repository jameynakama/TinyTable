package com.jameyiguess.tinytable

import java.io.{BufferedWriter, File, FileWriter}

object WriteAheadLogger {
  def writeToDisk(obj: Entry): Unit = {
    val file = new File("log.wal")
    val shouldAppend = file.exists()
    val bw = new BufferedWriter(new FileWriter(file, shouldAppend))
    bw.write(s"${obj.action},${obj.key},${obj.value}")
    bw.newLine()
    bw.close()
  }
}
