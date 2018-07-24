package com.android.ql.lf.redpacketmonkey.data.greendao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;
import org.json.JSONException;

import java.util.List;

public class ReceiverConverter implements PropertyConverter<List<ReceiverInfoBean>, String> {

    @Override
    public List<ReceiverInfoBean> convertToEntityProperty(String databaseValue) {
        try {
            if (databaseValue == null) {
                return null;
            } else {
                return new Gson().fromJson(databaseValue,new TypeToken<List<ReceiverInfoBean>>(){}.getType());
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String convertToDatabaseValue(List<ReceiverInfoBean> entityProperty) {
        if (entityProperty == null) {
            return null;
        } else {
            return new Gson().toJson(entityProperty);
        }
    }
}
