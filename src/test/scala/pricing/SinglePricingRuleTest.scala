package pricing
import org.scalatest.FlatSpec

class SinglePricingRuleTest extends FlatSpec{
  "A single rule" should "have special price or None" in {
    val ruleWithSpecialOffer = new SinglePricingRule("A", 50, "3 for 130")
    assert(ruleWithSpecialOffer.specialOffer == Some("3 for 130"))
    
    val ruleWithNoOffer = new SinglePricingRule("A", 50)
    assert(ruleWithNoOffer.specialOffer == None)
  }
}