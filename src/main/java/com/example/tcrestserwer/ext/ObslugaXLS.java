package com.example.tcrestserwer.ext;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.WebApplicationException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.util.JAXBSource;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;

public class ObslugaXSL {
    private ServletContext servletContext;

    public ObslugaXSL(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    // obiekt -(za pomocą JAXB)-> XML -(za pomocą transformera)-> XML(XSL-FO)
    // --(za pomocą Apache FOP)-> PDF -> output
    public void wypiszPDF(Object obj, OutputStream output) {
        try(InputStream configStream = servletContext.getResourceAsStream("WEB-INF/fop-conf.xml")) {

            JAXBContext ctx = JAXBContext.newInstance(obj.getClass());
            JAXBSource src = new JAXBSource(ctx, obj);

            FopFactory fopFactory = FopFactory.newInstance(new URI(""), configStream);

            try(BufferedOutputStream pdfOut = new BufferedOutputStream(output)) {
                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, pdfOut);
                TransformerFactory tf = TransformerFactory.newInstance();
                StreamSource xsl = new StreamSource(servletContext.getResourceAsStream("WEB-INF/sklep-fo.xsl"));

                Transformer tr = tf.newTransformer(xsl);
                Result res = new SAXResult(fop.getDefaultHandler());
                tr.transform(src, res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException("Problem FOP " + e.getMessage(), e, 500);
        }
    }

}



