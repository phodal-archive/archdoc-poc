package markdown

import kotlin.test.Test
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser

internal class MarkdownParserTest {
    @Test
    internal fun name() {
        val src = "Some *Markdown*"
        val flavour = CommonMarkFlavourDescriptor()
        val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(src)
        println(parsedTree)
    }
}