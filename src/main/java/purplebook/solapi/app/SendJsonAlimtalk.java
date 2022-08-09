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
 * 한번 요청으로 1만건까지 알림톡 발송이 가능합니다.
 */
public class SendJsonAlimtalk {
    public static void main(String[] args) {
        JsonObject params = new JsonObject();
        JsonArray messages = new JsonArray();

        JsonObject kakaoOptions1 = new JsonObject();
        kakaoOptions1.addProperty("pfId", "KA01PF210329072824713TdpP5oO4q1K");
        kakaoOptions1.addProperty("templateId", "KA01TP210329073403394EsVbp2rO6Yt");

        JsonObject msg = new JsonObject();
        msg.addProperty("to", "01086491176");
        msg.addProperty("from", "01086491176");
        msg.addProperty("text", "선생님 신원 및 학력 인증이 완료되었습니다.\n" + 
        		"수업을 만들어 나의 과외수업을 더욱 적극적으로 홍보 해보세요");
        msg.add("kakaoOptions", kakaoOptions1);
        messages.add(msg);

        // ... 최대 1만건까지 추가 가능

        params.add("messages", messages);

        Call<GroupModel> api = APIInit.getAPI().sendMessages(APIInit.getHeaders(), params);
        api.enqueue(new Callback<GroupModel>() {
            @Override
            public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                // 성공 시 200이 출력됩니다.
                if (response.isSuccessful()) {
                    System.out.println("statusCode : " + response.code());
                    GroupModel body = response.body();
                    System.out.println("groupId : " + body.getGroupId());
                    System.out.println("status: " + body.getStatus());
                    System.out.println("count: " + body.getCount().toString());
                } else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GroupModel> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
