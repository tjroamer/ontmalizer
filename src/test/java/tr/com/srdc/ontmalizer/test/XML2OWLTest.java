/**
 *
 */
package tr.com.srdc.ontmalizer.test;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.com.srdc.ontmalizer.XML2OWLMapper;
import tr.com.srdc.ontmalizer.XSD2OWLMapper;

/**
 * @author Mustafa
 *
 */
public class XML2OWLTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(XML2OWLTest.class);

    @Test
    public void createFirstPrototypeCDAInstances() {

        // This part converts XML schema to OWL ontology.
        XSD2OWLMapper mapping = new XSD2OWLMapper(new File("src/test/resources/CDA/CDA.xsd"));
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();

        File folder = new File("src/test/resources/CDA/first-prot");
        File[] files = folder.listFiles();
        if(files != null) {
	        for (File child : files) {
	            String inName = child.getName();
	            String outName = inName.substring(0, inName.lastIndexOf(".")) + "-cda.n3";
	
	            // This part converts XML instance to RDF data model.
	            XML2OWLMapper generator = new XML2OWLMapper(child, mapping);
	            generator.convertXML2OWL();
	
	            // This part prints the RDF data model to the specified file.
	            try {
	                File f = new File("src/test/resources/output/first-prot/" + outName);
	                f.getParentFile().mkdirs();
	                FileOutputStream fout = new FileOutputStream(f);
	                generator.writeModel(fout, "N3");
	                fout.close();
	
	            } catch (Exception e) {
	                LOGGER.error("{}", e.getMessage());
	            }
	        }
        }
    }

    @ParameterizedTest
    @CsvSource({
            "CDA/CDA.xsd,CDA/SALUS-sample-full-CDA-instance.xml",
            "salus-common-xsd/salus-cim.xsd,salus-common-xsd/salus-eligibility-instance.xml",
            "salus-common-xsd/salus-cim.xsd,salus-common-xsd/salus-cim-instance.xml",
            "test/test.xsd,test/test.xml"
    })
    public void createSALUSOntologyInstance(String xsdFileName, String xmlFileName) {
        Path resourceDir = Paths.get("src","test", "resources");

        // This part converts XML schema to OWL ontology.
        File xsdFile = Paths.get(resourceDir.toString(), xsdFileName).toFile();
        XSD2OWLMapper mapping = new XSD2OWLMapper(xsdFile);
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();

        // This part converts XML instance to RDF data model.
        File xmlFile = Paths.get(resourceDir.toString(), xmlFileName).toFile();
        XML2OWLMapper generator = new XML2OWLMapper(xmlFile, mapping);
        generator.convertXML2OWL();

        // This part prints the RDF data model to the specified file.
        try {
            String ttlFileName = xmlFileName.substring(0, xmlFileName.lastIndexOf('.')) + ".ttl";
            File f = Paths.get(resourceDir.toString(), "output", ttlFileName).toFile();
            f.getParentFile().mkdirs();
            FileOutputStream fout = new FileOutputStream(f);
            generator.writeModel(fout, "Turtle");
            fout.close();

        } catch (Exception e) {
            LOGGER.error("{}", e.getMessage());
        }
    }
}
