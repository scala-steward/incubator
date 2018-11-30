package com.cmartin.learn

import scala.concurrent.duration._

package object test {

  object Constants {
    val registrationLVL = "ec-lvl"
    val registrationMIG = "ec-mig"
    val registrationMNS = "ec-mns"
    val barajasIataCode = "MAD"

    val madDestinationCount = 4
    val tableCount = 7

    val waitTimeout = 5.second
  }

}