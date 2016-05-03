package pricing

import org.scalatest.FlatSpec

class PricingRulesTest extends FlatSpec{
  "Pricing rules for basicrulestest.txt" should "be readable" in {
    val rules = PricingRules.fromFile("src/test/resources/basicrulestest.txt")
    val singleRule = new SinglePricingRule("A", 50, "3 for 130")
    assert(rules.head == singleRule)
  }
}