/**
 * Copyright (C) FuseSource, Inc.
 * http://fusesource.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fusesource.fabric.internal;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.fusesource.fabric.api.FabricRequirements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

/**
 * Helper methods for marshalling to and from JSON
 */
public class RequirementsJson {
    private static final transient Logger LOG = LoggerFactory.getLogger(RequirementsJson.class);
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }

    public static void writeRequirements(OutputStream out, FabricRequirements value) throws IOException {
        mapper.writeValue(out, value);
    }


    public static String toJSON(FabricRequirements answer) throws IOException {
        try {
            StringWriter writer = new StringWriter();
            mapper.writeValue(writer, answer);

            return writer.toString();
        } catch (IOException e) {
            LOG.warn("Failed to marshal to JSON: " + e, e);
            throw new IOException(e.getMessage());
        }
    }


    public static FabricRequirements readRequirements(InputStream in) throws IOException {
        return mapper.readValue(in, FabricRequirements.class);
    }


    public static FabricRequirements fromJSON(String json) throws IOException {
        if (json == null) {
            return null;
        }
        json = json.trim();
        if (json.length() == 0 || json.equals("{}")) {
            return null;
        }
        return mapper.reader(FabricRequirements.class).readValue(json);
    }
}
