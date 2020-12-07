package myClasses;

import com.google.gson.*;
import gameClient.CL_Pokemon;
import gameClient.util.Point3D;

import java.lang.reflect.Type;

public class PokJasonDes implements JsonDeserializer<CL_Pokemon> {
    private Object CL_Pokemon;

    @Override
    public CL_Pokemon deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {


        JsonObject jsonObject = jsonElement.getAsJsonObject();



        JsonArray GraphJsonObj1 = jsonObject.get("Pokemons").getAsJsonArray();
        for (JsonElement set : GraphJsonObj1) {

            JsonElement jsonValueElement = set;
          //  set = jsonObject1.get("Pokemons").getAsJsonArray();
            int v = set.getAsJsonObject().get("value").getAsInt();
            int t = set.getAsJsonObject().get("type").getAsInt();
            String Spos = set.getAsJsonObject().get("pos").getAsString();
            String[] x = Spos.split(",");
            Point3D pos = new Point3D(Double.valueOf(x[0]), Double.valueOf(x[1]), Double.valueOf(x[2]));

            CL_Pokemon p = new CL_Pokemon(pos, t, v, 0.0, null);
           // pok.arr.add(p);


        }
       return null;
    }
}
