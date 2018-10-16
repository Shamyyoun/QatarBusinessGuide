package json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datamodels.Company;

public class CompaniesHandler {
    private String response;

    public CompaniesHandler(String response) {
        this.response = response;
    }

    public Company[] handle() {
        try {
            JSONArray jsonArray = new JSONArray(response);
            Company[] companies = new Company[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Company company = handleCompany(jsonObject);

                companies[i] = company;
            }
            return companies;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private Company handleCompany(JSONObject jsonObject) {
        Company company;
        try {
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String nameEn = jsonObject.getString("name_en");
            String country = jsonObject.getString("country");
            String place = jsonObject.getString("place");
            String desc = jsonObject.getString("desc1");
            String descEn = jsonObject.getString("desc1_en");
            String telephone1 = jsonObject.getString("telephone1");
            String telephone2 = jsonObject.getString("telephone2");
            String mobile1 = jsonObject.getString("mobile1");
            String mobile2 = jsonObject.getString("mobile2");
            String address = jsonObject.getString("address");
            String addressEn = jsonObject.getString("address_en");
            double latitude = jsonObject.getDouble("latitude");
            double longitude = jsonObject.getDouble("longitude");
            String fax = jsonObject.getString("fax");
            String email = jsonObject.getString("email");
            String website = jsonObject.getString("website");
            String video = jsonObject.getString("video");
            String logo = jsonObject.getString("logo");

            company = new Company(id)
                    .setName(name)
                    .setNameEn(nameEn)
                    .setCountry(country)
                    .setPlace(place)
                    .setDesc(desc)
                    .setDescEn(descEn)
                    .setTelephone1(telephone1)
                    .setTelephone2(telephone2)
                    .setMobile1(mobile1)
                    .setMobile2(mobile2)
                    .setAddress(address)
                    .setAddressEn(addressEn)
                    .setLatitude(latitude)
                    .setLongitude(longitude)
                    .setFax(fax)
                    .setEmail(email)
                    .setWebsite(website)
                    .setVideo(video)
                    .setLogo(logo);

        } catch (JSONException e) {
            company = null;
            e.printStackTrace();
        }

        return company;
    }
}
