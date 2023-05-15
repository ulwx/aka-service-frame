package com.github.ulwx.aka.frame;

import com.github.ulwx.aka.frame.protocol.req.IProtocol;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AspectJTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class ScanProtocolTypeFilter implements TypeFilter {

    private ResourceLoader resourceLoader;
    private AssignableTypeFilter protocolFilter;
    private AspectJTypeFilter aspectActionFilter;
    public ScanProtocolTypeFilter(ResourceLoader resourceLoader){
        this.resourceLoader=resourceLoader;
        protocolFilter=new AssignableTypeFilter(IProtocol.class);

    }
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {

        if(aspectActionFilter.match(metadataReader,metadataReaderFactory)) {
            return true;
        }

        return false;
    }
}
