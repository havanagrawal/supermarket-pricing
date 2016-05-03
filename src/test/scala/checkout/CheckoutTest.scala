package checkout

import org.scalatest.FlatSpec
import pricing.PricingRules
import pricing.NoSuchRuleException
import org.scalatest.Matchers

class CheckoutTest extends FlatSpec with Matchers{
  
  val basicPricingRules = PricingRules.fromFile("src/test/resources/basicrulestest.txt") 
  
  "A checkout" should "be instantiable with PricingRules" in {
    val checkout = new CheckOut(basicPricingRules)
    assert(checkout != null)
  }
  
  "A checkout" should "be able to scan a single item" in {
    val checkout = new CheckOut(basicPricingRules)
    checkout.scan("A")
    assert(checkout.itemsScannedTillNow.length == 1)
  }
  
  "A checkout" should "throw NoSuchRuleException if invalid item is requested" in {
    val checkout = new CheckOut(basicPricingRules)
    a [NoSuchRuleException] should be thrownBy {
      checkout.scan("Z") 
    }
  }
  
  "A checkout" should "maintain a correct running total" in {
    val checkout = new CheckOut(basicPricingRules)
    (1 to 5).foreach{i =>
      checkout.scan("B")
    }
    assert(checkout.total == 250)
  }
  
  "A checkout" should "account for special offers" in {
    val checkout = new CheckOut(basicPricingRules)
    (1 to 3).foreach(i => checkout.scan("A"))
    assert(checkout.total == 130)
  }
}