package checkout

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map

import pricing.PricingRules

class CheckOut(pricingRules: PricingRules) {
  private var runningTotal = 0.0

  private val itemsScanned = Map.empty[String, Int]

  def scan(itemName: String): Unit = {
    val price = pricingRules.getStandardPrice(itemName)
    itemsScanned.put(itemName, itemsScanned.getOrElse(itemName, 0) + 1)
    runningTotal += price
  }

  def total = {
    optimizeItemsIfNeeded()
    runningTotal
  }

  def itemsScannedTillNow = itemsScanned.flatMap{case (item, count) => List.fill(count)(item)}.toList
  
  def optimizeItemsIfNeeded() = {
    itemsScanned.foreach {
      case (itemName, freq) => {
        val standardPrice = pricingRules.getStandardPrice(itemName)
        pricingRules.getSpecialOfferPrice(itemName).foreach {
          case (count, cost) => {
            val canBeGrouped = freq / count
            runningTotal -= canBeGrouped * count * standardPrice
            runningTotal += cost
          }
        }
      }
    }
  }
}