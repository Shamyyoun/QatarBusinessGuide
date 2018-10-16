package json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datamodels.Sector;

public class SectorsHandler {
    private String response;

    public SectorsHandler(String response) {
        this.response = response;
    }

    public Sector[] handle() {
        try {
            JSONArray jsonArray = new JSONArray(response);
            Sector[] sectors = new Sector[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Sector sector = handleSector(jsonObject);

                sectors[i] = sector;
            }
            return sectors;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private Sector handleSector(JSONObject jsonObject) {
        Sector sector;
        try {
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String nameEn = jsonObject.getString("name_en");

            sector = new Sector(id, name, nameEn);
        } catch (JSONException e) {
            sector = null;
            e.printStackTrace();
        }

        return sector;
    }
}
