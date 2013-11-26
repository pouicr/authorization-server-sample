package net.atos.contest.test.arquillian.cukespace;

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;
import cucumber.deps.com.thoughtworks.xstream.converters.Converter;
import cucumber.deps.com.thoughtworks.xstream.converters.MarshallingContext;
import cucumber.deps.com.thoughtworks.xstream.converters.UnmarshallingContext;
import cucumber.deps.com.thoughtworks.xstream.io.HierarchicalStreamReader;
import cucumber.deps.com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.Date;

@XStreamConverter(CardInput.CardConverter.class)
public class CardInput {
    private long id;
    private Date expiration;

    public static class CardConverter implements Converter {
        @Override
        public void marshal(final Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
            return null;
        }

        @Override
        public boolean canConvert(Class type) {
            return CardInput.class.equals(type);
        }
    }
}
