package json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datamodels.SubSector;

public class SubSectorsHandler {
    private String response;

    public SubSectorsHandler(String response) {
        this.response = response;
    }

    public SubSector[] handle() {
        try {
            JSONArray jsonArray = new JSONArray(response);
            SubSector[] subSectors = new SubSector[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SubSector subSector = handleSubSector(jsonObject);

                subSectors[i] = subSector;
            }
            return subSectors;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private SubSector handleSubSector(JSONObject jsonObject) {
        SubSector subSector;
        try {
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String nameEn = jsonObject.getString("name_en");

            subSector = new SubSector(id, name, nameEn);
        } catch (JSONException e) {
            subSector = null;
            e.printStackTrace();
        }

        return subSector;
    }
}
