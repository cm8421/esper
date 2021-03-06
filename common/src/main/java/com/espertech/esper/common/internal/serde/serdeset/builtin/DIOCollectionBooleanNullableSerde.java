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
import java.util.ArrayList;
import java.util.Collection;

public class DIOCollectionBooleanNullableSerde implements DataInputOutputSerde<Collection<Boolean>> {
    public final static EPTypeClass EPTYPE = new EPTypeClass(DIOCollectionBooleanNullableSerde.class);

    public final static DIOCollectionBooleanNullableSerde INSTANCE = new DIOCollectionBooleanNullableSerde();

    private DIOCollectionBooleanNullableSerde() {
    }

    public void write(Collection<Boolean> object, DataOutput output) throws IOException {
        writeInternal(object, output);
    }

    public Collection<Boolean> read(DataInput input) throws IOException {
        return readInternal(input);
    }

    public void write(Collection<Boolean> object, DataOutput output, byte[] unitKey, EventBeanCollatedWriter writer) throws IOException {
        writeInternal(object, output);
    }

    public Collection<Boolean> read(DataInput input, byte[] unitKey) throws IOException {
        return readInternal(input);
    }

    private void writeInternal(Collection<Boolean> object, DataOutput output) throws IOException {
        if (object == null) {
            output.writeInt(-1);
            return;
        }
        output.writeInt(object.size());
        for (Boolean i : object) {
            DIONullableBooleanSerde.INSTANCE.write(i, output);
        }
    }

    private Collection<Boolean> readInternal(DataInput input) throws IOException {
        int len = input.readInt();
        if (len == -1) {
            return null;
        }
        Collection<Boolean> array = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            array.add(DIONullableBooleanSerde.INSTANCE.read(input));
        }
        return array;
    }
}
