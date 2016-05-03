package checkout

import pricing.PricingRules
import scala.collection.mutable.ArrayBuffer

class CheckOut(pricingRules: PricingRules) {
  private var runningTotal = 0.0
  private val itemsScanned = ArrayBuffer.empty[String]

  def scan(itemName: String): Unit = {
    val price = pricingRules.getStandardPrice(itemName)
    itemsScanned += itemName
    println(itemsScanned.mkString(", "))
    runningTotal += price
  }

  def itemsScannedTillNow = itemsScanned.toList

  def total = {
    optimizeItems()
    runningTotal
  }
  
  def optimizeItems() = {
    
  }
}