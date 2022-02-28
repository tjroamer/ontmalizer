/**
 *
 */
package tr.com.srdc.ontmalizer.helper;

import com.sun.xml.xsom.parser.AnnotationContext;
import com.sun.xml.xsom.parser.AnnotationParser;
import org.xml.sax.*;

/**
 * @author mustafa
 *
 */
public class XsdAnnotationParser extends AnnotationParser {

    private final StringBuilder documentation = new StringBuilder();

    @Override
    public ContentHandler getContentHandler(AnnotationContext context,
            String parentElementName, ErrorHandler handler, EntityResolver resolver) {
        return new ContentHandler() {
            private boolean parsingDocumentation = false;

            @Override
            public void characters(char[] ch, int start, int length) {
                if (parsingDocumentation) {
                    documentation.append(ch, start, length);
                }
            }

            @Override
            public void endElement(String uri, String localName, String name) {
                if (localName.equals("documentation")) {
                    parsingDocumentation = false;
                }
            }

            @Override
            public void startElement(String uri, String localName, String name, Attributes atts) {
                if (localName.equals("documentation")) {
                    parsingDocumentation = true;
                }
            }

            @Override
            public void endDocument() {
                // TODO Auto-generated method stub

            }

            @Override
            public void endPrefixMapping(String prefix) {
                // TODO Auto-generated method stub

            }

            @Override
            public void ignorableWhitespace(char[] ch, int start, int length) {
                // TODO Auto-generated method stub

            }

            @Override
            public void processingInstruction(String target, String data) {
                // TODO Auto-generated method stub

            }

            @Override
            public void setDocumentLocator(Locator locator) {
                // TODO Auto-generated method stub

            }

            @Override
            public void skippedEntity(String name) {
                // TODO Auto-generated method stub

            }

            @Override
            public void startDocument() {
                // TODO Auto-generated method stub

            }

            @Override
            public void startPrefixMapping(String prefix, String uri) {
                // TODO Auto-generated method stub

            }
        };
    }

    @Override
    public Object getResult(Object existing) {
        return documentation.toString().trim();
    }

}
