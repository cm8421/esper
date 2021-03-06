/*
 ***************************************************************************************
 *  Copyright (C) 2006 EsperTech, Inc. All rights reserved.                            *
 *  http://www.espertech.com/esper                                                     *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 ***************************************************************************************
 */
package com.espertech.esper.common.internal.serde.serdeset.builtin;


import com.espertech.esper.common.client.serde.DataInputOutputSerde;
import com.espertech.esper.common.client.serde.EventBeanCollatedWriter;
import com.espertech.esper.common.client.type.EPTypeClass;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Binding for nullable float values.
 */
public class DIONullableFloatSerde implements DataInputOutputSerde<Float> {
    public final static EPTypeClass EPTYPE = new EPTypeClass(DIONullableFloatSerde.class);

    public final static DIONullableFloatSerde INSTANCE = new DIONullableFloatSerde();

    private DIONullableFloatSerde() {
    }

    public void write(Float object, DataOutput output, byte[] pageFullKey, EventBeanCollatedWriter writer) throws IOException {
        write(object, output);
    }

    public void write(Float object, DataOutput stream) throws IOException {
        boolean isNull = object == null;
        stream.writeBoolean(isNull);
        if (!isNull) {
            stream.writeFloat(object);
        }
    }

    public Float read(DataInput input) throws IOException {
        return readInternal(input);
    }

    public Float read(DataInput input, byte[] resourceKey) throws IOException {
        return readInternal(input);
    }

    private Float readInternal(DataInput input) throws IOException {
        boolean isNull = input.readBoolean();
        if (isNull) {
            return null;
        }
        return input.readFloat();
    }
}
