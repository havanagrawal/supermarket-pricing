package pricing

class SinglePricingRule(val name: String, val price: Double, val specialOffer: Option[String]) {
  def this(name: String, price: Double) = this(name, price, None)
  def this(name: String, price: Double, specialOffer: String) = this(name, price, Some(specialOffer))

  override def equals(other: Any): Boolean = other match {
    case that: SinglePricingRule => (
      that.canEqual(this)
      && that.name == this.name
      && that.price == this.price
      && that.specialOffer == this.specialOffer)
  }

  def canEqual(that: Any): Boolean = that.isInstanceOf[SinglePricingRule]

  def specialOfferInterpret() = {
    val (count, price) = specialOffer.map(offer => offer.split(" ").toList match {
      case count :: "for" :: price :: _ => (count.toInt, price.toDouble)
      case _                            => throw new IllegalArgumentException("The format of the special offer could not be recognized")
    }).get
    (count, price)
  }
}