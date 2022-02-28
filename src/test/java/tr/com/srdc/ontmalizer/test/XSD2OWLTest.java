/**
 *
 */
package tr.com.srdc.ontmalizer.test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.com.srdc.ontmalizer.XSD2OWLMapper;

/**
 * @author Mustafa
 *
 */

public class XSD2OWLTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(XSD2OWLTest.class);

    @ParameterizedTest
    @ValueSource(strings = {
            "CDA/CDA.xsd",
            "salus-common-xsd/salus-cim.xsd",
            "test/test.xsd"
    })
    public void createOntology(String xsdFileName) {

        Path resourceDir = Paths.get("src","test", "resources");
        File xsdFile = Paths.get(resourceDir.toString(), xsdFileName).toFile();
        XSD2OWLMapper mapping = new XSD2OWLMapper(xsdFile);
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();

        // This part prints the ontology to the specified file.
        FileOutputStream ont;
        try {
            String ttlFileName = xsdFileName.substring(0, xsdFileName.lastIndexOf('.')) + ".ttl";
            File f = Paths.get(resourceDir.toString(), "output", ttlFileName).toFile();
            f.getParentFile().mkdirs();
            ont = new FileOutputStream(f);
            mapping.writeOntology(ont, "Turtle");
            ont.close();
        } catch (Exception e) {
            LOGGER.error("{}", e.getMessage());
        }
    }
}
