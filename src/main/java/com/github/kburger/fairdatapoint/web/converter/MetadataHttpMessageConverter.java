package com.github.kburger.fairdatapoint.web.converter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.eclipse.rdf4j.rio.RDFFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.github.kburger.fairdatapoint.io.MetadataWriter;
import com.github.kburger.fairdatapoint.model.Metadata;

public abstract class MetadataHttpMessageConverter extends AbstractHttpMessageConverter<Metadata> {
    @Autowired
    protected MetadataWriter metadataWriter;
    
    protected RDFFormat format;
    
    public MetadataHttpMessageConverter(RDFFormat format) {
        super(getMediaTypes(format));
        this.format = format;
    }
    
    @Override
    protected boolean supports(Class<?> clazz) {
        return Metadata.class.isAssignableFrom(clazz);
    }

    @Override
    protected Metadata readInternal(Class<? extends Metadata> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void writeInternal(Metadata metadata, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        String output = metadataWriter.write(metadata);
        
        // The OutputStreamWriter could be surrounded by a try-with-resources, but that seems
        // to produce unreachable bytecode. When determining the code coverage, this produces
        // seemingly non-covered code. See http://stackoverflow.com/a/17356707
        OutputStreamWriter writer =
                new OutputStreamWriter(outputMessage.getBody(), StandardCharsets.UTF_8);
        writer.write(output);
        writer.close();
    }
    
    private static MediaType[] getMediaTypes(RDFFormat format) {
        return format.getMIMETypes().stream()
                .map(MediaType::parseMediaType)
                .toArray(MediaType[]::new);
    }
}
