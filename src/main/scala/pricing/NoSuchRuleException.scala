package pricing

class NoSuchRuleException(message: String, cause: Throwable) extends RuntimeException(message) {
  def this(message: String) = this(message, null)
}

object NoSuchRuleException {
  def apply(message: String) = new NoSuchRuleException(message)
  def apply(message: String, cause: Throwable) = new NoSuchRuleException(message, cause)
}