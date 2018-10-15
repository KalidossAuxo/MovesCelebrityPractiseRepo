package com.moves.movesCelebrity.utils.serdesr;

import com.google.gson.*;

import java.lang.reflect.Type;


public  class NumberLongGsonDeserializer implements JsonDeserializer<Long> {
    @Override
    public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.has("$numberLong")) {
            JsonElement elem = jsonObject.get("$numberLong");
            if (elem != null && !elem.isJsonNull()) {
                return Long.parseLong(elem.getAsString());
            }
        }
        return null;
    }
}
