/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.Project;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author hoffmann
 */
public class XMLPersistenceFactory {

    public void marshal(IProject projectInterface, File outputFile) throws JAXBException, IllegalArgumentException {
        if (projectInterface instanceof Project) {
            JAXBContext jc;
            jc = JAXBContext.newInstance(Project.class);
            JAXBElement<Project> je2 = new JAXBElement<Project>(new QName("proteusProject"), Project.class, (Project) projectInterface);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(je2, outputFile);
        } else {
            throw new IllegalArgumentException("Argument projectInterface must be of type de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.Project!");
        }
    }

    public IProject unmarshal(File inputFile) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Project.class);
        StreamSource xml = new StreamSource(inputFile);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        JAXBElement<Project> je1 = unmarshaller.unmarshal(xml, Project.class);
        return je1.getValue();
    }
}
