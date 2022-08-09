package purplebook.solapi.app;

import java.io.IOException;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import purplebook.model.response.GroupModel;
import purplebook.utilities.APIInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 한번 요청으로 1만건까지 SMS 발송이 가능합니다.
 */
public class SendJsonSMS {
    public static void sendSMS(String phoneNumber, String message) {
        JsonObject params = new JsonObject();
        JsonArray messages = new JsonArray();
System.out.println(phoneNumber);
System.out.println("테스트");
        JsonObject msg = new JsonObject();
        msg.addProperty("to", phoneNumber);
        msg.addProperty("from", "01086491176");
        msg.addProperty("text", message);
        messages.add(msg);

        // ... 최대 1만건까지 추가 가능

        params.add("messages", messages);

        Call<GroupModel> api = APIInit.getAPI().sendMessages(APIInit.getHeaders(), params);
        api.enqueue(new Callback<GroupModel>() {
            @Override
            public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
            	System.out.println(response.toString());
                // 성공 시 200이 출력됩니다.
                if (response.isSuccessful()) {
                } else {
                    
                }
            }

            @Override
            public void onFailure(Call<GroupModel> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
