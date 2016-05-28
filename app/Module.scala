import com.google.inject.AbstractModule
import java.time.Clock

import com.nakoradio.scalc.core.parser.ExpressionEvaluator
import com.nakoradio.scalc.core.parser.PatternExpEvaluator

class Module extends AbstractModule {

  override def configure() = {
    // Bind expression evaluator
    bind(classOf[ExpressionEvaluator]).to(classOf[PatternExpEvaluator])
  }

}
