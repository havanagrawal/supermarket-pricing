package checkout

import org.scalatest.FlatSpec
import pricing.PricingRules
import pricing.NoSuchRuleException
import org.scalatest.Matchers

class CheckoutTest extends FlatSpec with Matchers{
  
  val basicPricingRules = PricingRules.fromFile("src/test/resources/basicrulestest.txt") 
  
  behavior of "A checkout"
  
  it should "be instantiable with PricingRules" in {
    val checkout = new CheckOut(basicPricingRules)
    assert(checkout != null)
  }
  
  it should "be able to scan a single item" in {
    val checkout = new CheckOut(basicPricingRules)
    checkout.scan("A")
    assert(checkout.itemsScannedTillNow.length == 1)
  }
  
  it should "throw NoSuchRuleException if invalid item is requested" in {
    val checkout = new CheckOut(basicPricingRules)
    a [NoSuchRuleException] should be thrownBy {
      checkout.scan("Z") 
    }
  }
  
  it should "maintain a correct running total" in {
    val checkout = new CheckOut(basicPricingRules)
    (1 to 5).foreach{i =>
      checkout.scan("B")
    }
    assert(checkout.total == 250)
  }
  
  it should "account for special offers" in {
    val checkout = new CheckOut(basicPricingRules)
    (1 to 3).foreach(i => checkout.scan("A"))
    assert(checkout.total == 130)
  }
  
  it should "apply a special offer only once" in {
    val checkout = new CheckOut(basicPricingRules)
    (1 to 3).foreach(i => checkout.scan("A"))
    assert(checkout.total == 130)
    assert(checkout.total == 130)
  }
}