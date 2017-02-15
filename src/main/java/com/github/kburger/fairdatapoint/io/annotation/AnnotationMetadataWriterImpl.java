package com.github.kburger.fairdatapoint.io.annotation;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kburger.fairdatapoint.io.MetadataWriter;
import com.github.kburger.fairdatapoint.model.Metadata;

/**
 * Annotation based {@link MetadataWriter} implementation.
 */
public class AnnotationMetadataWriterImpl implements MetadataWriter {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationMetadataWriterImpl.class);
    private static final ValueFactory FACTORY = SimpleValueFactory.getInstance();
    
    /** Holds cached analysis to prevent redundant analysis. */
    private Map<Class<?>, List<AnalyzedField>> cache;
    
    public AnnotationMetadataWriterImpl() {
        cache = new HashMap<>();
    }
    
    @Override
    public String write(Metadata metadata) {
        Class<? extends Metadata> clazz = metadata.getClass();
        
        Model model = new LinkedHashModel();
        model.setNamespace(DCTERMS.PREFIX, DCTERMS.NAMESPACE);
        
        List<AnalyzedField> fields = analyze(clazz);
        
        IRI subject = FACTORY.createIRI("http://example.com/fdp");
        
        for (AnalyzedField field : fields) {
            Optional<?> value;
            try {
                value = Optional.ofNullable(field.getter.invoke(metadata));
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.warn("Could not invoke {} on {}: {}", field.getter, clazz, e.getMessage());
                continue;
            }
            
            if (!value.isPresent()) {
                logger.debug("{} returned null, skipping", field.getter);
                continue;
            }
            
            if (value.get() instanceof Iterable) {
                // loop over triple value
                for (Object v : (Iterable<?>)value.get()) {
                    writeTriple(model, subject, field.annotation, v);
                }
            } else {
                // write single triple
                writeTriple(model, subject, field.annotation, value.get());
            }
        }
        
        StringWriter writer = new StringWriter();
        Rio.write(model, writer, RDFFormat.TURTLE);
        
        return writer.toString();
    }
    
    /**
     * Analyzes the given {@code Class} by checking its fields for the presence of a
     * {@link Predicate} annotation. When found, the annotation and the
     * {@link PropertyDescriptor#getReadMethod() getter} that corresponds to the field are stored.
     * @param clazz the {@code Class} under analysis.
     * @return list of analyzed fields.
     */
    private List<AnalyzedField> analyze(Class<?> clazz) {
        if (cache.containsKey(clazz)) {
            logger.debug("Retrieved cached analysis for {}", clazz);
            return cache.get(clazz);
        }
        
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            logger.warn("Could not introspect {}: {}", clazz, e.getMessage());
            throw new IllegalStateException(e);
        }
        
        List<AnalyzedField> fields = new ArrayList<>();
        
        if (!Object.class.equals(clazz.getSuperclass())) {
            List<AnalyzedField> parentFields = analyze(clazz.getSuperclass());
            fields.addAll(parentFields);
        }
        
        for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
            if ("class".equals(property.getName())) {
                logger.debug("Skipping .class property for {}", clazz);
                continue;
            }
            
            Field field;
            try {
                field = clazz.getDeclaredField(property.getName());
            } catch (NoSuchFieldException e) {
                logger.debug("Could not get field {} for {}: {}", property.getName(), clazz,
                        e.getMessage());
                continue;
            }
            
            Optional<Predicate> annotation =
                    Optional.ofNullable(field.getDeclaredAnnotation(Predicate.class));
            if (!annotation.isPresent()) {
                logger.debug("No @Predicate annotation found on {} for {}", field.getName(), clazz);
                continue;
            }
            
            fields.add(new AnalyzedField(property, annotation.get()));
        }
        
        // cache result for future analysis
        synchronized (cache) {
            logger.debug("Caching analysis for {}", clazz);
            cache.put(clazz, fields);
        }
        
        return fields;
    }
    
    /**
     * Convenience method for writing the triple statement to a {@link Model}.
     * @param model the model container.
     * @param subject subject of the triple statement.
     * @param annotation field annotation to determine the triple's predicate and object datatype.
     * @param value the triple's object value.
     */
    private void writeTriple(Model model, Resource subject, Predicate annotation, Object value) {
        Value object;
        
        if (!annotation.isLiteral()) {
            object = FACTORY.createIRI(value.toString());
        } else {
            IRI datatype = FACTORY.createIRI(annotation.datatype());
            object = FACTORY.createLiteral(value.toString(), datatype);
        }
        
        IRI pred = FACTORY.createIRI(annotation.value());
        model.add(subject, pred, object);
    }
    
    /**
     * {@code POD} container to represent an analyzed field.
     */
    private class AnalyzedField {
        private Method getter;
        private Predicate annotation;
        
        public AnalyzedField(PropertyDescriptor property, Predicate annotation) {
            this.getter = property.getReadMethod();
            this.annotation = annotation;
        }
    }
}
