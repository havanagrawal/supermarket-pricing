package pricing

import scala.io.Source
import scala.collection.{ immutable => im }
import scala.util.Try

class PricingRules(val pricingRules: im.Seq[SinglePricingRule]) {
  lazy val head = pricingRules.head
  lazy val tail = new PricingRules(pricingRules.tail)

  def getStandardPrice(name: String): Double = {
    val priceRuleForItem = getPricingRuleOrElseThrow(name)
    priceRuleForItem.price
  }

  def getSpecialOfferPrice(name: String): Try[(Int, Double)] = {
    val priceRuleForItem = Try {
      getPricingRuleOrElseThrow(name)
    }
    priceRuleForItem.map(_.specialOfferInterpret())
  }

  private def getPricingRuleOrElseThrow(name: String) = {
    pricingRules.find { _.name == name }.getOrElse(throw new NoSuchRuleException("The item you specified has no associated price rule"))
  }
}

object PricingRules {

  def fromFile(rulesFilePath: String) = {
    val lines = io.Source.fromFile(rulesFilePath).getLines()
    val pricingRules = lines.map { line =>
      line.split("\t").toList match {
        case name :: price :: offer :: _ => new SinglePricingRule(name, price.toDouble, offer)
        case name :: price :: _          => new SinglePricingRule(name, price.toDouble)
        case List(_) | Nil               => throw new IllegalArgumentException("The format of the content in the file is not supported.")
      }
    }.toList
    new PricingRules(pricingRules)
  }
}