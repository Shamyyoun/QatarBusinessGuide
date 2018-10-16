package json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datamodels.Event;

public class EventsHandler {
    private String response;

    public EventsHandler(String response) {
        this.response = response;
    }

    public Event[] handle() {
        try {
            JSONArray jsonArray = new JSONArray(response);
            Event[] events = new Event[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Event event = handleEvent(jsonObject);

                events[i] = event;
            }
            return events;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private Event handleEvent(JSONObject jsonObject) {
        Event event;
        try {
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String nameEn = jsonObject.getString("name_en");
            String date = jsonObject.getString("date");
            String place = jsonObject.getString("place");
            String placeEn = jsonObject.getString("place_en");
            String desc1 = jsonObject.getString("desc1");
            String desc1En = jsonObject.getString("desc1_en");
            String logo = jsonObject.getString("logo");

            event = new Event(id, name, nameEn, date, place, placeEn, desc1, desc1En, logo);
        } catch (JSONException e) {
            event = null;
            e.printStackTrace();
        }

        return event;
    }
}
