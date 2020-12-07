package myClasses;

import api.geo_location;
import com.google.gson.*;

import java.lang.reflect.Type;

public  class GraphJsonDeserializer implements JsonDeserializer<DWGraph_DS> {

    @Override
    public DWGraph_DS deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        DWGraph_DS graph = new DWGraph_DS();

        JsonArray GraphJsonObj1 = jsonObject.get("Nodes").getAsJsonArray();
        for (JsonElement set : GraphJsonObj1) {
            JsonElement jsonValueElement = set;
            int id = set.getAsJsonObject().get("id").getAsInt();
           String Spos = set.getAsJsonObject().get("pos").getAsString();
           String[] x = Spos.split(",");

            geo_location pos = new geoLocation(Double.valueOf(x[0]) , Double.valueOf(x[1]) , Double.valueOf(x[2]));

            NodeData n = new NodeData(id);
            n.setLocation(pos);
            graph.addNode(n);

        }


        JsonArray GraphJsonObj = jsonObject.get("Edges").getAsJsonArray();

        for (JsonElement set : GraphJsonObj) {
            int src = set.getAsJsonObject().get("src").getAsInt();
            double w = set.getAsJsonObject().get("w").getAsDouble();
            int dest = set.getAsJsonObject().get("dest").getAsInt();

            graph.connect(src, dest, w);
        }

        return graph;
    }
}