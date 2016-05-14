package checkout

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map

import pricing.PricingRules

class CheckOut(pricingRules: PricingRules) {
  private var runningTotal = 0.0

  private val itemsScanned = Map.empty[String, Int]
  private val itemsWithOfferApplied = Map.empty[String, Int]
  private val itemsWithOfferNotApplied = Map.empty[String, Int]

  def scan(itemName: String): Unit = {
    val standardPrice = pricingRules.getStandardPrice(itemName)
    val specialPriceWithCount = pricingRules.getSpecialOfferPrice(itemName)
    val currentItemCount = itemsWithOfferNotApplied.getOrElse(itemName, 0)
    
    if (specialPriceWithCount.isSuccess){
      val countForSpecialPriceEligibility = specialPriceWithCount.get._1
      
      if (currentItemCount + 1 == countForSpecialPriceEligibility) {
        itemsWithOfferNotApplied.put(itemName, 0)
        itemsWithOfferApplied.put(itemName, itemsWithOfferApplied.getOrElse(itemName, 0) + countForSpecialPriceEligibility)
        val specialPrice = specialPriceWithCount.get._2
        runningTotal -= (countForSpecialPriceEligibility - 1) * standardPrice
        runningTotal += specialPrice
      }
      else {
        itemsWithOfferNotApplied.put(itemName, currentItemCount + 1)
        runningTotal += standardPrice
      }
    }
    else {
      itemsWithOfferNotApplied.put(itemName, currentItemCount + 1)
      runningTotal += standardPrice
    }
  }

  def total = runningTotal

  def itemsScannedTillNow = {
    itemsWithOfferNotApplied.flatMap{case (item, count) => List.fill(count)(item)}.toList ++
    itemsWithOfferApplied.flatMap{case (item, count) => List.fill(count)(item)}.toList
  }
    
      
}